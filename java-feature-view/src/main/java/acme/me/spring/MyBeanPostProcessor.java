package acme.me.spring;

<<<<<<< HEAD
import org.apache.log4j.Logger;
=======
>>>>>>> cefff89027bf2a89316240f931bbffafaacb9471
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class MyBeanPostProcessor implements BeanPostProcessor{
<<<<<<< HEAD
    private static Logger logger = Logger.getLogger(MyBeanPostProcessor.class);

    public Object postProcessAfterInitialization(Object arg0, String arg1) throws BeansException {
        logger.error("##After bean ##"+arg0.toString()+"## initial");
=======

    public Object postProcessAfterInitialization(Object arg0, String arg1) throws BeansException {
        System.out.println("##After bean ##"+arg0.toString()+"## initial");
>>>>>>> cefff89027bf2a89316240f931bbffafaacb9471
        return arg0;
    }

    public Object postProcessBeforeInitialization(Object arg0, String arg1) throws BeansException {
<<<<<<< HEAD
        logger.error("##Before bean ##"+arg0.toString()+"## initial");
=======
        System.out.println("##Before bean ##"+arg0.toString()+"## initial");
>>>>>>> cefff89027bf2a89316240f931bbffafaacb9471
        return arg0;
    }

}
