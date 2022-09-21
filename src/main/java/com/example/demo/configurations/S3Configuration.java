package com.example.demo.configurations;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Configuration {

    private AWSCredentialsProvider awsCredentialsProvider;

    @Value("${amazon.aws.region}")
    private String amazonAWSRegion;

    @Autowired
    public S3Configuration(AWSCredentialsProvider awsCredentialsProvider){
        this.awsCredentialsProvider = awsCredentialsProvider;
    }

    @Bean
    public AmazonS3 amazonS3(){
        AmazonS3 client = AmazonS3ClientBuilder.standard()
                .withRegion(amazonAWSRegion)
                .withCredentials(awsCredentialsProvider).build();
        return client;
    }
}
