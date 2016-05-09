package acme.me.designpattern.chainofresp;

public abstract class HelpHandler {
    private HelpHandler successor;
    private Topic topic = Topic.NO_HELP_TOPIC;

    public HelpHandler(HelpHandler successor, Topic topic) {
        this.successor = successor;
        this.topic = topic;
    }

    public HelpHandler getSuccessor() {
        return successor;
    }

    public void setSuccessor(HelpHandler successor) {
        this.successor = successor;
    }

    public boolean shouldHandle(Topic t) {
        return topic == t;
    }

    public boolean hasHelp() {
        return this.topic != Topic.NO_HELP_TOPIC;
    }

    public void doHelp() {
    }

    public void handleHelp() {
        if (hasHelp()) {
            this.doHelp();;
        }
        if (this.successor != null) {
            this.successor.handleHelp();
        }
    }

    public void handleHelp(Topic t) {
        if (shouldHandle(t)) {
            this.doHelp();;
        }
        if (this.successor != null) {
            this.successor.handleHelp(t);
        }
    }
}
