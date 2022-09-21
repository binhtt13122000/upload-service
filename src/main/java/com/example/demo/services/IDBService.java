package com.example.demo.services;

import com.example.demo.entities.Inventory;

import java.util.List;

public interface IDBService {
    void insert(Inventory inventory);
    void batchInsert(List<Inventory> inventoryList);
}
