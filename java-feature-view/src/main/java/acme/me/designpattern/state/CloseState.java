package acme.me.designpattern.state;

public class CloseState implements State {

    public void handle() {
        System.out.println("The connection was closed! So i cannot help you now!");
    }

}
