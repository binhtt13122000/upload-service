package com.example.demo.services.messageservices;

import java.io.IOException;

public interface MessageReceiveService<T extends Object> {
    void receiveEvent(T event) throws IOException;
}
