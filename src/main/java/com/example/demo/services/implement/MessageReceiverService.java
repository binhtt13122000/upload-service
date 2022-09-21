package com.example.demo.services.implement;

import com.amazonaws.services.s3.event.S3EventNotification;
import com.example.demo.common.Common;
import com.example.demo.entities.Inventory;
import com.example.demo.services.IDBService;
import com.example.demo.services.IFileService;
import com.example.demo.services.IMessageReceiverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class MessageReceiverService implements IMessageReceiverService {
    private IFileService fileService;

    private IDBService dbService;
    @Autowired
    public MessageReceiverService(IFileService fileService, IDBService dbService){
        this.fileService = fileService;
        this.dbService = dbService;
    }

    @SqsListener(value = Common.QUEUE_NAME, deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void receiveS3Event(S3EventNotification s3EventNotificationRecord) throws IOException {
        S3EventNotification.S3Entity s3Entity = s3EventNotificationRecord.getRecords().get(0).getS3();
        String objectKey = s3Entity.getObject().getKey();
        BufferedReader bufferedReader = fileService.download(objectKey);
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
        this.dbService.batchInsert(inventoryList);

    }

    private List<String> getRecordFromLine(String line) {
        List<String> values = new ArrayList<String>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(",");
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }
        return values;
    }
}
