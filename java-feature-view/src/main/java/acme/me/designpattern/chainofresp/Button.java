package acme.me.designpattern.chainofresp;

public class Button extends Widget {

    protected Button(Widget parent, Topic topic) {
        super(parent, topic);
    }
    public void HandleHelp(){
        if(hasHelp()){
            
        }else{
            super.handleHelp();
        }
    }
}
