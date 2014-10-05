package acme.me.rpc.rmi;

import java.rmi.Naming;
import java.rmi.RemoteException;

public class RMISampleClient {
    public static void main(String[] args) {
        try {
            String url = "//localhost:" + RMISampleServer.PORT + "/" + RMISampleServer.SERVERICE_NAME;
            RMISampleInterface RmiObject = (RMISampleInterface) Naming.lookup(url);
            System.out.println(RmiObject.sayHello("ChenMx"));
            System.out.println("12 + 23 = " + RmiObject.sum(12, 23));
            RMIObject object = new RMIObject();
            object.setName("Aliex");
            object.setAge(12);
            System.out.println(RmiObject.seriObject(object));
        } catch (RemoteException rex) {
            System.out.println("Error in lookup: " + rex.toString());
        } catch (java.net.MalformedURLException me) {
            System.out.println("Malformed URL: " + me.toString());
        } catch (java.rmi.NotBoundException ne) {
            System.out.println("NotBound: " + ne.toString());
        }

    }
}
