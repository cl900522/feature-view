package acme.me.j2se.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSide {
    private static Socket socket;
    private static PrintWriter printWriter;
    private static BufferedReader bufferReader;

    public static void main(String[] args) {
        try {
            /**
             * 创建socket对象，并指明服务器的IP地址和端口号
             */
            socket = new Socket("127.0.0.1", ServerSide.LISTEN_PORT);
            /**
             * 得到socket发送数据的输出流;将字节流包装成字符流 向服务器发送数据
             */
            OutputStream outStream = socket.getOutputStream();
            printWriter = new PrintWriter(outStream);
            printWriter.println("I am the client, how are you? server");
            /**
             * 刷新流，确保数据能写到服务器
             */
            printWriter.flush();

            InputStream inStream = socket.getInputStream();
            bufferReader = new BufferedReader(new InputStreamReader(inStream));
            String incomeInfo = bufferReader.readLine();
            System.out.println("Message from Server:" + incomeInfo);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (printWriter != null) {
                    printWriter.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
