package acme.me.rpc.hessian;

import java.net.MalformedURLException;

import org.junit.Test;

import acme.me.rpc.ServiceInterface;

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
}
