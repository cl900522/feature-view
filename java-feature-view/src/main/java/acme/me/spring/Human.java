package acme.me.spring;

public class Human implements Sleepable {

    @Override
    public void sleep() {
        System.out.println("睡觉了！梦中自有颜如玉！");
    }

}
