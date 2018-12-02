package acme.me.sso.service.impl;

import acme.me.common.util.EncryptUtil;
import acme.me.sso.entity.AccountEntity;
import acme.me.sso.rpc.ManagerServiceRpc;
import acme.me.sso.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private ManagerServiceRpc managerServiceRpc;

    @Override
    public AccountEntity login(String loginName, String inPwd) {
        AccountEntity account = new AccountEntity();
        account.setLoginName("admin");
        account.setPassword(EncryptUtil.md5("admin" + "admin123!@#"));
        if (EncryptUtil.md5(loginName + inPwd).equals(account.getPassword())) {
            account.setToken(createToken());
            return account;
        }

        return null;
    }

    Set<String> tokens = new HashSet<String>();

    private String createToken() {
        String token = UUID.randomUUID().toString();
        tokens.add(token);
        return token;
    }

    @Override
    public Boolean isValidTooken(String token) {
        if (StringUtils.isEmpty(token)) {
            return Boolean.FALSE;
        }
        return tokens.contains(token);
    }
}
