package acme.me.orm;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import acme.me.orm.bean.Account;
import acme.me.orm.mapper.AccountMapper;
import acme.me.orm.pager.Page;

public class MybatisView {
    private static Logger logger = Logger.getLogger(MybatisView.class);
    public static void main(String[] args) throws Exception{
        ApplicationContext ap = new ClassPathXmlApplicationContext("spring-beans.xml");
        AccountMapper mapper = ap.getBean(AccountMapper.class);
        if(mapper != null){
            System.out.println("success");
            Map<String,Object> params = new HashMap<String,Object>();
            params.put("keyWord", "admin");
            Page<Account> page = new Page<Account>();
            page.setPageNo(1);
            page.setPageSize(2);
            mapper.getAccountList(params, page);
            System.out.println(page.getResults());
            System.out.println(page.getTotalRecord());

            Page<Account> page2 = new Page<Account>();
            page2.setPageNo(1);
            page2.setPageSize(2);
            mapper.getAccountListB(page2);
            System.out.println(page2.getResults());
            System.out.println(page2.getTotalRecord());

            Page<Account> page3 = new Page<Account>();
            page3.setPageNo(1);
            page3.setPageSize(2);
            mapper.getAccountListB(params, page3);
            System.out.println(page3.getResults());
            System.out.println(page3.getTotalRecord());

            mapper.getAccountList(params, null);
        }else{
            logger.error("Fail to create mapper");
        }
    }
}
