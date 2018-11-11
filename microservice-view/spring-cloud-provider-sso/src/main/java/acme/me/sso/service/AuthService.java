package acme.me.sso.service;

import acme.me.sso.entity.UserInfo;

public interface AuthService {
    UserInfo login(String loginName, String password, String passwordSalt);

    Boolean isValidTooken(String token);
}
