package com.alphawang.netty.bio;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

public class BioClient {

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                
                Socket socket = new Socket("127.0.0.1", 8000);
                while (true) {
                    String msg = "OUTPUT: " + new Date();
                    System.out.println(">>> sending msg " + msg);
                    socket.getOutputStream().write(msg.getBytes());
                    Thread.sleep(2000);
                }
                
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();
    }
}
