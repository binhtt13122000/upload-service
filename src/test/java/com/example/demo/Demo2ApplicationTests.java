package com.example.demo;

import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

//@Testcontainers
@SpringBootTest
class Demo2ApplicationTests {
//    @Container
//    static LocalStackContainer localStack =
//            new LocalStackContainer(DockerImageName.parse("localstack/localstack"))
//                    .withServices(LocalStackContainer.Service.S3, LocalStackContainer.Service.SQS, LocalStackContainer.Service.DYNAMODB);

}
