package acme.me.designpattern.template;

public abstract class Employee {
    private double basicSalary;
    private double reward;
    private double punishment;

    public Employee(double salary) {
        this.basicSalary = salary;
    }

    public double calculateSalary() {
        return getBasicSlary() + getCommision() + getReward() - getPunishment();
    }

    public double getBasicSlary() {
        return basicSalary;
    }

    public abstract double getCommision();

    public double getReward() {
        return reward;
    }

    public double getPunishment() {
        return punishment;
    }

    public void setReward(double reward) {
        this.reward = reward;
    }

    public void setPunishment(double punishment) {
        this.punishment = punishment;
    }
}
