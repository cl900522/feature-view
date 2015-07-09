package acme.me.rpc.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class RMISampleServer extends UnicastRemoteObject implements RMISampleInterface {
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
        throw new RemoteException("xxxxx");
        //return a + b;
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
}
