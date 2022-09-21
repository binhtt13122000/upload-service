package com.example.demo.services;

import java.io.BufferedReader;

public interface IFileService {
    void upload();
    BufferedReader download(String fileName);
}
