package com.student.ui;

import java.io.DataInputStream;
import java.net.Socket;

public class ClientReader extends Thread {
    private Socket socket;
    private ClientChatFrame win;
    private DataInputStream dis;
    private String[] onLineUsers;
    public ClientReader(Socket socket, ClientChatFrame clientChatFrame) {
        this.socket = socket;
        this.win = clientChatFrame;
    }
    public void run() {
        try {
            dis = new DataInputStream(socket.getInputStream());
            while (true) {
                int init = dis.readInt();
                switch (init){
                    case 1:
                        upOnLineUsers();

                        break;
                    case 2:
                        // 接收群聊消息
                        getMsgtoWin();
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getMsgtoWin() throws Exception {
        String msg = dis.readUTF();
        win.smsContent.append(msg + "\n");
    }

    private void upOnLineUsers() throws Exception {
        int size = dis.readInt(); // 读取在线人数
        onLineUsers = new String[size];
        for (int i = 0; i < size; i++) {
            String nickname = dis.readUTF();
            onLineUsers[i] = nickname;
        }
        win.updateOnLineUsers(onLineUsers);
    }
}
