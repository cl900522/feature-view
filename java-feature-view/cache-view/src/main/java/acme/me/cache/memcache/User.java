package acme.me.cache.memcache;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    public String userName;
    public Date birthDate;
    public String password;
    public Long age = 1000L;
    public Boolean is = false;

    public Long age1 = 1000L;
    public Boolean is1 = false;
    public Long age2 = 1000L;
    public Boolean is2 = false;
    public Long age3 = 1000L;
    public Boolean is3 = false;
    public Long age4 = 1000L;
    public Boolean is5 = false;

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public Date getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Long getAge() {
        return age;
    }
    public void setAge(Long age) {
        this.age = age;
    }
    public Boolean getIs() {
        return is;
    }
    public void setIs(Boolean is) {
        this.is = is;
    }

}
