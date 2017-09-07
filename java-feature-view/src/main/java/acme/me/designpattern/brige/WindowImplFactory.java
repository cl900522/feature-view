package acme.me.designpattern.brige;

public class WindowImplFactory {
    private static WindowImplFactory instance;
    private WindowImplFactory(){

    }
    public static WindowImplFactory instace(){
        if(instance == null){
            instance = new WindowImplFactory();
        }
        return instance;
    }
    public WindowImpl getWindowImpl(){
        return new XWindowImpl();
    }
}
