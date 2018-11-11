package acme.me.sso.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: cdchenmingxuan
 * @Date: 2018/11/6 14:34
 * @Description: microservice-view
 */
@Configuration
public class SSOConfiguration {

    public String redirct_url_format = "%s?token=%s";

    @Value("${sso.param.key.redirect_url}")
    public String param_key_redirect_url = "redirect_url";

    @Value("${sso.param.default_redirct_url}")
    public String default_redirct_url = "http://home.acme.org";

    @Value("${sso.cookie.domain}")
    public String sso_cookie_domain = "acme.org";

    @Value("${sso.cookie.key.token}")
    public String cooke_key_token = "token";

    @Value("${sso.cookie.key.tokenAge}")
    public Integer cooke_key_token_age = 30;

    @Value("${sso.session.key.redirect_url}")
    public String session_key_redirect_url = "redirect_rl";

}
