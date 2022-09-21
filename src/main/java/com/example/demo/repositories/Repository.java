package com.example.demo.repositories;

import com.example.demo.entities.Inventory;

import java.util.List;

public interface Repository<T> {
    void insert(T t);
    void batchInsert(List<T> list);
}
