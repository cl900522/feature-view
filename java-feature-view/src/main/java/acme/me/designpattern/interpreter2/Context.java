package acme.me.designpattern.interpreter2;

import java.util.HashMap;
import java.util.Map;

public class Context<T> {
    private Map<String, T> map = new HashMap<String, T>();

    T lookup(String c) {
        return map.get(c);
    }

    public void assign(String e, T b) {
        map.put(e, b);
    }
}
