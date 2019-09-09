package com.alphawang.demo.alphawebsocket.endpoint;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * https://www.baeldung.com/java-websockets
 * 
 * 如何实现？
 * 1. extends javax.websocket.Endpoint
 * 2. Annotation
 * 
 * 如何运行？
 * 
 * 如何测试？
 * 
 */
@ServerEndpoint(value = "/websocket/chat")
public class ChatEndpoint {

    private static final String GUEST_PREFIX = "Guest";

    private static final AtomicInteger connectionIds = new AtomicInteger(0);

    // 每个用户用一个 CharAnnotation 实例来维护
    private static final Set<ChatEndpoint> connections = new CopyOnWriteArraySet<>();

    private final String nickname;
    private Session session;

    public ChatEndpoint() {
        nickname = GUEST_PREFIX + connectionIds.getAndIncrement();
    }

    // 新连接到达时，Tomcat 会创建一个 Session，并回调这个函数
    @OnOpen
    public void start(Session session) {
        this.session = session;
        connections.add(this);
        String message = String.format("* %s %s", nickname, "has joined.");
        broadcast(message);
    }

    // 浏览器关闭连接时，Tomcat 会回调这个函数
    @OnClose
    public void end() {
        connections.remove(this);
        String message = String.format("* %s %s", nickname, "has disconnected.");
        broadcast(message);
    }

    // 浏览器发送消息到服务器时，Tomcat 会回调这个函数
    @OnMessage
    public void incoming(String message) {
        // Never trust the client
        String filteredMessage = String.format("%s: onMessage %s", nickname, message);
        broadcast(filteredMessage);
    }

    //Websocket 连接出错时，Tomcat 会回调这个函数
    @OnError
    public void onError(Throwable t) throws Throwable {
        System.err.println("Chat Error: " + t.toString());
        t.printStackTrace();
    }

    // 向聊天室中的每个用户广播消息
    private static void broadcast(String msg) {
        System.out.println("LOG " + msg);
        
        for (ChatEndpoint client : connections) {
            try {
                synchronized (client) {
                    client.session.getBasicRemote().sendText(msg);
                }
            } catch (IOException e) {
               e.printStackTrace();
            }
        }
    }
}

