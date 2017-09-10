package acme.me.rpc.burlap.impl;

import acme.me.rpc.ServiceInterface;

import com.caucho.burlap.server.BurlapServlet;

public class BurlapServer extends BurlapServlet implements ServiceInterface {

    @Override
    public String getState() {
        return "Server is running and using Burlap framework";
    }

}
