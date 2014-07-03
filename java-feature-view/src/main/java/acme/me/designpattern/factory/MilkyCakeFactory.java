package acme.me.designpattern.factory;


public class MilkyCakeFactory implements AbstractCakeFactory {

    public Cake createCake() {
        return new MilkyCake();
    }

}
