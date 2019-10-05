package com.lakeqiu.base.design.charpter14.eg;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 服务器
 * @author lakeqiu
 */
public class AppServer extends Thread {
    /**
     * 指定端口
     */
    private final int port;
    /**
     * 默认端口
     */
    private final static int DEFAULT_PORT = 12880;
    /**
     * 服务器是否处于启动状态
     */
    private volatile boolean start = true;
    /**
     * 用于处理任务的线程池
     */
    private ExecutorService services = Executors.newFixedThreadPool(10);

    private ServerSocket socket;

    private List<ClientHandler> clientHandlers = new ArrayList<>();

    public AppServer(){
        this(DEFAULT_PORT);
    }

    public AppServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try {
            // 创建服务器套接字
            this.socket = new ServerSocket(port);
            while (start){
                Socket accept = socket.accept();
                ClientHandler handler = new ClientHandler(accept);
                services.submit(handler);
                clientHandlers.add(handler);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.dispose();
        }

    }

    private void dispose() {
        System.out.println("dispose");
        this.clientHandlers.stream().forEach(ClientHandler::stop);
        this.services.shutdown();
    }

    public void shutdown() throws IOException {
        this.start = false;
        this.interrupt();
        this.socket.close();
    }

}
