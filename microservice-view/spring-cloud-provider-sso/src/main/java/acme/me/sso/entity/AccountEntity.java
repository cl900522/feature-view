package acme.me.sso.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountEntity {

    String email;
    String phoneNumber;
    String loginName;
    String password;
    String pwdSalt;
    String token;

}
