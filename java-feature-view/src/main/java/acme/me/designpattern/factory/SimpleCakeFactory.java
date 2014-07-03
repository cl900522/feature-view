package acme.me.designpattern.factory;


public class SimpleCakeFactory {
    public Cake createStrawberyCake(){
        return new StrawberyCake();
    }
    
    public Cake createMilkyCake(){
        return new MilkyCake();
    }
}
