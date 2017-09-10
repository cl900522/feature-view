package acme.me.rpc.cxf.impl;

import acme.me.rpc.ServiceInterface;

public class CXFWebService implements ServiceInterface {

    public CXFWebService() {
    }

    public String getState() {
        return "I and running and using cxf framwork";
    }

}
