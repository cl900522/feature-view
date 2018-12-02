package acme.me.sso.action;

import acme.me.common.action.BaseAction;
import acme.me.common.exception.BussinessException;
import acme.me.common.exception.BussinessExceptionEnum;
import acme.me.common.model.Response;
import acme.me.common.util.RSAUtils;
import acme.me.common.util.RegUtil;
import acme.me.sso.config.AccountConfiguration;
import acme.me.sso.config.SSOConfiguration;
import acme.me.sso.entity.AccountRegisterEntity;
import acme.me.sso.service.AccountRegisterService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.interfaces.RSAPrivateKey;

@Controller
@Api("用户注册与修改")
@RequestMapping("account")
public class AccountRegisterAction extends BaseAction {

    @Autowired
    private AccountRegisterService accountRegisterService;

    @ApiOperation(value = "账号注册", notes = "账号注册页")
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
        map.put(SSOConfiguration.param_rsa_private, SecurityUtil.rsa_public_key);

        return "account_register";
    }

    @ApiOperation(value = "验证并注册", notes = "验证并注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", value = "邮箱", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String")
    })
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Response register(@RequestParam("email") String email,
                             @RequestParam("password") String password) throws Exception {
        if (StringUtils.isEmpty(email)) {
            throw new BussinessException(BussinessExceptionEnum.ERROR_00_01, "email不存在");
        }
        if (!RegUtil.isEmail(email)) {
            throw new BussinessException(BussinessExceptionEnum.ERROR_00_03, "非邮箱");
        }
        if (StringUtils.isEmpty(password)) {
            throw new BussinessException(BussinessExceptionEnum.ERROR_00_01, "password不存在");
        }

        String decryptPassword = null;
        try {
            RSAPrivateKey privateKey = RSAUtils.getPrivateKey(SecurityUtil.rsa_private_key);
            decryptPassword = RSAUtils.privateDecrypt(password, privateKey);
        } catch (Exception e) {
            throw new BussinessException(BussinessExceptionEnum.ERROR_01_01, "加密数据解析错误");
        }

        if (!RegUtil.isPatternMatch(AccountConfiguration.passwordReg, decryptPassword)) {
            throw new BussinessException(BussinessExceptionEnum.ERROR_00_03, AccountConfiguration.passwodHint);
        }
        AccountRegisterEntity accountEntity = new AccountRegisterEntity();
        accountEntity.setEmail(email);
        accountEntity.setPassword(decryptPassword);
        accountRegisterService.registerAccount(accountEntity);

        return Response.success(null);
    }
}

