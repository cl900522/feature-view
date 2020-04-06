package acme.me.j2se.collection;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class CollectionView {
    /**
     * 根据数据量的大小，可以设置初始化时候，collection的size
     */
    List<String> list = new ArrayList<String>(6);
    Map<String, Object> map = new LinkedHashMap<String, Object>(100);

    @Test
    public void sortView() {
        String[] args = new String[]{"Mingxuan", "Daniel", "b", "Chenyi", "Jone", "Pook"};
        List<String> list = Arrays.asList(args);
        Collections.sort(list);
        System.out.println(list);


        String[] args2 = new String[]{"Mingxuan", "Daniel", "b", "Chenyi", "Jone", "Pook"};
        Arrays.sort(args2, new StringCompator());
        System.out.println(Arrays.asList(args2));
    }

    public class StringCompator implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return o1.length() - o2.length();
        }
    }

    @Test
    public void anoSortView() {
        int minGroupSize = 12;
        List<List<String>> winners = new ArrayList<List<String>>();

        for (int i = 0; i < minGroupSize; i++) {
            winners.add(generateRandamList());
        }

        // Sort anagram groups according to size
        Collections.sort(winners, new Comparator<List<String>>() {
            public int compare(List<String> o1, List<String> o2) {
                return o2.size() - o1.size();
            }
        });

        // Print anagram groups.
        for (List<String> l : winners) {
            System.out.println(l.size() + ": " + l);
        }
    }

    private List<String> generateRandamList() {
        int i = (int) (Math.random() * 20);
        List<String> aa = new ArrayList<String>(i);
        for (int j = 0; j < i; j++) {
            aa.add("x");
        }
        return aa;
    }

    @Test
    public void listOperate() {
        List<String> list = new ArrayList<String>();
        list.add("wo");
        list.add("ni");
        list.add("na");
        list.add("ba");
        list.add("jo");

        Iterator<String> ite = list.iterator();
        System.out.println(list);
        while (ite.hasNext()) {
            String value = ite.next();
            if (value.indexOf("o") != -1) {
                ite.remove();
            }
        }
        System.out.println(list);
    }

    @Test
    public void toArray() {
        List<String> list = new ArrayList<>();
        list.add("Hello1");
        list.add("Hello2");
        list.add("Hello3");
        list.add("Hello4");
        Object[] objects = list.toArray();
        System.out.println(objects);


        String[] a1 = new String[0];
        String[] stringArr = list.toArray(a1);
        System.out.println(stringArr);
        Assert.assertFalse(stringArr == a1);


        String[] a2 = new String[5];
        stringArr = list.toArray(a2);
        System.out.println(stringArr);
        Assert.assertTrue(stringArr == a2);

    }
}
