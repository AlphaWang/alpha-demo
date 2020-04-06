package com.alphawang.demo.alphawebsocket.client.endpoint;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.IO;
import java.io.IOException;
import java.net.URI;
import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ClientEndpoint
public class MyClientEndpoint {
    
    Session userSession = null;
    private MessageHandler messageHandler;
    
    public MyClientEndpoint(URI endpointUri) {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        try {
            container.connectToServer(this, endpointUri);
        } catch (DeploymentException | IOException e) {
            log.error("Failed to connect. ", e);
            throw new RuntimeException(e);
        }
    }
    
    @OnOpen
    public void onOpen(Session userSession) {
        log.info("---- onOpen : " + userSession.getId());
        this.userSession = userSession;
    }
    
    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        log.info("---- onClose : {}. Reason = {}", userSession.getId(), reason);
        this.userSession = null;
    }
    
    @OnMessage
    public void onMessage(String msg) {
        if (this.messageHandler != null) {
            messageHandler.handleMessage(msg);
        }
    }
    
    public void addMessageHandle(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }
    
    public void sendMessage(String msg) {
        this.userSession.getAsyncRemote().sendText(msg);
    }
    
    public static interface MessageHandler {
        public void handleMessage(String msg);
    }
    

}
