package acme.me.j2se.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NIOServerSide {
    private static final Logger logger = LoggerFactory.getLogger(NIOServerSide.class);
    public static final int LISTEN_PORT = 10000;

    private ServerSocketChannel serverChannel = null;
    private Selector selector = null;

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
        serverChannel.configureBlocking(false);
        selector = Selector.open();
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务器通道已经打开");
    }

    private void waitForConnection() throws IOException {
        while (selector.select() > 0) {
            Set<SelectionKey> keys = selector.keys();
            for (SelectionKey key : keys) {
                if (key.isAcceptable()) {
                    SocketChannel clientChannel = ((ServerSocketChannel) key.channel()).accept();
                    if (clientChannel == null)
                        continue;
                    System.out.println("新的请求到来……");
                    clientChannel.configureBlocking(false);
                    Handler handler = wrapSocket(clientChannel);
                    clientChannel.register(selector, SelectionKey.OP_READ, handler);
                }
                if (key.isReadable()) {
                    Handler handler = (Handler) key.attachment();
                    handler.parseRequest();
                    handler.getClient().register(selector, SelectionKey.OP_WRITE, handler);
                }
                if (key.isWritable()) {
                    Handler handler = (Handler) key.attachment();
                    handler.processRequest();
                }
            }
        }
    }

    private Handler wrapSocket(SocketChannel clientChannel) {
        Handler handler = new Handler();
        handler.setClient(clientChannel);
        return handler;
    }

    public static void main(String[] args) {
        new NIOServerSide().start();
    }

    private class Handler {

        private ByteBuffer inBuffer = ByteBuffer.allocate(10);
        private IntBuffer inIntBuffer = inBuffer.asIntBuffer();
        private ByteBuffer outBuffer = ByteBuffer.allocate(10);
        private IntBuffer outIntBuffer = outBuffer.asIntBuffer();
        private SocketChannel client;
        private int firstParam;
        private int secondParam;

        public SocketChannel getClient() {
            return client;
        }

        public void setClient(SocketChannel client) {
            this.client = client;
        }

        private void parseRequest() throws IOException {
            System.out.println("读取请求参数……");
            inBuffer.clear();
            client.read(inBuffer);
            firstParam = inIntBuffer.get(0);
            secondParam = inIntBuffer.get(1);
            logger.info("Params are {},{}", firstParam, secondParam);
        }

        private void processRequest() throws IOException {
            System.out.println("处理请求并返回……");
            outBuffer.flip();
            outBuffer.clear();
            outIntBuffer.put(0, firstParam + secondParam);
            client.write(outBuffer);
        }

    }
}
