package acme.me.orm;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;

import acme.me.orm.bean.Account;
import acme.me.orm.bean.Address;

public class HibernateView {
    private static Logger logger = Logger.getLogger(HibernateView.class);
    public static void main(String[] args) throws Exception{
        ApplicationContext ap = new ClassPathXmlApplicationContext("spring-beans.xml");
        HibernateTemplate hibernateTemplate = (HibernateTemplate) ap.getBean("hibernateTemplate");

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

        hibernateTemplate.save(account);
    }
}
