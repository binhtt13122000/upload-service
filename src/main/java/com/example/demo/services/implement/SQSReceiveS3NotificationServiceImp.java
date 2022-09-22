package com.example.demo.services.implement;

import com.amazonaws.services.s3.event.S3EventNotification;
import com.example.demo.common.Constants;
import com.example.demo.entities.Inventory;
import com.example.demo.services.dbservices.InventoryService;
import com.example.demo.services.fileservices.S3Service;
import com.example.demo.services.messageservices.SQSReceiveS3NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SQSReceiveS3NotificationServiceImp implements SQSReceiveS3NotificationService {
    @Autowired
    private S3Service s3Service;
    @Autowired
    private InventoryService inventoryService;

    @SqsListener(value = "${amazon.aws.sqs}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void receiveEvent(S3EventNotification event) throws IOException {
        S3EventNotification.S3Entity s3Entity = event.getRecords().get(Constants.FIRST_ELEMENT).getS3();
        String objectKey = s3Entity.getObject().getKey();
        BufferedReader bufferedReader = s3Service.download(objectKey);
        String[] data;
        List<Inventory> inventoryList = new ArrayList<>();
        while (true) {
            String line = bufferedReader.readLine();
            if (!StringUtils.hasText(line)) {
                break;
            } else {
                data = line.split(Constants.DELIMITER);
                inventoryList.add(new Inventory(data[Constants.FIRST_ELEMENT], data[Constants.SECOND_ELEMENT], Integer.parseInt(data[Constants.THIRD_ELEMENT])));
            }
        }
        this.inventoryService.batchInsert(inventoryList);

    }
}
