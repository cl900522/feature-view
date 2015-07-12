package acme.me.rpc.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Rmisuper extends UnicastRemoteObject{

    protected Rmisuper() throws RemoteException {
        super();
    }

}
