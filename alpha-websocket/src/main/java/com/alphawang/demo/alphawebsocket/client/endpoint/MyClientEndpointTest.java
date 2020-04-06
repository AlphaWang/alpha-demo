package com.alphawang.demo.alphawebsocket.client.endpoint;

import com.alphawang.demo.alphawebsocket.client.endpoint.MyClientEndpoint.MessageHandler;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import javax.websocket.ClientEndpoint;

public class MyClientEndpointTest {

    private static final String HOST = "ws://localhost:8080/greeting";
    
    public static void main(String[] args) throws URISyntaxException, InterruptedException {
        MyClientEndpoint endpoint = new MyClientEndpoint(new URI(HOST));
        
        endpoint.addMessageHandle(new MessageHandler() {
            @Override
            public void handleMessage(String msg) {
                
                String reply = "REP_" +msg;
                System.out.println("will reply: " + reply);

                endpoint.sendMessage(reply);
            }
        });

        int loop = 0;
        while(true) {
            endpoint.sendMessage("Hi " + loop++);
            TimeUnit.SECONDS.sleep(1);
        }
    }

}
