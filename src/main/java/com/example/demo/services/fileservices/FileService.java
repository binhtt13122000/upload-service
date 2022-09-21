package com.example.demo.services.fileservices;

import java.io.BufferedReader;

public interface FileService {
    void upload();
    BufferedReader download(String fileName);
}
