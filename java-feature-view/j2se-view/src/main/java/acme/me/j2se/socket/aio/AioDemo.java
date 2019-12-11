package acme.me.j2se.socket.aio;

public class AioDemo {
    public static void main(String[] args) {
        Integer communicatePort = 8090;

        AioServerSide aioServerSide = new AioServerSide(communicatePort);
        aioServerSide.start();

        try {
            Thread.sleep(10 * 1000);
            System.out.println("Exit: 0");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 10000; i++) {
            AioClientSide aioClientSide = new AioClientSide("127.0.0.1", communicatePort);
            aioClientSide.send("GET_TIME");
        }

        try {
            Thread.sleep(20 * 1000);
            System.out.println("Exit: 0");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
