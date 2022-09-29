package com.example.demo.configurations;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("aws")
public class AWSConfiguration {
    @Bean
    public AmazonS3 amazonS3() {
        System.out.println("aws");
        AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
        System.out.println(s3.getRegion());
        return s3;
    }

    @Bean
    public AmazonSQSAsync amazonSQS() {
        return AmazonSQSAsyncClientBuilder.defaultClient();
    }

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        return AmazonDynamoDBClientBuilder.defaultClient();
    }

    @Bean
    public DynamoDBMapper dynamoDBMapper(){
        return new DynamoDBMapper(amazonDynamoDB());
    }

//    @Bean
//    public AmazonCloudWatchAsync amazonCloudWatchAsync() {
//        return AmazonCloudWatchAsyncClientBuilder.defaultClient();
//    }

//    private AWSCredentials amazonAWSCredentials() {
//        return new BasicAWSCredentials(
//                amazonAWSAccessKey, amazonAWSSecretKey);
//    }
//
//    @Bean
//    public AWSCredentialsProvider awsCredentialsProvider(){
//        return new AWSStaticCredentialsProvider(amazonAWSCredentials());
//    }
}
