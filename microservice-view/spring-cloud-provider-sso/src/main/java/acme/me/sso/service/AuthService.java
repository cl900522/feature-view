package acme.me.sso.service;

public interface AuthService {
    String login(String loginName, String password);

    Boolean isValidTooken(String token);
}
