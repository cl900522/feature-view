package acme.me.designpattern.chainofresp;

public class Button extends Widget {

    protected Button(Widget parent, Topic topic) {
        super(parent, topic);
    }

    public void doHelp() {
        System.out.println("I am a ##BUTTON## and handling help!");
    }
}
