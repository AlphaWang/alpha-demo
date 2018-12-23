package com.alphawang.netty.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class BioServer {

    public static void main(String[] args) throws IOException {
        int port = 8000;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("======LISTENING " + port);

        /**
         * 1. 接受BioClient连接
         */
        new Thread(() -> {
            while (true) {
                try {
                    
                    // 读取客户端连接
                    Socket socket = serverSocket.accept();
                    System.out.println("accepted " + socket);

                    /**
                     * 2. 每个连接创建新线程 读取数据
                     * 
                     * 问题： 每个连接创建成功之后都需要一个线程来维护，每个线程包含一个 while 死循环，
                     *       那么 1w 个连接对应 1w 个线程，继而 1w 个 while 死循环
                     *       
                     *       
                     */
                    new Thread(() -> {
                        try {
//                            Scanner scanner = new Scanner(socket.getInputStream());
//                            while (true) {
//                                String request = scanner.nextLine();
//                            }
//                            
                            InputStream inputStream = socket.getInputStream();
                            
                            int len;
                            byte[] data = new byte[1024];
                            
                            while ((len = inputStream.read(data)) != -1) {
                                String request = new String(data, 0, len);
                                System.out.println(Thread.currentThread().getName() + " READ: " + request);
                                
                                String response = "Hello " + request;
                                socket.getOutputStream().write(response.getBytes());
                            }
                            
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
