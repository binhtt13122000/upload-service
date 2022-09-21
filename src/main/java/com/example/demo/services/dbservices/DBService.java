package com.example.demo.services.dbservices;

import java.util.List;

public interface DBService<T> {
    void insert(T t);
    void batchInsert(List<T> list);
}
