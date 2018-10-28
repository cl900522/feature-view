package acme.me.designpattern.adapter;

public class TextView {
    private static final double X_PERCENT = 1.5;
    private static final double Y_PERCENT = 0.5;
    
    public void getOrigin(int x, int y){
        x *= X_PERCENT;
        y *= X_PERCENT;
    }

    public void getExtent(int width, int height){
        width *= Y_PERCENT;
        height *= Y_PERCENT;
    }

    public boolean isEmpty(){
        return false;
    }
}
