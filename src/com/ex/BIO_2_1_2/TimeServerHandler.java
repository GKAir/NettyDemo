package com.ex.BIO_2_1_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author zhangweizhou
 * Email: zhangweizhou@wanhuchina.com
 * Date:  2018/3/1
 * Time:  16:39
 */
public class TimeServerHandler implements Runnable {

    private Socket socket;

    public TimeServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            in = new BufferedReader((new InputStreamReader(this.socket.getInputStream())));
            out = new PrintWriter(this.socket.getOutputStream(), true);
            String currentTime = null;
            String body = null;
            while (true) {
                //通过BufferReader读取一行，如果已经读取到了输入流的尾部，则返回值为null,
                //退出循环，如果读到了费控值，则对内容进行判断，如果请求消息为查询事件的指令
                //为QUERY TIME ORDER，则获取当前最新的系统时间，通过PrintWriter的println函数发送给客户端
                //退出循环
                body = in.readLine();
                if (body == null) {
                    break;
                }
                System.out.println("The time Server receive otder:" + body);
                currentTime = "QUERY TIME ORDER:".equalsIgnoreCase(body) ?
                        new java.util.Date(System.currentTimeMillis()).toString() :"BAD ORDER";
                System.out.println(currentTime);
                System.out.println(2);
                out.print(currentTime);
            }
        } catch (Exception e) {
            //以下代码为释放资源的代码
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (out != null) {
                out.close();
                out = null;
            }
            if (this.socket != null) {
                try {
                    this.socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                this.socket = null;
            }
            e.printStackTrace();
        } finally {

        }
    }
}
