package acme.me.designpattern.chainofresp;

public abstract class HelpHandler {
    private HelpHandler successor;
    private Topic topic;

    public HelpHandler(HelpHandler successor, Topic topic) {
        this.successor = successor;
        this.topic = topic;
    }
    public boolean hasHelp(){
        return topic != Topic.NO_HELP_TOPIC;
    }
    public void handleHelp(){
        if(this.successor != null){
            this.successor.handleHelp();
        }
    }

    public HelpHandler getSuccessor() {
        return successor;
    }
    public void setSuccessor(HelpHandler successor) {
        this.successor = successor;
    }
}
