package org.example;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MessageService {

    public MessageService() {
        System.out.println("another service");
    }

    public String process(byte[] message) throws IOException {
        String messageJson = new String(message);
        System.out.println("this is it: " + messageJson);
        return ">>>" + messageJson;
    }

}