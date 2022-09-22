package com.example.demo.repositories.implement;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;
import com.example.demo.entities.Inventory;
import com.example.demo.repositories.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InventoryRepositoryImp implements InventoryRepository {
    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public void insert(Inventory inventory) throws AmazonDynamoDBException {
        dynamoDBMapper.save(inventory);
    }

    @Override
    public void batchInsert(List<Inventory> inventoryList) {
        dynamoDBMapper.batchSave(inventoryList);
    }
}
