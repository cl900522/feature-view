package acme.me.designpattern.chainofresp;

public class Dialog extends Widget {
    protected Dialog(Widget parent, Topic topic) {
        super(parent, topic);
    }
    public void HandleHelp(){
        if(hasHelp()){
            
        }else{
            super.handleHelp();
        }
    }
}
