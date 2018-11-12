package acme.me.sso.action;

import acme.me.common.util.RSAUtils;
import acme.me.sso.config.SSOConfiguration;
import acme.me.sso.entity.UserInfo;
import acme.me.sso.exception.BussinessException;
import acme.me.sso.exception.BussinessExceptionEnum;
import acme.me.sso.service.AuthService;
import acme.me.sso.util.SecurityUtil;
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
import java.security.interfaces.RSAPrivateKey;

@Controller
public class AuthAction extends BaseAction {

    @Autowired
    private AuthService authService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
        String redirectUrl = getRequestParameter(request, SSOConfiguration.param_key_redirect_url);
        if (StringUtils.isEmpty(redirectUrl)) {
            redirectUrl = SSOConfiguration.default_redirct_url;
            map.put(SSOConfiguration.param_key_redirect_url, redirectUrl);
        }

        Cookie[] cookies = request.getCookies();
        String token = getCookeValue(cookies, SSOConfiguration.cookie_key_token);

        Boolean validToken = authService.isValidTooken(token);
        if (validToken) {
            response.sendRedirect(String.format(SSOConfiguration.redirct_url_format, redirectUrl, token));
            return null;
        }

        map.put(SSOConfiguration.param_pwd_salt, SecurityUtil.createNewSalt());
        map.put(SSOConfiguration.param_rsa_private, SecurityUtil.rsa_public_key);

        return "index";
    }

    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    @ResponseBody
    public Response login(HttpServletResponse response, ModelMap map,
                          @RequestParam("loginName") String loginName,
                          @RequestParam("password") String password,
                          @RequestParam("passwordSalt") String passwordSalt,
                          @RequestParam("redirectUrl") String redirectUrl) throws BussinessException, Exception {
        if (StringUtils.isEmpty(loginName) || StringUtils.isEmpty(password)) {
            return Response.fail(BussinessExceptionEnum.ERROR_00_01.getMessage() + "未提供登录凭证");
        }
        if (StringUtils.isEmpty(redirectUrl)) {
            return Response.fail(BussinessExceptionEnum.ERROR_00_01.getMessage() + "redirectUrl未提供");
        }
        Boolean validPwdSalt = SecurityUtil.isValidPwdSalt(passwordSalt);
        if (!validPwdSalt) {
            return Response.fail(BussinessExceptionEnum.ERROR_00_01.getMessage() + "passwordSalt无效");
        }

        String decryptPassword = null;
        try {
            RSAPrivateKey privateKey = RSAUtils.getPrivateKey(SecurityUtil.rsa_private_key);
            decryptPassword = RSAUtils.privateDecrypt(password, privateKey);
        } catch (Exception e) {
            return Response.fail("加密数据解析错误");
        }
        if (!StringUtils.hasText(decryptPassword) || decryptPassword.indexOf(passwordSalt) < 0) {
            return Response.fail("数据封装错误");
        }
        String oriPassword = decryptPassword.substring(0, decryptPassword.indexOf(passwordSalt));

        UserInfo userInfo = authService.login(loginName, oriPassword);
        if (userInfo != null && !StringUtils.isEmpty(userInfo.getToken())) {
            String token = userInfo.getToken();
            Cookie cookie = addOrUpdateCookie(response, SSOConfiguration.cookie_key_token, token);
            cookie.setDomain(SSOConfiguration.sso_cookie_domain);
            cookie.setMaxAge(SSOConfiguration.cooke_key_token_age);
            String toUrl = String.format(SSOConfiguration.redirct_url_format, redirectUrl, token);
            response.sendRedirect(toUrl);
            return Response.success(toUrl);
        } else {
            return Response.fail("账号或密码错误");
        }
    }
}

