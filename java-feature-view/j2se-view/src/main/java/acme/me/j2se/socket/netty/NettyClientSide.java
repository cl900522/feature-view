package acme.me.j2se.socket.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;

public class NettyClientSide {
    private static Logger logger = Logger.getLogger(NettyClientSide.class);

    private String host;
    private Integer port;

    public NettyClientSide(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    public void send(String msg) {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new InitHandler());

            ChannelFuture fu = bootstrap.connect(host, port).sync();
            logger.info("Success connected to server!");

            fu.channel().closeFuture().sync();
            logger.info("Client closed");
        } catch (InterruptedException e) {
            e.printStackTrace();
            group.shutdownGracefully();
        }
    }


    private class InitHandler extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
            socketChannel.pipeline().addLast(new StringDecoder());
            socketChannel.pipeline().addLast(new TimeClientHandler("GET_TIME\n"));
        }
    }

    private class TimeClientHandler extends ChannelInboundHandlerAdapter {
        private String msg;

        public TimeClientHandler(String msg) {
            this.msg = msg;
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) {
            for (int i = 0; i < 100; i++) {
                byte[] bytes = msg.getBytes();
                ByteBuf request = Unpooled.copiedBuffer(bytes);
                ctx.writeAndFlush(request);
            }
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            try {
                /*ByteBuf buf = (ByteBuf) msg;
                byte[] response = new byte[buf.readableBytes()];
                buf.readBytes(response);

                String res = new String(response, "UTF-8");*/
                String res = (String) msg;
                System.out.println("Got response: " + res);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
