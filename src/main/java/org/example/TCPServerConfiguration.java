package org.example;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.ip.tcp.TcpInboundGateway;
import org.springframework.integration.ip.tcp.connection.*;
import org.springframework.messaging.MessageChannel;

@Configuration
public class TCPServerConfiguration {

    @Bean
    public AbstractServerConnectionFactory serverConnectionFactory() {
        TcpNioServerConnectionFactory tcpNioServerConnectionFactory = new TcpNioServerConnectionFactory(8888);
        tcpNioServerConnectionFactory.setUsingDirectBuffers(true);


        return tcpNioServerConnectionFactory;
    }

    @Bean(name = "tcp-channel-sample")
    public MessageChannel messageChannel() {
        return new DirectChannel();
    }

    @Bean
    public TcpInboundGateway tcpInboundGateway(AbstractServerConnectionFactory serverConnectionFactory,
                                               @Qualifier(value = "tcp-channel-sample") MessageChannel messageChannel) {
        TcpInboundGateway gateway = new TcpInboundGateway();
        gateway.setConnectionFactory(serverConnectionFactory);
        gateway.setRequestChannel(messageChannel);
        return gateway;
    }

    @Bean
    public TcpConnectionEventListener tcpConnectionEventListener() {
        return new TcpConnectionEventListener();
    }

    private static class TcpConnectionEventListener {

        @EventListener
        public void handleConnectionEvent(TcpConnectionEvent event) {
            if (event instanceof TcpConnectionOpenEvent) {
                handleConnectionOpenEvent((TcpConnectionOpenEvent) event);
            } else if (event instanceof TcpConnectionCloseEvent) {
                handleConnectionCloseEvent((TcpConnectionCloseEvent) event);
            }
        }

        private void handleConnectionOpenEvent(TcpConnectionOpenEvent event) {
            String connectionId = event.getConnectionId();
            System.out.println("Connection opened: " + connectionId);
        }

        private void handleConnectionCloseEvent(TcpConnectionCloseEvent event) {
            String connectionId = event.getConnectionId();
            System.out.println("Connection closed: " + connectionId);
        }
    }
}


