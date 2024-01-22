package org.example;

import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;

import java.io.IOException;

@MessageEndpoint
public class TCPMessageEndpoint {

    public TCPMessageEndpoint() {
        System.out.println("endpoint created");
    }

    @ServiceActivator(inputChannel = "tcp-channel-sample")
    public byte[] process(Message<?> message) throws IOException {
        return "another".getBytes();
    }

}