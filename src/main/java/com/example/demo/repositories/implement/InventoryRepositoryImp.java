package com.example.demo.repositories.implement;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.example.demo.common.Constants;
import com.example.demo.entities.Inventory;
import com.example.demo.repositories.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InventoryRepositoryImp implements InventoryRepository {
    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    @Override
    public void insert(Inventory inventory) throws AmazonDynamoDBException {
        dynamoDBMapper.save(inventory);
    }

    @Override
    public void batchInsert(List<Inventory> inventoryList) {
        dynamoDBMapper.batchSave(inventoryList);
    }

    @Override
    public int getQuantityByBookUUID(String bookUUID) {
        Map<String, String> expressionAttributesNames = new HashMap<>();
        expressionAttributesNames.put(Constants.BOOK_ID_ATTRIBUTE_NAME, Constants.BOOK_ID_ATTRIBUTE_NAME_VALUE);
        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(Constants.BOOK_ID_ATTRIBUTE_VALUE, new AttributeValue().withS(bookUUID));
        PaginatedScanList<Inventory> list = dynamoDBMapper.scan(Inventory.class,
                new DynamoDBScanExpression()
                        .withFilterExpression(Constants.GET_BY_BOOK_SCAN)
                        .withExpressionAttributeNames(expressionAttributesNames)
                        .withExpressionAttributeValues(expressionAttributeValues)
                        .withConsistentRead(false)
        );
        return list.stream().map(book -> book.getQuantity()).reduce(0, (prev, cur) -> prev + cur);
    }
}
