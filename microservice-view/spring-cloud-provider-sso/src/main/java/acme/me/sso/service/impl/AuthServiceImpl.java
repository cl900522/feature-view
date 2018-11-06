package acme.me.sso.service.impl;

import acme.me.sso.rpc.ManagerServiceRpc;
import acme.me.sso.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private ManagerServiceRpc managerServiceRpc;

    @Override
    public String login(String loginName, String password) {
        managerServiceRpc.getAll();
        return loginName;
    }

    @Override
    public Boolean isValidTooken(String token) {
        if (StringUtils.isEmpty(token)) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
