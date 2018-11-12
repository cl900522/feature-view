package acme.me.sso.service.impl;

import acme.me.common.util.EncryptUtil;
import acme.me.sso.entity.UserInfo;
import acme.me.sso.rpc.ManagerServiceRpc;
import acme.me.sso.service.AuthService;
import acme.me.sso.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private ManagerServiceRpc managerServiceRpc;

    @Override
    public UserInfo login(String loginName, String inPwd) {
        UserInfo manager = new UserInfo();
        manager.setUserName("admin");
        manager.setPassword(EncryptUtil.md5("admin" + "admin123!@#"));
        if (EncryptUtil.md5(loginName + inPwd).equals(manager.getPassword())) {
            manager.setToken(createToken());
            return manager;
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
