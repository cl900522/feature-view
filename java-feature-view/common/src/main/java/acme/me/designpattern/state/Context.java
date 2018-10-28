package acme.me.designpattern.state;

public class Context {
    private State state = new InitialState();
    public void setState(int i){
        switch(i){
            case 1:
                state = new ReadyState();
                break;
            case 2:
                state = new CloseState();
                break;
            default:
                break;
        }
    }
    public void request(){
        System.out.println("####Sending request to process:");
        state.handle();
    }
}
