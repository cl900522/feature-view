package acme.me.designpattern.chainofresp;

public enum Topic {
    NO_HELP_TOPIC(0), BUTTON_HELP_TOPIC(1), DIALOG_HELP_TOPIC(2), FRAME_HELP_TOPIC(3);
    private int code;

    public int getCode() {
        return code;
    }

    Topic(int code) {
        this.code = code;
    }
}
