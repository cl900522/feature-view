package acme.me.rpc.httpinvoker.impl;

import acme.me.rpc.ServiceInterface;

public class HttpInvokerServer implements ServiceInterface {

    @Override
    public String getState() {
        return "Server is running and using HttpInvokerServlet framework";
    }

}
