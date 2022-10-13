package com.example.demo.services.implement;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.example.demo.services.fileservices.S3Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class S3ServiceImp implements S3Service {

    @Autowired
    private AmazonS3 client;

    @Value("${s3.bucket}")
    private String bucketName;

    @Override
    public void upload() {
//        client.upload();
    }

    @Override
    public List<String> download(String fileName) {
        List<String> lines = new ArrayList<>();
        String line;
        try (S3Object fileData = client.getObject(new GetObjectRequest(bucketName, fileName));
             Reader decoder = new InputStreamReader(fileData.getObjectContent(), Charset.defaultCharset());
             BufferedReader bufferedReader = new BufferedReader(decoder)) {
            while (StringUtils.hasText(line = bufferedReader.readLine())) {
                lines.add(line);
            }
        } catch (Exception e) {
            log.error("Could not handle file s3://{}/{}", bucketName, fileName, e);
            throw new RuntimeException(e);
        }
        return lines;
    }
}
