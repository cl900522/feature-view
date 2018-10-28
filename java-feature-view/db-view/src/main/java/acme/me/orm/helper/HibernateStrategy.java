package acme.me.orm.helper;

import org.hibernate.cfg.ImprovedNamingStrategy;

public class HibernateStrategy extends ImprovedNamingStrategy {

    private static final long serialVersionUID = 7954400928130351397L;

    /**
     * Return the argument
     */
    @Override
    public String tableName(String tableName) {
        return "hibernate_"+super.tableName(tableName);
    }

}
