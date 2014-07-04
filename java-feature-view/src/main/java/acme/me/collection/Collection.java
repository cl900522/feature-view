package acme.me.collection;

import java.util.*;

public class Collection {
    /**
     * 根据数据量的大小，可以设置初始化时候，collection的size
     */
    List<String> list= new ArrayList<String>(6);
    Map<String,Object> map = new LinkedHashMap<String,Object>(100);
}
