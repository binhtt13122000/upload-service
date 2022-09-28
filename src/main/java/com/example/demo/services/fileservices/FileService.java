package com.example.demo.services.fileservices;

import java.io.BufferedReader;
import java.util.List;

public interface FileService {
    void upload();
    List<String> download(String fileName);
}
