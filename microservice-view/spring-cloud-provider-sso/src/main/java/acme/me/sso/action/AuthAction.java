package acme.me.sso.action;

import acme.me.common.model.Response;
import acme.me.common.util.RSAUtils;
import acme.me.common.action.BaseAction;
import acme.me.sso.config.SSOConfiguration;
import acme.me.sso.entity.AccountEntity;
import acme.me.common.exception.BussinessException;
import acme.me.common.exception.BussinessExceptionEnum;
import acme.me.sso.service.AuthService;
import acme.me.sso.util.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@Api("用户验证服务接口")
public class AuthAction extends BaseAction {

    @Autowired
    private AuthService authService;

    @ApiOperation(value="单点登录页", notes="跳转登录页或者跳转成功页")
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

    @ApiOperation(value="验证登录", notes="登录验证的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginName", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "passwordSalt", value = "密码加密的盐", required = true, dataType = "String"),
            @ApiImplicitParam(name = "redirectUrl", value = "跳转地址", required = true, dataType = "String")
    })
    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    @ResponseBody
    public Response login(HttpServletResponse response,
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

        AccountEntity accountEntity = authService.login(loginName, oriPassword);
        if (accountEntity != null && !StringUtils.isEmpty(accountEntity.getToken())) {
            String token = accountEntity.getToken();
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

