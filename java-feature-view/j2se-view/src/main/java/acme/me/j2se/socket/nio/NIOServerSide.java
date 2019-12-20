package acme.me.j2se.socket.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NIOServerSide {
    private static final Logger logger = LoggerFactory.getLogger(NIOServerSide.class);
    public static final int LISTEN_PORT = 10000;

    private ServerSocketChannel serverChannel = null;

    private Selector selector;
    private Selector serverSelector;

    private Boolean blocking = false;

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

        selector = Selector.open();
        serverSelector = Selector.open();


        // 如果阻塞模式，则不可以使用select来注册关注的事件
        if (blocking) {
            serverChannel.configureBlocking(true);
        } else {
            serverChannel.configureBlocking(false);
            serverChannel.register(serverSelector, SelectionKey.OP_ACCEPT);
        }

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
        handler.setDaemon(false);
        handler.start();
        System.out.println("处理器准备就绪……");
    }

    private void startAcceptor() {
        Thread acceptor = new Thread() {
            public void run() {
                try {
                    waitForConnection();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        acceptor.start();
        System.out.println("接收器准备就绪，可以向服务器发送请求了……");
    }

    private void waitForConnection() throws IOException {
        if (blocking) {
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
        } else {
            while (true) {
                serverSelector.select(100);
                Set<SelectionKey> keys = serverSelector.selectedKeys();
                Iterator<SelectionKey> it = keys.iterator();

                while (it.hasNext()) {
                    SelectionKey key = it.next();
                    if (key.isAcceptable()) {
                        SelectableChannel channel = key.channel();
                        ServerSocketChannel serverChannel = (ServerSocketChannel) channel;
                        SocketChannel clientChannel = serverChannel.accept();
                        if (clientChannel != null) {
                            clientChannel.configureBlocking(false);
                            Handler handler = wrapSocket(clientChannel);
                            clientChannel.register(selector, SelectionKey.OP_READ, handler);
                            System.out.println("新的请求到来……");
                        }
                        it.remove();
                    }
                }
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
