package acme.me.designpattern.template;

public class TemplateView {

    public static void main(String[] args) {
        Employee manager = new Manager(3000);
        manager.setReward(100);
        manager.setPunishment(600);
        System.out.println(manager.calculateSalary());
    }

}
