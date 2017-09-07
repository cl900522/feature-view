package acme.me.designpattern.brige;

public abstract class Window {
    private WindowImpl windowImpl;
    protected Coords location;
    protected Size size;

    public WindowImpl getWindowImpl(){
        return windowImpl;
    }
    
    public void setWindowImpl(WindowImpl windowImpl){
        this.windowImpl = windowImpl;
    }

    abstract public void refeash();
    abstract public void drawArea();
    abstract public void setLocation(Coords location);
    abstract public void setSize(Size size);
}
