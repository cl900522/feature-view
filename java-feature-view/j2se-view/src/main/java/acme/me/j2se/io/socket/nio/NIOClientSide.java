package acme.me.j2se.socket.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.SocketChannel;

public class NIOClientSide {
    private static final int FIRST_SED = 100;
    private static final int SECOND_SED = 349;

    private ByteBuffer buffer = ByteBuffer.allocate(8);
    private IntBuffer intBuffer;
    private SocketChannel channel;

    public NIOClientSide() {
        intBuffer = buffer.asIntBuffer();
    } // SumClient constructor

    public int getSum() {
        int result = 0;
        try {
            channel = connect();
            sendSumRequest();
            result = receiveResponse();
        } catch (IOException e) {
            System.err.println(e.toString());
        } finally {
            if (channel != null) {
                try {
                    channel.close();
                } catch (IOException e) {
                }
            }
        }
        return result;
    }

    private SocketChannel connect() throws IOException {
        InetSocketAddress socketAddress = new InetSocketAddress("localhost", NIOServerSide.LISTEN_PORT);
        return SocketChannel.open(socketAddress);
    }

    private void sendSumRequest() throws IOException {
        int first = (int) (FIRST_SED * Math.random());
        int second = (int) (SECOND_SED * Math.random());
        buffer.flip();
        buffer.clear();
        intBuffer.put(0, first);
        intBuffer.put(1, second);
        channel.write(buffer);
        System.out.println("发送加法请求 " + first + "+" + second);
    }

    private int receiveResponse() throws IOException {
        buffer.clear();
        channel.read(buffer);
        return intBuffer.get(0);
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 100; j++) {
                Thread request = new Thread() {
                    public void run() {
                        NIOClientSide sumClient = new NIOClientSide();
                        System.out.println("加法结果为 :" + sumClient.getSum());
                    }
                };
                request.start();
            }
        }
        Thread.sleep(2000);
    }
}
