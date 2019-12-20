package acme.me.j2se.io.socket.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class AioClientSide {

    private Integer port;
    private String host;
    AsynchronousSocketChannel channel;

    public AioClientSide(String host, Integer port) {
        try {
            this.port = port;
            this.host = host;
            this.channel = AsynchronousSocketChannel.open();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void send(String command) {
        ConnectCompletionHandler connectHandler = new ConnectCompletionHandler(command);
        channel.connect(new InetSocketAddress(host, port), this, connectHandler);
    }

    private class ConnectCompletionHandler implements CompletionHandler<Void, AioClientSide> {
        private String sendMsg;

        ConnectCompletionHandler(String sendMsg) {
            this.sendMsg = sendMsg;
        }

        @Override
        public void completed(Void result, AioClientSide attachment) {
            byte[] bytes = sendMsg.getBytes();
            ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
            buffer.put(bytes);
            buffer.flip();

            attachment.channel.write(buffer, buffer, new WriteCompletionHandler(attachment.channel));
        }

        @Override
        public void failed(Throwable exc, AioClientSide attachment) {
        }

        private class WriteCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {

            private AsynchronousSocketChannel channel;

            WriteCompletionHandler(AsynchronousSocketChannel channel) {
                this.channel = channel;
            }

            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                if (attachment.hasRemaining()) {
                    channel.write(attachment, attachment, this);
                } else {
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    channel.read(byteBuffer, byteBuffer, new ReadCompletionHander(channel));
                }
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class ReadCompletionHander implements CompletionHandler<Integer, ByteBuffer> {
        private AsynchronousSocketChannel channel;

        public ReadCompletionHander(AsynchronousSocketChannel channel) {
            this.channel = channel;
        }

        @Override
        public void completed(Integer result, ByteBuffer attachment) {
            try {
                attachment.flip();
                byte[] body = new byte[attachment.remaining()];
                attachment.get(body);
                String response = new String(body, "UTF-8");
                System.out.println("Got response: " + response);
            } catch (Exception e) {

            }
        }

        @Override
        public void failed(Throwable exc, ByteBuffer attachment) {
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
