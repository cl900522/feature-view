package acme.me.j2ee.jmx;

import org.junit.Test;

import javax.management.JMException;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import java.lang.management.ManagementFactory;
import java.rmi.registry.LocateRegistry;

/**
 * sku扩展属性表 domain类
 *
 * @author cdchenmingxuan
 * @description
 * @since 2019/11/20 13:45
 */
public class JMXTest {
    @Test
    /**
     * 启动注册JMX bean
     */
    public void test1() throws JMException, Exception {
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("serverInfoMBean:name=serverInfo");
        server.registerMBean(new ServerInfo(), name);
    }


    /**
     * 在服务器开启端口注册，使得jconsole可以通过远程连接查看 MBEAN
     *
     * 也可以通过启动参数配置
     * -Djava.rmi.server.hostname=127.0.0.1
     * -Dcom.sun.management.jmxremote.port=10086
     * -Dcom.sun.management.jmxremote.ssl=false
     * -Dcom.sun.management.jmxremote.authenticate=false
     *
     * @throws JMException
     * @throws Exception
     */
    public void test2() throws JMException, Exception {
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("serverInfoMBean:name=serverInfo");
        server.registerMBean(new ServerInfo(), name);

        LocateRegistry.createRegistry(8081);
        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:8081/jmxrmi");
        JMXConnectorServer jcs = JMXConnectorServerFactory.newJMXConnectorServer(url, null, server);
        jcs.start();
    }
}
