package acme.me.designpattern.state;

public class StateView {
    public static void main(String[] args) throws InterruptedException{
        Context c = new Context();
        c.request();
        Thread.sleep(2000);
        c.setState(1);
        c.request();
        Thread.sleep(2000);
        c.setState(2);
        c.request();
    }
}
