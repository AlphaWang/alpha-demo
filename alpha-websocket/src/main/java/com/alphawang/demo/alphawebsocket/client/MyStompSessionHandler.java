package com.alphawang.demo.alphawebsocket.client;

import com.alphawang.demo.alphawebsocket.dto.Message;
import java.lang.reflect.Type;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;

public class MyStompSessionHandler implements StompSessionHandler {

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        System.out.println("---- New session connected : " + session.getSessionId());
        
        session.subscribe("/topic/messages", this);
        session.send("/app/chat", getSampleMessage());

        System.out.println("---- Sent msg to websocket server.");
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload,
                                Throwable exception) {
        System.err.println("EXCEPTION : " + exception);
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        System.err.println("TRANSPORT ERROR : " + exception);
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return Message.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        Message msg = (Message) payload;
        System.out.println("Received : " + msg);
    }

    private Object getSampleMessage() {
        Message msg = new Message();
        msg.setFrom("Alpha");
        msg.setText("Hello!!");
        return msg;
    }
}
