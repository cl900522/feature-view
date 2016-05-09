package acme.me.designpattern.state;

public class ReadyState implements State {

    public void handle() {
        System.out.println("I was processing your request...");
        System.out.println("Please wait...");
        System.out.println("It was successfully processed!");
    }
}
