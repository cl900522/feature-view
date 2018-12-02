package acme.me.sso.entity;

import acme.me.common.model.DbEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountRegisterEntity extends DbEntity {
    String email;

    String password;
    String pwdSalt;
    String channel;//来源渠道

    RegisterStatus status;
    String failReason;
}
