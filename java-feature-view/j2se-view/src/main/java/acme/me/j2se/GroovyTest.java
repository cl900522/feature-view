package acme.me.j2se;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.junit.Assert;
import org.junit.Test;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.HashMap;
import java.util.Map;

public class GroovyTest {

    @Test
    public void test1() throws Exception {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("groovy");
        Bindings bindings = engine.createBindings();

        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            Map<String, Object> map = new HashMap<>();
            bindings.put("map", map);
            Object eval2 = engine.eval(" map.remove('a');  map['name']='55'; return map;", bindings);
            Assert.assertTrue(eval2 == map);
        }
        System.out.println(System.currentTimeMillis() - start);
    }


    @Test
    public void test2() throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            Map<String, Object> map = new HashMap<>();
            map.remove("a");
            map.put("a", "55");
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    public void test3() {
        GroovyShell shell = new GroovyShell();
        Script parse = shell.parse(" map.remove('a');  map['name']='55' ");

        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            Map<String, Object> map = new HashMap<>();
            Binding bindings = new Binding(map);
            bindings.setVariable("map", map);
            parse.setBinding(bindings);
            parse.run();
            Assert.assertEquals("55", map.get("name"));
        }
        System.out.println(System.currentTimeMillis() - start);
    }
}
