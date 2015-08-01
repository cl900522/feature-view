package acme.me.orm.mapper;

import java.util.List;
import java.util.Map;

import acme.me.orm.bean.Account;
import acme.me.orm.pager.Page;

public interface AccountMapper {
    List<Account> getAccountList(Map<String,Object> params, Page<Account> pager) throws Exception;
}
