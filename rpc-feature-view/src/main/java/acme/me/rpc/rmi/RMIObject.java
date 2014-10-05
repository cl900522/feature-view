package acme.me.rpc.rmi;

import java.io.Serializable;

/**
 * it must implements Serializable to be able to be transfered on net
 * @author 明轩
 */
public class RMIObject implements Serializable {
    private static final long serialVersionUID = 1342670546183564958L;
    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String toString() {
        return "Name is " + name + "; age is:" + age + "。";
    }

}
