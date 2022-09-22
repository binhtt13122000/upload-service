package com.example.demo.services.implement;

import com.example.demo.entities.Inventory;
import com.example.demo.repositories.InventoryRepository;
import com.example.demo.services.dbservices.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryServiceImp implements InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    @Override
    public void insert(Inventory inventory) {
        this.inventoryRepository.insert(inventory);
    }

    @Override
    public void batchInsert(List<Inventory> inventoryList) {
        this.inventoryRepository.batchInsert(inventoryList);
    }
}
