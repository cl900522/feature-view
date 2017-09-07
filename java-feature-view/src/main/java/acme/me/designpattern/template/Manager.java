package acme.me.designpattern.template;

public class Manager extends Employee {

    public Manager(double salary){
        super(salary);
    }
    @Override
    public double getCommision() {
        return this.getBasicSlary() * 1.5;
    }

}
