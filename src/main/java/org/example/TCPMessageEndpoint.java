package org.example;

import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;

import java.io.IOException;

@MessageEndpoint
public class TCPMessageEndpoint {

    private final MessageService messageService;

    public TCPMessageEndpoint(MessageService messageService) {
        this.messageService = messageService;
    }

    @ServiceActivator(inputChannel = "tcp-channel-sample")
    public byte[] process(byte[] message) throws IOException {
        String response = messageService.process(message);
        return response.getBytes();
    }

}