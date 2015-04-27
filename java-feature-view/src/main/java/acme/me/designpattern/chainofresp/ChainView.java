package acme.me.designpattern.chainofresp;

public class ChainView {

    public static void main(String[] args) {
        Widget frame = new Frame(null, Topic.FRAME_HELP_TOPIC);
        Widget dialog = new Dialog(frame, Topic.DIALOG_HELP_TOPIC);
        Widget button = new Button(dialog, Topic.BUTTON_HELP_TOPIC);
        System.out.println("1.*********************************");
        button.handleHelp();
        System.out.println("2.*********************************");
        button.handleHelp(Topic.DIALOG_HELP_TOPIC);
        System.out.println("3.*********************************");
        button.handleHelp(Topic.BUTTON_HELP_TOPIC);
        System.out.println("4.*********************************");
        button.handleHelp(Topic.FRAME_HELP_TOPIC);
    }
}
