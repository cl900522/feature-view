package acme.me.rpc.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RmiSuper extends UnicastRemoteObject{

    protected RmiSuper() throws RemoteException {
        super();
    }

}
