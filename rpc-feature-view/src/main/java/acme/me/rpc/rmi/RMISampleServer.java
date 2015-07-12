package acme.me.rpc.rmi;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.HashMap;

public class RMISampleServer extends Rmisuper implements RMISampleInterface {
    public static final Integer PORT = 8808;
    public static final String SERVERICE_NAME = "SAMEPLE-SERVER";
    private static final long serialVersionUID = 2742977636753958461L;

    protected RMISampleServer() throws RemoteException {
        super();
    }

    public String sayHello(String userName) throws RemoteException {
        if (userName != null && !userName.trim().equals("")) {
            return "Hello: " + userName;
        } else {
            throw new RemoteException("Please input your name!");
        }
    }

    public int sum(int a, int b) throws RemoteException {
        return a + b;
    }

    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(PORT);
            RMISampleInterface server = new RMISampleServer();
            Naming.rebind("//localhost:" + PORT + "/" + SERVERICE_NAME, server);
        } catch (MalformedURLException me) {
            System.out.println("Malformed URL: " + me.toString());
        } catch (RemoteException re) {
            System.out.println("Remote Exception: " + re.toString());
        }
    }

    public String seriObject(RMIObject object) throws RemoteException {
        return object.toString();
    }

    public ArrayList<String> getStringList(HashMap<String, Serializable> params) throws RemoteException {
        return null;
    }

    @Override
    public String doEnum(Gender gender) {
        return gender.toString();
    }
}
