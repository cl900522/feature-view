package acme.me.rpc.rmi;

import java.lang.reflect.Method;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class RMISampleClient {
    public static void main(String[] args) {
        RMISampleInterface rmiObject = null;
        try {
            String url = "//localhost:" + RMISampleServer.PORT + "/" + RMISampleServer.SERVERICE_NAME;
            rmiObject = (RMISampleInterface) Naming.lookup(url);
            System.out.println(rmiObject.sayHello("ChenMx"));
            RMIObject object = new RMIObject();
            object.setName("Aliex");
            object.setAge(12);
            System.out.println(rmiObject.seriObject(object));
            System.out.println("12 + 23 = " + rmiObject.sum(12, 23));
            System.out.println(rmiObject.doEnum(Gender.FEMALE));
            System.out.println(rmiObject.getStringList(null));
            System.out.println(rmiObject.getList(null));
        } catch (RemoteException rex) {
            System.out.println("Error in lookup: " + rex.toString());
        } catch (java.net.MalformedURLException me) {
            System.out.println("Malformed URL: " + me.toString());
        } catch (java.rmi.NotBoundException ne) {
            System.out.println("NotBound: " + ne.toString());
        } catch (Exception e) {
            System.out.println("NotBound: " + e.toString());
        }

        try {
            Method[] methods = RMISampleInterface.class.getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().equals("sayHello")) {
                    System.out.println(method.invoke(rmiObject, "ChenMx"));
                }
            }
        } catch (Exception e) {
            System.out.println("NotBound: " + e.toString());
        }
    }
}
