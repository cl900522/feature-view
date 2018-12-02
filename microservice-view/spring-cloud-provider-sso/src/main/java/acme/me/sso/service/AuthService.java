package acme.me.sso.service;

import acme.me.sso.entity.AccountEntity;

public interface AuthService {
    AccountEntity login(String loginName, String password);

    Boolean isValidTooken(String token);
}
