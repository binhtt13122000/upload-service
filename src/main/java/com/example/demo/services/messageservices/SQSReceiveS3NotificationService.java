package com.example.demo.services.messageservices;

import com.amazonaws.services.s3.event.S3EventNotification;

public interface SQSReceiveS3NotificationService extends MessageReceiveService<S3EventNotification>{
}
