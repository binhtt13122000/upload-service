package com.example.demo.services.implement;

import com.amazonaws.services.s3.event.S3EventNotification;
import com.example.demo.common.Constants;
import com.example.demo.common.ValidationFunctions;
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
import java.util.stream.Collectors;

@Slf4j
@Service
public class SQSReceiveS3NotificationServiceImp implements SQSReceiveS3NotificationService {
    @Autowired
    private S3Service s3Service;
    @Autowired
    private InventoryService inventoryService;

    @SqsListener(value = "${amazon.aws.sqs}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void receiveEvent(S3EventNotification event) throws IOException {
        //batch insert with dynamoMapper
        S3EventNotification.S3Entity s3Entity = event.getRecords().get(Constants.FIRST_ELEMENT).getS3();
        String objectKey = s3Entity.getObject().getKey();
        List<String> lines = s3Service.download(objectKey);
        List<Inventory> inventories = lines.stream().map(line -> {
                final  String[] data = line.split(Constants.DELIMITER);
                if(data.length == Constants.TOTAL_ELEMENT_IN_LINE){
                    return null;
                }
                String quantity = data[Constants.THIRD_ELEMENT];
                if(!ValidationFunctions.isInteger(quantity, 0, Integer.MAX_VALUE)){
                    return null;
                }
                return new Inventory(data[Constants.FIRST_ELEMENT], data[Constants.SECOND_ELEMENT], Integer.parseInt(quantity));
        }
        ).filter(inventory -> inventory != null).collect(Collectors.toList());
        this.inventoryService.batchInsert(inventories);
    }
}
