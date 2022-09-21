package com.example.demo.configurations;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamoDBConfiguration {
    private AWSCredentialsProvider awsCredentialsProvider;

    @Value("${amazon.aws.region}")
    private String amazonAWSRegion;

    @Autowired
    public DynamoDBConfiguration(AWSCredentialsProvider awsCredentialsProvider){
        this.awsCredentialsProvider = awsCredentialsProvider;
    }

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        AmazonDynamoDB amazonDynamoDB
                = AmazonDynamoDBClientBuilder
                    .standard()
                    .withCredentials(awsCredentialsProvider)
                    .withRegion(amazonAWSRegion)
                    .build();
        return amazonDynamoDB;
    }
}
