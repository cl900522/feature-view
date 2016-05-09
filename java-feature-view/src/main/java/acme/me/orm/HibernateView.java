package acme.me.orm;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.orm.hibernate3.HibernateTemplate;
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

    @Resource
    private HibernateTemplate hiberTemplate;

    private Account genSameAccount() {
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
        return account;
    }

    @Test
    public void loadTest() throws Exception {
        Session session1 = hiberSessionFactory.getCurrentSession();
        logger.error("It will goes wrong when call load");
        boolean loadError = false;
        try {
            session1.load(Account.class, Long.MAX_VALUE);
        } catch (Exception e) {
            loadError = true;
        }
        Assert.assertTrue(loadError);
    }

    @Test
    public void saveTest() throws Exception {
        Account account = genSameAccount();

        Session session1 = hiberSessionFactory.getCurrentSession();
        Transaction t = session1.beginTransaction();
        session1.save(account);
        t.commit();

        Session session2 = hiberSessionFactory.getCurrentSession();
        Assert.assertNotEquals(session1, session2);
        t = session2.beginTransaction();
        account.setNo(null);
        session2.save(account);
        logger.warn("Before commit any changes to bean will be saved");
        account.setProvince("Jiangsu");
        account.setCity("Yangzhou");
        account.setSex("femal");
        t.commit();
    }

    @Test
    public void updateAndMergeTest() throws Exception {
        Account account = genSameAccount();
        Account getAccount = null;

        Session session1 = hiberSessionFactory.getCurrentSession();
        Transaction t = session1.beginTransaction();
        account.setProvince("Jiangsu");
        logger.info("Before commit any changes to bean will be saved");
        session1.save(account);
        getAccount = (Account) session1.get(Account.class, account.getNo());
        logger.warn("When call save on Entiry, the object will be Persistant");
        Assert.assertTrue(getAccount == account);
        t.commit();

        /*update object will be persistant*/
        Session session2 = hiberSessionFactory.getCurrentSession();
        Assert.assertNotEquals(session1, session2);
        t = session2.beginTransaction();
        session2.update(account);
        getAccount = (Account) session2.get(Account.class, account.getNo());
        logger.warn("When call update, the new entiry will be persistant");
        Assert.assertTrue(getAccount == account);
        t.commit();

        /*load before merge will be ok*/
        Session session3 = hiberSessionFactory.getCurrentSession();
        Assert.assertNotEquals(session2, session3);
        Assert.assertNotEquals(session1, session3);
        t = session3.beginTransaction();
        getAccount = (Account) session3.get(Account.class, account.getNo());
        Account newUpdateAccount = genSameAccount();
        newUpdateAccount.setNo(account.getNo());
        session3.merge(newUpdateAccount);
        t.commit();
        
        /*load before update will goes wrong*/
        Session session4 = hiberSessionFactory.getCurrentSession();
        Assert.assertNotEquals(session3, session4);
        Assert.assertNotEquals(session2, session4);
        Assert.assertNotEquals(session1, session4);
        t = session4.beginTransaction();
        getAccount = (Account) session4.get(Account.class, account.getNo());
        try {
            session4.update(account);
            t.commit();
        } catch (Exception e) {
            logger.warn("When call update after load it will goes wrong.");
        }
        logger.warn("You can just clean the cache befor call update, then it will be ok.");
        session4.evict(getAccount);
        // session4.clear();
        session4.update(account);
        t.commit();
    }
    
    @Test
    public void deleteTest() throws Exception {
        Account account = genSameAccount();
        Account getAccount = null;

        Session session1 = hiberSessionFactory.getCurrentSession();
        Transaction t = session1.beginTransaction();
        account.setProvince("Jiangsu");
        logger.info("Before commit any changes to bean will be saved");
        session1.save(account);
        getAccount = (Account) session1.get(Account.class, account.getNo());
        logger.warn("When call save on Entiry, the object will be Persistant");
        Assert.assertTrue(getAccount == account);
        t.commit();
    }
}
