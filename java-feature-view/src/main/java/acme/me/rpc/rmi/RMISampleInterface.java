package acme.me.rpc.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMISampleInterface extends Remote {
    public String sayHello(String userName) throws RemoteException;

    public int sum(int a, int b) throws RemoteException;

    public String seriObject(RMIObject object) throws RemoteException;
}
