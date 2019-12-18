package acme.me.j2se.io.socket.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOServerSide {
    private static final Logger logger = LoggerFactory.getLogger(NIOServerSide.class);
    public static final int LISTEN_PORT = 10000;

    private ServerSocketChannel serverChannel = null;
    private Selector selector = null;

    public void start() {
        try {
            openChannel();
            startHandler();
            startAcceptor();
        } catch (IOException e) {
            logger.error(e.toString());
        }
    }

    private void openChannel() throws IOException {
        serverChannel = ServerSocketChannel.open();
        serverChannel.socket().bind(new InetSocketAddress(LISTEN_PORT));
        serverChannel.configureBlocking(true);
        selector = Selector.open();
        System.out.println("服务器通道已经打开……");
    }

    private void startHandler() {
        Thread handler = new Thread() {
            public void run() {
                try {
                    handleRequest();
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error(e.getMessage());
                }
            }
        };
        handler.setDaemon(true);
        handler.start();
        System.out.println("处理器准备就绪……");
    }

    private void startAcceptor() {
        Thread acceptor = new Thread() {
            public void run() {
                try {
                    waitForConnection();
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
        };
        acceptor.start();
        System.out.println("接收器准备就绪，可以向服务器发送请求了……");
    }

    private void waitForConnection() throws IOException {
        while (true) {
            SocketChannel clientChannel = serverChannel.accept();
            if (clientChannel == null) {
                continue;
            } else {
                System.out.println("新的请求到来……");
                clientChannel.configureBlocking(false);
                Handler handler = wrapSocket(clientChannel);
                clientChannel.register(selector, SelectionKey.OP_READ, handler);
                selector.wakeup();
            }
        }
    }

    private void handleRequest() throws IOException, InterruptedException {
        while (true) {
            selector.select(100);
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> it = keys.iterator();

            while (it.hasNext()) {
                SelectionKey key = it.next();
                if (key.isReadable()) {
                    Handler handler = (Handler) key.attachment();
                    handler.parseRequest();
                    handler.getClient().register(selector, SelectionKey.OP_WRITE, handler);
                }
                if (key.isWritable()) {
                    Handler handler = (Handler) key.attachment();
                    handler.processRequest();
                    handler.getClient().close();
                }
                it.remove();
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
            inBuffer.clear();
            client.read(inBuffer);
            firstParam = inIntBuffer.get(0);
            secondParam = inIntBuffer.get(1);
            logger.debug("请求参数为： {},{}", firstParam, secondParam);
        }

        private void processRequest() throws IOException {
            outBuffer.flip();
            outBuffer.clear();
            outIntBuffer.put(0, firstParam + secondParam);
            client.write(outBuffer);
            logger.debug("处理结果为：{}", outIntBuffer.get());
        }

    }
}
