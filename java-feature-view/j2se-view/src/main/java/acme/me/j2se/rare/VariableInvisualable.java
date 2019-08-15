package acme.me.j2se.rare;

/**
 * @author: cdchenmingxuan
 * @date: 2019/8/15 09:30
 * @description: java-feature-view
 */
public class VariableInvisualable {

    public static int v = 0;

    public static void main(String[] args) throws InterruptedException {
        ThreadA threadA = new ThreadA();
        ThreadB threadB = new ThreadB();
        new Thread(threadA, "threadA").start();
        Thread.sleep(100);//为了保证threadA比threadB先启动，sleep一下
        new Thread(threadB, "threadB").start();
    }

    static class ThreadA extends Thread {
        public void run() {
            while (true) {
                if (v == 1) {
                    //永远无法进入并输出结果
                    System.out.println(Thread.currentThread().getName() + " : v is " + v);
                    break;
                }
            }
        }
    }

    static class ThreadB extends Thread {
        public void run() {
            v++;
            System.out.println(Thread.currentThread().getName() + " : v is " + v);
        }
    }
}
