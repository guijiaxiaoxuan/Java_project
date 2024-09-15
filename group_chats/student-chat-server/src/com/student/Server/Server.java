package com.student.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
    public static final Map<Socket, String> onlineUsers = new HashMap<>();
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(Constant.SERVER_PORT);
            while (true) {
                System.out.println("等待客户端连接...");
                Socket socket = serverSocket.accept();
                new ServerReader(socket).start();
                System.out.println("客户端连接成功...");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
