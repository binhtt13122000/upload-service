package com.example.demo.configurations;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.messaging.config.QueueMessageHandlerFactory;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.annotation.support.PayloadMethodArgumentResolver;

import java.util.Collections;

@Configuration
public class CustomSqsConfiguration {
    private AWSCredentialsProvider awsCredentialsProvider;

    @Value("${amazon.aws.region}")
    private String amazonAWSRegion;

    @Autowired
    public CustomSqsConfiguration(AWSCredentialsProvider awsCredentialsProvider){
        this.awsCredentialsProvider = awsCredentialsProvider;
    }

    @Bean
    public AmazonSQSAsync amazonSQS() {
        AmazonSQSAsync amazonSQS = AmazonSQSAsyncClientBuilder.standard()
                .withRegion(amazonAWSRegion)
                .withCredentials(awsCredentialsProvider).build();
        return amazonSQS;
    }


    @Bean
    public QueueMessagingTemplate queueMessagingTemplate(AmazonSQSAsync amazonSQSAsync) {
        return new QueueMessagingTemplate(amazonSQSAsync);
    }

    @Bean
    public QueueMessageHandlerFactory queueMessageHandlerFactory(final ObjectMapper mapper,
                                                                 final AmazonSQSAsync amazonSQSAsync) {
        final QueueMessageHandlerFactory queueHandlerFactory = new QueueMessageHandlerFactory();
        queueHandlerFactory.setAmazonSqs(amazonSQSAsync);
        queueHandlerFactory.setArgumentResolvers(
                Collections.singletonList(new PayloadMethodArgumentResolver(jackson2MessageConverter(mapper))));
        return queueHandlerFactory;
    }

    private MessageConverter jackson2MessageConverter(final ObjectMapper mapper) {
        final MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setStrictContentTypeMatch(false);
        converter.setObjectMapper(mapper);
        return converter;
    }
}
