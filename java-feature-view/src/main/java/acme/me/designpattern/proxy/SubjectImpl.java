package acme.me.designpattern.proxy;

public class SubjectImpl implements Subject {

    public void doSomeThing() throws Exception {
        System.out.println("i am doing something");
    }

    @Override
    public String getName() {
       return "Subject Impl";
    }

    @Override
    public void throwException() throws Exception {
        throw new Exception("I throw exception!");
    }

}
