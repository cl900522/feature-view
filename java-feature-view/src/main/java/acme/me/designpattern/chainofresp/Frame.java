package acme.me.designpattern.chainofresp;

public class Frame extends Widget {

    protected Frame(Widget parent, Topic topic) {
        super(parent, topic);
    }

    public void doHelp() {
        System.out.println("I am a ##FRAME## and handling help!");
    }
}
