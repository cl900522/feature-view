package acme.me.j2se.io.socket.aio;

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
            serverSocketChannel.bind(new InetSocketAddress(this.port));
            logger.info("Success open aio server at port->" + port);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Fail to start aio server at port->" + port, e);
        }

    }

    public void start() {
        serverSocketChannel.accept(serverSocketChannel, new AcceptCompletionHandler());
    }

    /**
     * 接受数据完成
     */
    private class AcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, AsynchronousServerSocketChannel> {

        @Override
        public void completed(AsynchronousSocketChannel result, AsynchronousServerSocketChannel attachment) {
            attachment.accept(attachment, this);
            logger.info("thread [" + Thread.currentThread().getName() + "] accepted.->"+ this.toString());


            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            result.read(byteBuffer, byteBuffer, new ReactCompletionHandler(result));

        }

        @Override
        public void failed(Throwable result, AsynchronousServerSocketChannel attachment) {

        }
    }


    /**
     * 响应接受数据并处理返回
     */
    private class ReactCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {
        AsynchronousSocketChannel channel;

        ReactCompletionHandler(AsynchronousSocketChannel asynchronousSocketChannel) {
            this.channel = asynchronousSocketChannel;
        }

        @Override
        public void completed(Integer i, ByteBuffer byteBuffer) {
            try {
                byteBuffer.flip();
                byte[] body = new byte[byteBuffer.remaining()];
                byteBuffer.get(body);
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

            channel.write(writeBuffer, writeBuffer, new WriteCompletionHandler(channel));
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

    private class WriteCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {
        private AsynchronousSocketChannel channel;

        public WriteCompletionHandler(AsynchronousSocketChannel channel) {
            this.channel = channel;
        }

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
    }

}
