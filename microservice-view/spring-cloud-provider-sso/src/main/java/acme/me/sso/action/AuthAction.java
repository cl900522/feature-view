package acme.me.sso.action;

import acme.me.sso.config.SSOConfiguration;
import acme.me.sso.entity.UserInfo;
import acme.me.sso.exception.BussinessException;
import acme.me.sso.exception.BussinessExceptionEnum;
import acme.me.sso.service.AuthService;
import acme.me.sso.util.RSAUtils;
import acme.me.sso.util.SecurityUtil;
import org.apache.commons.codec.binary.Base64;
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
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

@Controller
public class AuthAction extends BaseAction {

    @Autowired
    private SSOConfiguration configuration;

    @Autowired
    private AuthService authService;

    private String publicKey = null;
    private String privateKey = null;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
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

        Map<String, String> stringObjectMap = RSAUtils.createKeys(1024);
        publicKey = stringObjectMap.get("publicKey");
        privateKey = stringObjectMap.get("privateKey");
        RSAPublicKey publicKey = RSAUtils.getPublicKey(this.publicKey);
        this.publicKey = Base64.encodeBase64String(publicKey.getEncoded());

        //publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC7vGHzK5TdMC2tcnV3xTN-regjGYLM46--ew7a-kMEGFZh-697zecsxiJqjJWqfRP0KT33ORMCLZgHWbXw3MzLgjY0XSgRUu1EF0EiUvMnAa5ZCA1a4QyBAJHrkEGJvi1ipf0XMCa_zP1I-8X_lEeqa5ZtHU3NeV43D2U5dHu1twIDAQAB";//stringObjectMap.get("privateKey");
        //privateKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBALu8YfMrlN0wLa1ydXfFM36t6CMZgszjr757Dtr6QwQYVmH7r3vN5yzGImqMlap9E_QpPfc5EwItmAdZtfDczMuCNjRdKBFS7UQXQSJS8ycBrlkIDVrhDIEAkeuQQYm-LWKl_RcwJr_M_Uj7xf-UR6prlm0dTc15XjcPZTl0e7W3AgMBAAECgYEAkb8zj_SSspGXtKM6nQxE6SEcKjvA904Af3HL26cU5hX64kDiQMxen70GtQ-FlgFav2BRUiBGKJe7AmHcRS0186SCXZMXfMWPOL_RAGWeJYyNKg2DEWmlZZo9rk-DRRedikW8ywQYfdhIExi4UvSJh3yxU3dUMmY-G4jPyql0eDkCQQD9-urqm4R3YfY6Int84R55WnEaPMu7sBBY-oeiezdEX9gVpwl7hiQHPOJNe9tVUv77QJqDSy1dDJd2WcgMI727AkEAvTqY34WcxvSB1uRVw8xUmyHQW5js_aDAiYvs-2rV58YyHi9lvPEkyaGcdYGqlC14fkib6RKXhku1G6wK4jJqNQJBAIDKSW-4dv7W1TZ7n-UefwaIn0vvwlwlltSYDkmNc-QfOgMY1g0mY4SsEhG6mel1kck05GVprNI_fVrNgkztntsCQDd-u96xvKzMgFeZV124ywdD_2sdikNHpa-xDaseReVXRtN7awbiYbuQXeZIRNJ4E2JVwQO1pCKKiW64Iej8iBECQQD6ZQBMo0L4a--o0LL9aWHoNUd0ogHFcpzmytKBeI1VhdwCQrfBlbrn_04WzDfQ1WaCCVADBkFnm8vrf0Z1-trH";//stringObjectMap.get("publicKey");
        map.put("pwd_salt", SecurityUtil.createNewSalt(32));
        map.put("publicKey", this.publicKey);

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

        RSAPrivateKey privateKey = RSAUtils.getPrivateKey(this.privateKey);
        String decryptPassword = RSAUtils.privateDecrypt(password, privateKey);
        String oriPassword = decryptPassword.replace(passwordSalt, "");

        UserInfo userInfo = authService.login(loginName, password, passwordSalt);
        if (userInfo != null && !StringUtils.isEmpty(userInfo.getToken())) {
            String token = userInfo.getToken();
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

