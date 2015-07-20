package acme.me.rpc.rmi;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface RMISampleInterface extends Remote {
    public String sayHello(String userName) throws RemoteException;

    public int sum(int a, int b) throws RemoteException;

    public String seriObject(RMIObject object) throws RemoteException;

    public ArrayList<String> getStringList(HashMap<String, Serializable> params) throws RemoteException;

    public String doEnum(Gender gender) throws RemoteException;

    public List<String> getList(Map<String, Object> params) throws RemoteException ;
}
