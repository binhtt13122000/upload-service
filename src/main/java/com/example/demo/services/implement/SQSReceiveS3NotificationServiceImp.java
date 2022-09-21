package com.example.demo.services.implement;

import com.amazonaws.services.s3.event.S3EventNotification;
import com.example.demo.common.Common;
import com.example.demo.entities.Inventory;
import com.example.demo.services.dbservices.InventoryService;
import com.example.demo.services.fileservices.S3Service;
import com.example.demo.services.messageservices.SQSReceiveS3NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Slf4j
@Service
public class SQSReceiveS3NotificationServiceImp implements SQSReceiveS3NotificationService {
    private S3Service s3Service;

    private InventoryService inventoryService;
    @Autowired
    public SQSReceiveS3NotificationServiceImp(S3Service s3Service, InventoryService inventoryService){
        this.s3Service = s3Service;
        this.inventoryService = inventoryService;
    }

    @SqsListener(value = Common.QUEUE_NAME, deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void receiveEvent(S3EventNotification event) throws IOException {
        S3EventNotification.S3Entity s3Entity = event.getRecords().get(0).getS3();
        String objectKey = s3Entity.getObject().getKey();
        BufferedReader bufferedReader = s3Service.download(objectKey);
        String[] data;
        List<Inventory> inventoryList = new ArrayList<>();
        while (true) {
            String line = bufferedReader.readLine();
            if (line == null) {
                break;
            } else {
                data = line.split(Common.DELIMITER);
                inventoryList.add(new Inventory(data[0], data[1], Integer.parseInt(data[2])));
            }
        }
        this.inventoryService.batchInsert(inventoryList);

    }
}
