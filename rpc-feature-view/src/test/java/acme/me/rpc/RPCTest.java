package acme.me.rpc;

import java.net.MalformedURLException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.junit.Test;
import org.springframework.remoting.httpinvoker.CommonsHttpInvokerRequestExecutor;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;

import com.caucho.burlap.client.BurlapProxyFactory;
import com.caucho.hessian.client.HessianProxyFactory;

public class RPCTest {
    @Test
    public void testHessian() {
        String url = "http://localhost:8080/hessian";
        HessianProxyFactory factory = new HessianProxyFactory();
        try {

            ServiceInterface hessian = (ServiceInterface) factory.create(ServiceInterface.class, url);
            System.out.println(hessian.getState());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testBurlap() {
        String url = "http://localhost:8080/burlap";
        BurlapProxyFactory factory = new BurlapProxyFactory();

        try {

            ServiceInterface burlap = (ServiceInterface) factory.create(ServiceInterface.class, url);
            System.out.println(burlap.getState());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testHttpInvoker() {
        HttpInvokerProxyFactoryBean factory = new HttpInvokerProxyFactoryBean();
        factory.setServiceUrl("http://localhost:8080/httpinvoker");
        factory.setServiceInterface(ServiceInterface.class);

        MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
        HttpConnectionManagerParams params = new HttpConnectionManagerParams();
        params.setMaxTotalConnections(30);
        params.setDefaultMaxConnectionsPerHost(20);
        params.setConnectionTimeout(10 * 1000);// 连接超时时间：10秒
        params.setSoTimeout(5 * 60 * 1000);// 读取数据的超时时间：五分钟
        connectionManager.setParams(params);
        HttpClient httpClient = new HttpClient(connectionManager);
        CommonsHttpInvokerRequestExecutor CommonsHttpInvokerRequestExecutor = new CommonsHttpInvokerRequestExecutor(httpClient);
        factory.setHttpInvokerRequestExecutor(CommonsHttpInvokerRequestExecutor);

        factory.afterPropertiesSet();
        ServiceInterface httpInvoker = (ServiceInterface) factory.getObject();
        System.out.println(httpInvoker.getState());
    }
}
