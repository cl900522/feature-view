package acme.me.designpattern.chainofresp;

public class Dialog extends Widget {
    protected Dialog(Widget parent, Topic topic) {
        super(parent, topic);
    }

    public void doHelp() {
        System.out.println("I am a ##DIALOG## and handling help!");
    }
}
