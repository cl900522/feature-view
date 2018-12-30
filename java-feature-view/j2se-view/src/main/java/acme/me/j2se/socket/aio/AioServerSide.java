package acme.me.j2se.socket.aio;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Date;

public class AioServerSide {
    private static Logger logger = Logger.getLogger(AioServerSide.class);
    private AsynchronousServerSocketChannel serverSocketChannel;
    private Integer port;

    public AioServerSide(Integer port) {
        this.port = port;

        try {
            serverSocketChannel = AsynchronousServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(port));
            logger.info("Success open aio server at port->" + port);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Fail to start aio server at port->" + port, e);
        }

    }

    public void start() {
        serverSocketChannel.accept(this, new AcceptCompletionHandler());
    }

    private class AcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, AioServerSide> {

        @Override
        public void completed(AsynchronousSocketChannel result, AioServerSide attachment) {
            attachment.serverSocketChannel.accept(attachment, this);
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            result.read(byteBuffer, byteBuffer, new ReacCompletionHandler(result));
        }

        @Override
        public void failed(Throwable result, AioServerSide attachment) {

        }
    }


    private class ReacCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {
        AsynchronousSocketChannel channel;

        ReacCompletionHandler(AsynchronousSocketChannel asynchronousSocketChannel) {
            if (this.channel == null) {
                this.channel = asynchronousSocketChannel;
            }
        }

        @Override
        public void completed(Integer i, ByteBuffer serverSide) {
            try {
                serverSide.flip();
                byte[] body = new byte[serverSide.remaining()];
                serverSide.get(body);
                String order = new String(body, "UTF-8");
                System.out.println("Got command: " + order);
                if ("GET_TIME".equalsIgnoreCase(order)) {
                    doWrite(new Date().toString());
                } else {
                    doWrite("Not supported command");
                }
            } catch (Exception e) {
                doWrite("Server error");
            }
        }

        private void doWrite(String response) {
            byte[] bytes = response.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();

            channel.write(writeBuffer, writeBuffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    if (attachment.hasRemaining()) {
                        channel.write(attachment, attachment, this);
                    }
                }

                @Override
                public void completed(Integer result, ByteBuffer attachment) {
                    try {
                        channel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

        }

        @Override
        public void failed(Throwable exc, ByteBuffer serverSide) {
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
