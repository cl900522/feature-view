package acme.me.designpattern.brige;

public class ApplicationWindow extends Window {

    public ApplicationWindow(){
        WindowImpl windowImpl = WindowImplFactory.instace().getWindowImpl();
        this.setWindowImpl(windowImpl);
    }
    
    @Override
    public void refeash() {
        this.getWindowImpl().deviceRefresh();
    }

    @Override
    public void setLocation(Coords location) {
        this.location = location;
    }

    @Override
    public void drawArea() {
        this.getWindowImpl().deviceRedraw();
    }

    @Override
    public void setSize(Size size) {
        this.size=size;
    }

}
