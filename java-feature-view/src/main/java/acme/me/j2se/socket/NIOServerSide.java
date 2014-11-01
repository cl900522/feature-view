package acme.me.j2se.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NIOServerSide {
    public static final int LISTEN_PORT = 10000;

    private ByteBuffer buffer = ByteBuffer.allocate(8);
    private IntBuffer intBuffer = buffer.asIntBuffer();
    private SocketChannel clientChannel = null;
    private ServerSocketChannel serverChannel = null;

    public void start() {
        try {
            openChannel();
            waitForConnection();
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }

    private void openChannel() throws IOException {
        serverChannel = ServerSocketChannel.open();
        serverChannel.socket().bind(new InetSocketAddress(LISTEN_PORT));
        System.out.println("服务器通道已经打开");
    }

    private void waitForConnection() throws IOException {
        while (true) {
            clientChannel = serverChannel.accept();
            if (clientChannel != null) {
                System.out.println("新的连接加入");
                processRequest();
                System.out.println("链接处理结束");
                clientChannel.close();
            }
        }
    }

    private void processRequest() throws IOException {
        buffer.clear();
        clientChannel.read(buffer);
        int result = intBuffer.get(0) + intBuffer.get(1);
        buffer.flip();
        buffer.clear();
        intBuffer.put(0, result);
        clientChannel.write(buffer);
    }

    public static void main(String[] args) {
        new NIOServerSide().start();
    }
}
