package acme.me.sso.dao;

import acme.me.sso.entity.AccountRegisterEntity;
import org.apache.ibatis.annotations.Insert;

public interface AccountRegisterMapper {
    @Insert("insert into account_register(email, password, pwd_salt, status, create_time, update_time) values(#{email}, #{password}, #{pwdSalt}, #{status}, #{createTime}, #{updateTime})")
    public int insertAccount(AccountRegisterEntity account);
}
