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
    private AmazonDynamoDB amazonDynamoDB;

    @Autowired
    public InventoryRepositoryImp(AmazonDynamoDB amazonDynamoDB){
        this.amazonDynamoDB = amazonDynamoDB;
    }
    @Override
    public void insert(Inventory inventory) throws AmazonDynamoDBException {
        DynamoDBMapper mapper = new DynamoDBMapper(amazonDynamoDB);
        mapper.save(inventory);
    }

    @Override
    public void batchInsert(List<Inventory> inventoryList) {
        DynamoDBMapper mapper = new DynamoDBMapper(amazonDynamoDB);
        mapper.batchSave(inventoryList);
    }
}
