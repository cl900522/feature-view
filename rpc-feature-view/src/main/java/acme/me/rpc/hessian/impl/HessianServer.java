package acme.me.rpc.hessian.impl;

import acme.me.rpc.ServiceInterface;

import com.caucho.hessian.server.HessianServlet;

public class HessianServer extends HessianServlet implements ServiceInterface {

    @Override
    public String getState() {
        return "Server is running and using Hessian framework";
    }

}
