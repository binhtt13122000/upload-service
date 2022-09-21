package com.example.demo.services.implement;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.example.demo.services.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Service
public class S3Service implements IFileService {

    private AmazonS3 client;

    @Value("${amazon.aws.bucket}")
    private String bucketName;

    @Autowired
    public S3Service(AmazonS3 client){
        this.client = client;
    }

    @Override
    public void upload() {
//        client.upload();
    }

    @Override
    public BufferedReader download(String fileName) {
        S3Object fileData = client.getObject(new GetObjectRequest(bucketName, fileName));
        return new BufferedReader(new InputStreamReader(
                fileData.getObjectContent()));
    }
}
