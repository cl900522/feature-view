package acme.me.servlet;

import javax.servlet.ServletConfig;

import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.frontend.ServerFactoryBean;
import org.apache.cxf.transport.servlet.CXFNonSpringServlet;

import acme.me.rpc.ServiceInterface;
import acme.me.rpc.cxf.impl.CXFWebService;

/**
 * webservice的访问处理Servlet
 * @author SipingWork
 */
public class CXFServlet extends CXFNonSpringServlet {
    private static final long serialVersionUID = 255790707463719625L;
    private static final String address = "/cxf";
    private static String appPath = "C://";

    @Override
    /**
     * 载入webservice类
     */
    protected void loadBus(ServletConfig servletConfig) {
        super.loadBus(servletConfig);
        Bus bus = getBus();
        BusFactory.setDefaultBus(bus);
        ServiceInterface cxfService = new CXFWebService();

        ServerFactoryBean serverFactoryBean = new ServerFactoryBean();
        serverFactoryBean.setServiceClass(ServiceInterface.class);
        serverFactoryBean.setAddress(address);
        serverFactoryBean.setServiceBean(cxfService);
        serverFactoryBean.create();

        appPath = getServletContext().getRealPath("/");
    }

}
