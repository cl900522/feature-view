package acme.me.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;

import acme.me.rpc.ServiceInterface;
import acme.me.rpc.httpinvoker.impl.HttpInvokerServer;

public class HttpInvokerServlet extends HttpServlet {
    private static final long serialVersionUID = 6922390981708818998L;
    private HttpInvokerServiceExporter exporter = new HttpInvokerServiceExporter();

    @Override
    public void init(ServletConfig config) throws ServletException {
        exporter.setService(new HttpInvokerServer());
        exporter.setServiceInterface(ServiceInterface.class);
        exporter.afterPropertiesSet();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            this.exporter.handleRequest(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
