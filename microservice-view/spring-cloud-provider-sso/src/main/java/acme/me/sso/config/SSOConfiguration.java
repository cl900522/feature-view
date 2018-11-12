package acme.me.sso.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: cdchenmingxuan
 * @Date: 2018/11/6 14:34
 * @Description: microservice-view
 */
@Configuration
public class SSOConfiguration {

    public static String redirct_url_format = "%s?token=%s";

    public static String param_key_redirect_url = "redirect_url";

    public static String param_rsa_private = "rsa_public_key";

    public static String param_pwd_salt = "pwd_salt";

    public static String default_redirct_url = "http://home.acme.org";

    public static String sso_cookie_domain = "acme.org";

    public static String cookie_key_token = "token";

    public static Integer cooke_key_token_age = 30;

    public static String session_key_redirect_url = "redirect_rl";

    @Value("${sso.param.key.redirect_url}")
    public void setParam_key_redirect_url(String param_key_redirect_url) {
        SSOConfiguration.param_key_redirect_url = param_key_redirect_url;
    }

    @Value("${sso.param.default_redirct_url}")
    public void setDefault_redirct_url(String default_redirct_url) {
        SSOConfiguration.default_redirct_url = default_redirct_url;
    }

    @Value("${sso.cookie.domain}")
    public void setSso_cookie_domain(String sso_cookie_domain) {
        SSOConfiguration.sso_cookie_domain = sso_cookie_domain;
    }

    @Value("${sso.cookie.key.token}")
    public void setCooke_key_token(String cooke_key_token) {
        SSOConfiguration.cookie_key_token = cooke_key_token;
    }

    @Value("${sso.cookie.key.tokenAge}")
    public void setCooke_key_token_age(Integer cooke_key_token_age) {
        SSOConfiguration.cooke_key_token_age = cooke_key_token_age;
    }

    @Value("${sso.session.key.redirect_url}")
    public void setSession_key_redirect_url(String session_key_redirect_url) {
        SSOConfiguration.session_key_redirect_url = session_key_redirect_url;
    }

    @Value("${sso.param.key.rsa_private}")
    public void setParamRsaPrivate(String param_rsa_private) {
        SSOConfiguration.param_rsa_private = param_rsa_private;
    }

    @Value("${sso.param.key.pwd_salt}")
    public void setParamPwdSalt(String param_pwd_salt) {
        SSOConfiguration.param_pwd_salt = param_pwd_salt;
    }
}
