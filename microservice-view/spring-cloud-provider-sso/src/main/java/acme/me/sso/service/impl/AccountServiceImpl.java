package acme.me.sso.service.impl;

import acme.me.common.exception.BussinessException;
import acme.me.common.exception.BussinessExceptionEnum;
import acme.me.common.util.DateUtil;
import acme.me.sso.entity.AccountEntity;
import acme.me.sso.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public Boolean isEmailRegistered() throws Exception {
        return null;
    }

    @Override
    public void registerAccount(AccountEntity accountEntity) throws Exception {
        String email = accountEntity.getEmail();
        email = email.toLowerCase().trim();
        Boolean hasRegistered = redisTemplate.hasKey("account_email_" + email);
        if (hasRegistered) {
            throw new BussinessException(BussinessExceptionEnum.ERROR_00_02, "账号已注册");
        }

        String key = "account_registering_email_" + email;
        Boolean duringRegist = redisTemplate.hasKey(key);
        if (duringRegist) {
            String time = redisTemplate.opsForValue().get(key);
            throw new BussinessException(BussinessExceptionEnum.ERROR_00_02, "最近注册:" + time + "，请等待审核");
        }

        redisTemplate.opsForValue().set(key, DateUtil.formateDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        redisTemplate.expire(key, 5, TimeUnit.MINUTES);
    }
}
