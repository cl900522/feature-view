package acme.me.designpattern.proxy;

import org.apache.log4j.Logger;

public class StaticProxy implements Subject {
    Logger logger = Logger.getLogger(StaticProxy.class);

    private Subject sub;

    public StaticProxy(Subject sub) {
        this.sub = sub;
    }

    public void doSomeThing() throws Exception {
        logger.error("##Static proxy execute before!");
        sub.doSomeThing();
        logger.error("##Static proxy execute after!");
    }

    static public void main(String[] args) throws Exception {
        Subject real = new StaticProxy(new SubjectImpl());
        real.doSomeThing();
    }
}
