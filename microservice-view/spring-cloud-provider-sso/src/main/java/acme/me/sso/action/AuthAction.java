package acme.me.sso.action;

import acme.me.sso.config.SSOConfiguration;
import acme.me.sso.exception.BussinessException;
import acme.me.sso.exception.BussinessExceptionEnum;
import acme.me.sso.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class AuthAction extends BaseAction {

    @Autowired
    private SSOConfiguration configuration;

    @Autowired
    private AuthService authService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(HttpServletRequest request, HttpServletResponse response, ModelMap map) throws IOException {
        String redirectUrl = getRequestParameter(request, configuration.param_key_redirect_url);
        if (StringUtils.isEmpty(redirectUrl)) {
            redirectUrl = configuration.default_redirct_url;
            map.put(configuration.param_key_redirect_url, redirectUrl);
        }

        Cookie[] cookies = request.getCookies();
        String token = getCookeValue(cookies, configuration.cooke_key_token);

        Boolean validTooken = authService.isValidTooken(token);
        if (validTooken) {
            response.sendRedirect(String.format(configuration.redirct_url_format, redirectUrl, token));
            return null;
        }

        return "index";
    }

    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    @ResponseBody
    public Response login(HttpServletResponse response, ModelMap map,
                          @RequestParam("loginName") String loginName,
                          @RequestParam("password") String password,
                          @RequestParam("redirect-url") String redirectUrl) throws BussinessException, IOException {
        if (StringUtils.isEmpty(loginName) || StringUtils.isEmpty(password)) {
            return Response.fail(BussinessExceptionEnum.ERROR_00_01.getMessage() + "未提供登录凭证");
        }
        if (StringUtils.isEmpty(redirectUrl)) {
            return Response.fail(BussinessExceptionEnum.ERROR_00_01.getMessage() + "redirect-url未提供");
        }

        String token = authService.login(loginName, password);
        if (!StringUtils.isEmpty(token)) {
            Cookie cookie = addOrUpdateCookie(response, configuration.cooke_key_token, token);
            cookie.setDomain(configuration.sso_cookie_domain);
            cookie.setMaxAge(configuration.cooke_key_token_age);
            response.sendRedirect(String.format(configuration.redirct_url_format, redirectUrl, token));
            return Response.success(null);
        } else {
            return Response.fail("账号或密码错误");
        }
    }
}

