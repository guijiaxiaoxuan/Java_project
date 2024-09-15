package com.student.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
public class ServerReader extends Thread{
    private Socket socket;
    public ServerReader(Socket socket) {
        this.socket = socket;
    }
    public void run() {
        try {
            while (true){
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                int type = dis.readInt();
                switch (type){
                    case 1:
                        // 接受登录消息
                        String username = dis.readUTF();
                        Server.onlineUsers.put(socket,username);
                        updateClientOnlineUsersLines();
                        break;
                    case 2:
                        //接受群聊消息
                        String msg = dis.readUTF();
                        sentMessageToAll(msg);
                        break;
                    case 3:
                        //接受私聊消息
                        break;
                }
            }
        }catch (Exception e){
            Server.onlineUsers.remove(socket);
            updateClientOnlineUsersLines();
        }
    }

    private void sentMessageToAll(String msg) {

        StringBuilder sb = new StringBuilder();
        String username = Server.onlineUsers.get(socket);
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss EEE a");
        String nowStr = formatter.format(now);
        String magResult = String.valueOf(sb.append(username).append(" ")
                .append(nowStr).append("\r\n").append(msg));
        for (Socket socket : Server.onlineUsers.keySet()){
            try{
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                dos.writeInt(2);
                //获取当前时
                dos.writeUTF(magResult);
                dos.flush();
            }catch (Exception e){
                System.out.println("退出了");
            }
        }
    }

    private void updateClientOnlineUsersLines() {
        //更新全部客户端的在线用户列表
        //拿到全部在线客户端的用户名称,把这些名称转发给所有在线的socket管道
        //1.拿到当前全部的在线用户名称
        Collection<String> onlineUsers = Server.onlineUsers.values(); // 拿到全部在线用户的名称
        System.out.println("在线用户:" + onlineUsers);
        //2.遍历这些名称,把名称一个一个的转发给所有在线的socket管道
        for (Socket socket : Server.onlineUsers.keySet()){
            try{
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                dos.writeInt(1);
                dos.writeInt(onlineUsers.size());
                for (String username : onlineUsers){
                    dos.writeUTF(username);
                }
                dos.flush();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

}
