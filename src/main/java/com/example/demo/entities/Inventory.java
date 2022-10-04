package com.example.demo.entities;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.example.demo.common.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName = Constants.TABLE_NAME)
public class Inventory {
    @DynamoDBHashKey
    private String uuid;
    @DynamoDBAttribute
    private String bookUuid;
    @DynamoDBAttribute
    private int quantity;

}
