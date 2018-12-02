package acme.me.sso.service;

import acme.me.sso.entity.AccountRegisterEntity;
import org.springframework.stereotype.Service;

@Service
public interface AccountRegisterService {
    public void registerAccount(AccountRegisterEntity accountEntity) throws Exception;
}
