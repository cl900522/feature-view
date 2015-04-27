package acme.me.designpattern.proxy;

public class SubjectImpl implements Subject {

    public void doSomeThing() throws Exception {
        System.out.println("i am doing something");
        //throw new Exception("####ERROR TEST!####");
    }

}
