package acme.me.j2se;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class J2SE {
    public static String m;

    @Test
    public void test1 (){
        if (m instanceof String) {
            System.out.println("m insanceof String!");
        }else {
            System.out.println("m NOT insanceof String!");
        }
    }

    static class OOMObject {
    }

    //-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=d:\
    @Test 
    public void test2 (){
        List<OOMObject> list = new ArrayList<OOMObject>();
        while(true){
            list.add(new OOMObject());
        }
    }
}
