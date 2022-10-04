package com.example.demo.repositories.implement;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
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
        Inventory inventory = new Inventory();
        inventory.setBookUuid(bookUUID);
        PaginatedQueryList<Inventory> list = dynamoDBMapper.query(Inventory.class,
                new DynamoDBQueryExpression<Inventory>()
                        .withHashKeyValues(inventory)
                        .withConsistentRead(false)
        );
        return list.stream().map(Inventory::getQuantity).reduce(0, Integer::sum);
    }
}
