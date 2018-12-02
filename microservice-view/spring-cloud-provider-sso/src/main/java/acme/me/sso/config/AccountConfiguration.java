package acme.me.sso.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountConfiguration {

    public static String passwordReg = "";

    public static String passwodHint = "密码必须大于6个字符,且同时包含大小写、数字和特殊字符";

    @Value("${account.password_reg}")
    public void setPasswordReg(String passwordReg) {
        AccountConfiguration.passwordReg = passwordReg;
    }

    @Value("${account.password_hint}")
    public void setPasswodHint(String passwodHint) {
        AccountConfiguration.passwodHint = passwodHint;
    }
}
