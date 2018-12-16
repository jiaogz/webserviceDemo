package cn.yun.socket;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * 客户端
 */
public class SocketClient {

    public static void main(String[] args) throws Exception {

        //控制台输入
        Scanner input = new Scanner(System.in);
        String str = input.nextLine();
        System.out.println("输入："+str);

        Socket socket = new Socket("127.0.0.1",80);
        //写出数据
        OutputStream outputStream = socket.getOutputStream();

        outputStream.write(str.getBytes());

        //获取服务端返回
        InputStream inputStream = socket.getInputStream();
        byte[] buffer=new byte[1024];
        int len=-1;
        len = inputStream.read(buffer);

        String getData=new String(buffer, 0, len);
        System.out.println("从服务端获取的数据:"+getData);

    }
}
