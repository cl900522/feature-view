package acme.me.sso.service.impl;

import acme.me.common.exception.BussinessException;
import acme.me.common.exception.BussinessExceptionEnum;
import acme.me.common.util.DateUtil;
import acme.me.sso.dao.AccountRegisterMapper;
import acme.me.sso.entity.AccountRegisterEntity;
import acme.me.sso.entity.RegisterStatus;
import acme.me.sso.service.AccountRegisterService;
import acme.me.sso.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class AccountRegisterServiceImpl implements AccountRegisterService {
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private AccountRegisterMapper accountRegisterMapper;

    @Override
    @Transactional
    public void registerAccount(AccountRegisterEntity accountRegisterEntity) throws Exception {
        String email = accountRegisterEntity.getEmail();
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

        String pwdSalt = SecurityUtil.createNewSalt();
        accountRegisterEntity.setPassword(SecurityUtil.encrtyptPassword(accountRegisterEntity.getPassword(), pwdSalt));
        accountRegisterEntity.setPwdSalt(pwdSalt);
        accountRegisterEntity.setStatus(RegisterStatus.SUBMITTED);
        accountRegisterEntity.setCreateTime(new Date());
        accountRegisterEntity.setUpdateTime(new Date());
        accountRegisterMapper.insertAccount(accountRegisterEntity);

        redisTemplate.opsForValue().set(key, DateUtil.formateDate(new Date(), DateUtil.DATE_TIME));
        redisTemplate.expire(key, 5, TimeUnit.MINUTES);
    }
}
