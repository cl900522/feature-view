package acme.me.orm;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import acme.me.orm.bean.Account;
import acme.me.orm.bean.Address;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(locations = "classpath:spring-beans.xml")
public class HibernateView {
    private static Logger logger = Logger.getLogger(HibernateView.class);

    @Resource
    private SessionFactory hiberSessionFactory;

    @Test
    public void saveTest() throws Exception {
        Account account = new Account();
        account.setId("admin");
        account.setName("admin");
        account.setSex("male");
        account.setProvince("SiChuan");
        account.setCity("Chendu");
        account.setBirthDay(new Date());
        account.setCreateDate(new Date());

        Address ad1 = new Address();
        ad1.setProvince("Sichuan");
        ad1.setCity("Chengdu");
        ad1.setArea("Gaoxing");
        ad1.setStreet("Guangxinglu");
        ad1.setAccount(account);

        Session currentSession = hiberSessionFactory.getCurrentSession();
        currentSession.save(account);
    }
}
