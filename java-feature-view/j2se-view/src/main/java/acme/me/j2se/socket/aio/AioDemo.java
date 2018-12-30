package acme.me.j2se.socket.aio;

public class AioDemo {
    public static void main(String[] args) {
        AioServerSide aioServerSide = new AioServerSide(80);
        aioServerSide.start();

        for (int i = 0; i < 10000; i++) {
            AioClientSide aioClientSide = new AioClientSide("127.0.0.1", 80);
            aioClientSide.send("GET_TIME");
        }

        try {
            Thread.sleep(3 * 1000);
            System.out.println("Exit: 0");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
