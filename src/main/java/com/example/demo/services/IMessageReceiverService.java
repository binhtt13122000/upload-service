package com.example.demo.services;

import com.amazonaws.services.s3.event.S3EventNotification;

import java.io.IOException;

public interface IMessageReceiverService {
    void receiveS3Event(S3EventNotification s3EventNotificationRecord) throws IOException;
}
