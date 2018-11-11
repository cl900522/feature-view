package acme.me.sso.service.impl;

import acme.me.sso.entity.UserInfo;
import acme.me.sso.rpc.ManagerServiceRpc;
import acme.me.sso.service.AuthService;
import acme.me.sso.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private ManagerServiceRpc managerServiceRpc;

    @Override
    public UserInfo login(String loginName, String inPwd, String passwordSalt) {
        UserInfo manager = new UserInfo();
        manager.setUserName("admin");
        String oriPwd = SecurityUtil.md5("admin123");
        manager.setPassword(oriPwd);

        if (SecurityUtil.md5(oriPwd + passwordSalt).equals(oriPwd)) {
            return manager;
        }
        return null;
    }

    @Override
    public Boolean isValidTooken(String token) {
        if (StringUtils.isEmpty(token)) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
