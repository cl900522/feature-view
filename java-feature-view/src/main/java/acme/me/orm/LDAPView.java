package acme.me.orm;

import java.util.Arrays;
import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

public class LDAPView {
    public static void main(String[] args) {
        LDAPView view = new LDAPView();
        view.testLdap();
    }

    public void testLdap() {
        try {
            DirContext context = getContext();
            addEntry(context, "uid=oracle,ou=people,dc=mycompany,dc=com");
            updateEntry(context, "uid=oracle,ou=people,dc=mycompany,dc=com");
            searchEntry(context, "uid=oracle,ou=people,dc=mycompany,dc=com");
            removeEntry(context, "uid=oracle,ou=people,dc=mycompany,dc=com");
            context.close();
        } catch (AuthenticationException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DirContext getContext() throws NamingException {
        String account = "Manager";
        String password = "secret";
        String root = "dc=centos,dc=com";

        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://192.168.1.101:389/" + root);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, "cn=" + account + "," + root);
        env.put(Context.SECURITY_CREDENTIALS, password);

        DirContext context = new InitialDirContext(env);
        return context;
    }

    public void addEntry(DirContext context, String dn) throws Exception {
        Attributes attrs = new BasicAttributes(true);  
        Attribute objclass = new BasicAttribute("objectclass");  
        // 添加ObjectClass  
        String[] attrObjectClassPerson = { "inetOrgPerson", "organizationalPerson", "person", "top" };  
        Arrays.sort(attrObjectClassPerson);  
        for (String ocp : attrObjectClassPerson) {  
            objclass.add(ocp);  
        }  
        attrs.put(objclass);  

        attrs.put("displayName", "张三");  
        attrs.put("mail", "abc@163.com");  
        attrs.put("description", "");  
        attrs.put("userPassword", "Passw0rd".getBytes("UTF-8"));

        context.createSubcontext(dn, attrs);
    }

    public boolean updateEntry(DirContext context, String dn) throws Exception {
        boolean result = true;
        Attributes attrs = new BasicAttributes(true);
        attrs.put("mail", "zhangsan@163.com");
        context.modifyAttributes(dn, DirContext.REPLACE_ATTRIBUTE, attrs);
        return result;
    }

    public void searchEntry(DirContext context, String dn) throws Exception {
        // 设置过滤条件
        String uid = "zhangsan";
        String filter = "(&(objectClass=top)(objectClass=organizationalPerson)(uid=" + uid + "))";
        // 限制要查询的字段内容
        String[] attrPersonArray = { "uid", "userPassword", "displayName", "cn", "sn", "mail", "description" };
        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        // 设置将被返回的Attribute
        searchControls.setReturningAttributes(attrPersonArray);
        // 三个参数分别为：
        // 上下文；
        // 要搜索的属性，如果为空或 null，则返回目标上下文中的所有对象；
        // 控制搜索的搜索控件，如果为 null，则使用默认的搜索控件
        NamingEnumeration<SearchResult> answer = context.search("cn=users,dc=cas,dc=mydc", filter.toString(), searchControls);
        // 输出查到的数据
        while (answer.hasMore()) {
            SearchResult result = answer.next();
            NamingEnumeration<? extends Attribute> attrs = result.getAttributes().getAll();
            while (attrs.hasMore()) {
                Attribute attr = attrs.next();
                System.out.println(attr.getID() + "=" + attr.get());
            }
            System.out.println("============");
        }
    }

    public void removeEntry(DirContext context, String dn) throws Exception {
        context.destroySubcontext(dn);
    }
}
