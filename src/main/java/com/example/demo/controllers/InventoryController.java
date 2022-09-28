package com.example.demo.controllers;

import com.example.demo.common.Constants;
import com.example.demo.services.dbservices.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @GetMapping(Constants.GET_QUANTITY_BY_BOOK)
    public ResponseEntity getOne(@PathVariable String id){
        return new ResponseEntity(this.inventoryService.countByBookId(id), HttpStatus.OK);
    }
}
