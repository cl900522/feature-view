package acme.me.designpattern.state;

public class InitialState implements State {

    public void handle() {
        System.out.println("The proxy has just initialized, please waite a moment!");
    }

}
