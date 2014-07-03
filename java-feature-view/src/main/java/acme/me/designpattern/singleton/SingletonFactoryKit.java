package acme.me.designpattern.singleton;

import acme.me.designpattern.factory.AbstractCakeFactory;
import acme.me.designpattern.factory.StrawberyCakeFactory;

public class SingletonFactoryKit {
     private static AbstractCakeFactory  factory;

     /**
      * 单例模式入口
      * @return
      */
     public static AbstractCakeFactory instance(){
        if(factory == null){
            factory = new StrawberyCakeFactory();
        }
        return factory;
    }
}
