package com.clinix.servlet;

import javax.servlet.ServletConfig;

import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.frontend.ServerFactoryBean;
import org.apache.cxf.transport.servlet.CXFNonSpringServlet;

import com.clinix.webservice.AskMobileWebService;
import com.clinix.webservice.impl.AskMobielWebServiceImpl;

/**
 * webservice的访问处理Servlet
 * @author SipingWork
 */
public class AskMobileServlet extends CXFNonSpringServlet {
    private static final long serialVersionUID = 255790707463719625L;
    public static String appPath = "C://";

    @Override
    /**
     * 载入webservice类
     */
    protected void loadBus(ServletConfig servletConfig) {
        super.loadBus(servletConfig);
        Bus bus = getBus();
        BusFactory.setDefaultBus(bus);
        AskMobielWebServiceImpl dataService = new AskMobielWebServiceImpl();

        ServerFactoryBean serverFactoryBean = new ServerFactoryBean();
        serverFactoryBean.setServiceClass(AskMobileWebService.class);
        serverFactoryBean.setAddress("/askmobile");
        serverFactoryBean.setServiceBean(dataService);
        serverFactoryBean.create();

        appPath = getServletContext().getRealPath("/");
    }

}
