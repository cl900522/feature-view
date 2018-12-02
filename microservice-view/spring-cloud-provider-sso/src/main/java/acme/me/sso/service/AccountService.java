package acme.me.sso.service;

import acme.me.sso.entity.AccountEntity;
import org.springframework.stereotype.Service;

@Service
public interface AccountService {
    public Boolean isEmailRegistered() throws Exception;

    public void registerAccount(AccountEntity accountEntity) throws Exception;
}
