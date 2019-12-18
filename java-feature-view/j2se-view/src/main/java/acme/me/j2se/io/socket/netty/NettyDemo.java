package acme.me.j2se.io.socket.netty;

public class NettyDemo {
    public static void main(String[] args) {
        startServer();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // sendClientRequest();
    }

    private static void sendClientRequest() {
        Thread thread = new Thread(() -> {
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

    private static void startServer() {
        Thread thread = new Thread(() -> {
            NettyServerSide server = new NettyServerSide(80);
            server.start();
        });
        thread.setDaemon(false);
        thread.start();
    }
}
