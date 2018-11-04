package acme.me.user.manager.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Manager {

    @Id
    private String name;
    private String pwd;
    private String address;

    public Manager() {

    }

    public Manager(String name, String pwd, String address) {
        super();
        this.name = name;
        this.pwd = pwd;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
