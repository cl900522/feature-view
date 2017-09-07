package acme.me.designpattern.brige;

public class XWindowImpl implements WindowImpl {
    public void deviceRedraw() {
        System.out.println("redraw as this device feature");

    }

    public void deviceRefresh() {
        System.out.println("refresh as this device feature");
    }

}
