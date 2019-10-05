package com.lakeqiu.base.design.charpter14.eg;

import java.io.*;
import java.net.Socket;

/**
 * 负责与客户端的交流工作
 * @author lakeqiu
 */
public class ClientHandler implements Runnable {
    private final Socket socket;
    /**
     * 判断是否正在运行
     */
    private volatile boolean running = true;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    /**
     * 与客户端进行交流
     */
    @Override
    public void run() {
        try (InputStream is = socket.getInputStream();
             OutputStream os = socket.getOutputStream();
             BufferedReader br = new BufferedReader(new InputStreamReader(is));
             PrintWriter pw = new PrintWriter(os);
             ) {
            while (running){
                String message = br.readLine();
                if (message.isEmpty()){
                    break;
                }
                System.out.println("Come from client > " + message);
                pw.println("echo " + message + "\n");
                pw.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
            // 将running设置为不运行状态
            this.running = false;
        }finally {

        }
    }

    public void stop(){
        // 如果已经关闭了
        if (!running){
            // 直接返回
            return;
        }

        this.running = false;
        try {
            // 关闭套接字
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
