package com.example.demo.services.implement;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.event.S3EventNotification;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.example.demo.common.Constants;
import com.example.demo.entities.Inventory;
import com.example.demo.services.dbservices.InventoryService;
import com.example.demo.services.messageservices.SQSReceiveS3NotificationService;
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SQSReceiveS3NotificationServiceImp implements SQSReceiveS3NotificationService {
    @Autowired
    private AmazonS3 client;
    @Autowired
    private InventoryService inventoryService;

    @Value("${s3.bucket}")
    private String bucketName;

    @SqsListener(value = "${sqs.queue}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void receiveEvent(S3EventNotification event) throws IOException {
        //batch insert with dynamoMapper
        S3EventNotification.S3Entity s3Entity = event.getRecords().get(Constants.S3_ELEMENT).getS3();
        String objectKey = s3Entity.getObject().getKey();
        String line;
        List<Inventory> inventories = new ArrayList<>();
        try (S3Object fileData = client.getObject(new GetObjectRequest(bucketName, objectKey));
             Reader decoder = new InputStreamReader(fileData.getObjectContent(), Charset.defaultCharset());
             BufferedReader bufferedReader = new BufferedReader(decoder)) {
            while (StringUtils.hasText(line = bufferedReader.readLine())) {
                try {
                    final String[] data = line.split(Constants.DELIMITER);
                    if (data.length != Constants.TOTAL_ELEMENT_IN_LINE) {
                        continue;
                    }
                    int quantity = Integer.parseInt(data[Constants.QUANTITY_INDEX]);
                    if (quantity > 0) {
                        inventories.add(new Inventory(data[Constants.UUID_INDEX], data[Constants.BOOK_UUID_INDEX], quantity));
                    }
                } catch (NumberFormatException ex) {
                    log.error("Number format fail");
                }
            }
        } catch (Exception e) {
            log.error("Could not handle file s3://{}/{}", bucketName, objectKey, e);
            throw new RuntimeException(e);
        }
        this.inventoryService.batchInsert(inventories);
    }
}
