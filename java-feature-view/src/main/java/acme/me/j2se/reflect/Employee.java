package acme.me.j2se.reflect;

import java.util.Date;

public class Employee {
    private String name;
    private int workAge;
    private Date joinDate;
    private double monthSalary;

    public double getMonthSalary() {
        return monthSalary;
    }

    public void setMonthSalary(double monthSalary) {
        this.monthSalary = monthSalary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWorkAge() {
        return workAge;
    }

    public void setWorkAge(int workAge) {
        this.workAge = workAge;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public double getYearSalary(){
        return this.getMonthSalary() * 12;
    }
}
