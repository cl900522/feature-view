package acme.me.j2se.concurrent;

public class ThreadTest extends Thread implements Runnable {
    public ThreadTest(){
    }

    public ThreadTest(Runnable runn){
        super(runn);
    }

    @Override
    public void run() {
        System.out.println("In run");
        yield();
        System.out.println("Leaving run");
    }

    public static void main(String[] args) {
        int i=3;
        i=i++ + ++i + i++;
        System.out.println(i);

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("T1:In run");
                yield();
                System.out.println("T1:Leaving run");
            }
        }).start();

        new ThreadTest(new Runnable() {
            @Override
            public void run() {
                System.out.println("T2:In run");
                yield();
                System.out.println("T2:Leaving run");
            }
        }).start();

        new ThreadTest().start();

        byte b=2,e=3;//------1
        int f=b+e;//------2
        System.out.println(f);
        char m[][] = new char[2][2];
        char[][] n = new char[2][2];
    }
}
