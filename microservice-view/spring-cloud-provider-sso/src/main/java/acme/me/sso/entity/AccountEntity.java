package acme.me.sso.entity;

import acme.me.common.model.DbEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountEntity extends DbEntity {
    String email;
    String phoneNumber;
    String loginName;

    String password;
    String pwdSalt;
    String token;
}
