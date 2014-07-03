package acme.me.designpattern.factory;


public class StrawberyCakeFactory implements AbstractCakeFactory {

    public Cake createCake() {
        return new StrawberyCake();
    }

}
