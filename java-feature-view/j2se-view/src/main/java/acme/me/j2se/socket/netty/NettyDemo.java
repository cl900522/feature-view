package acme.me.j2se.socket.netty;

public class NettyDemo {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            NettyServerSide server = new NettyServerSide(80);
            server.start();
        });
        thread.setDaemon(false);
        thread.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread = new Thread(()-> {
            NettyClientSide client = new NettyClientSide("127.0.0.1", 80);
            client.send("");
        });
        thread.setDaemon(false);
        thread.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
