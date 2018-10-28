package acme.me.j2se.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSide {
    public static final Integer LISTEN_PORT = 3081;
    private static Socket listenSocket;
    private static BufferedReader bufferReader;
    private static PrintWriter printWriter;

    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(LISTEN_PORT);
            System.out.println("I am listening the port: " + LISTEN_PORT);
            /**
             * 监听服务器端口，一旦有数据发送过来，那么就将数据封装成socket对象
             * 如果没有数据发送过来，那么这时处于线程阻塞状态，不会向下继续执行
             */
            while (true) {
                listenSocket = server.accept();
                System.out.println("Client info：" + listenSocket.getRemoteSocketAddress());
                /**
                 * 从socket中得到读取流，该流中有客户端发送过来的数据; InputStreamReader将字节流转化为字符流;
                 * 读取客户端数据;
                 */
                InputStream inStream = listenSocket.getInputStream();
                bufferReader = new BufferedReader(new InputStreamReader(inStream));
                String incomeInfo = bufferReader.readLine();
                System.out.println("Message from client：" + incomeInfo);
                /**
                 * 返回数据
                 */
                OutputStream outStream = listenSocket.getOutputStream();
                printWriter = new PrintWriter(outStream);
                printWriter.println("I am the local server, i am fine! and you?");
                printWriter.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (printWriter != null) {
                    printWriter.close();
                }
                if (bufferReader != null) {
                    bufferReader.close();
                }
                if (listenSocket != null) {
                    listenSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
