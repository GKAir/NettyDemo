package com.ex.BIO_2_1_2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author zhangweizhou
 * Email: zhangweizhou@wanhuchina.com
 * Date:  2018/3/1
 * Time:  16:29
 */
public class TimeServer {

    public static void main(String[] args) throws IOException {
        int port = 8900;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                //采用默认值
            }
        }
        ServerSocket serverSocket = null;
        try {
            //通过构造函数创建ServerSocket
            serverSocket = new ServerSocket(port);
            System.out.println("The time server is start in port:" + port);
            Socket socket = null;
            //通过一个无限循环来监听客户端的连接，如果没有客户端的接入，则主线程阻塞在ServerSocker的accept操作上
            while (true) {
                System.out.println(1);
                socket = serverSocket.accept();
                //如果有新的客户端接入的时候，执行这里，然后开始构造TimeServerHandler来处理这条Socket链路
                new Thread(new TimeServerHandler(socket)).start();
            }
        } finally {
            if (serverSocket != null) {
                System.out.println("The time server close");
                serverSocket.close();
                serverSocket = null;
            }
        }
    }
}
