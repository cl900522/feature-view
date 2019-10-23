package acme.me.guava;

import com.google.common.collect.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class CollTypeTest {
    @Test
    public void test1() {
        HashMultiset<String> hashMultiSet = HashMultiset.create(20);
        Multiset multiset = hashMultiSet;

        hashMultiSet.add("Great");
        multiset.add("G1");
        multiset.add("G1");

        multiset.add("Gm",3);

        Assert.assertTrue(multiset.count("G") == 0);
        Assert.assertTrue(multiset.count("G1") == 2);
        Assert.assertTrue(multiset.count("Gm") == 3);
        Assert.assertTrue(multiset.count("Great") == 1);

        Set<String> set = multiset.elementSet();//转换为set

        Multiset<String> treeMultiset = TreeMultiset.create();
        Multiset<String> linkedHashMultiset = LinkedHashMultiset.create(10);
        Multiset<String> concurrentHashMultiset = ConcurrentHashMultiset.create();
        Multiset<String> immutableMultiset = ImmutableMultiset.copyOf(hashMultiSet);
    }

    @Test
    public void test2() {
        ListMultimap<String, Integer> multimapList = MultimapBuilder.treeKeys().arrayListValues().build();
        ListMultimap<String, Integer> multimapList2 = MultimapBuilder.treeKeys().linkedListValues().build();
        multimapList.put("A1", 1);
        multimapList.put("A1", 2);
        multimapList.put("A1", 3);

        multimapList.put("A2", 5);
        multimapList.put("A2", 23);
        multimapList.put("A2", 25);

        multimapList.put("A3", 45);

        Collection<Map.Entry<String, Integer>> entries = multimapList.entries();


        Assert.assertTrue(multimapList.containsKey("A3"));
        multimapList.removeAll("A3");
        Assert.assertFalse(multimapList.containsKey("A3"));

        multimapList.putAll("A6", Lists.newArrayList(2, 3, 56, 8, 2, 4));
        Assert.assertEquals(6, multimapList.get("A6").size());

        multimapList.replaceValues("A6", Lists.newArrayList(1, 23, 4, 62));
        Assert.assertEquals(4, multimapList.get("A6").size());

        Map<String, Collection<Integer>> stringCollectionMap = multimapList.asMap();
        for (Map.Entry<String, Collection<Integer>> stringCollectionEntry : stringCollectionMap.entrySet()) {
            if (stringCollectionEntry.getKey().equals("A2")) {
                stringCollectionEntry.getValue().contains(5);
                stringCollectionEntry.getValue().contains(25);
                stringCollectionEntry.getValue().contains(23);
            }
        }

        SetMultimap<String, Integer> setMultimap = MultimapBuilder.treeKeys().hashSetValues().build();
        SetMultimap<String, Integer> setMultimap2 = MultimapBuilder.treeKeys().treeSetValues().build();
        Map<String, Collection<Integer>> stringCollectionMap1 = setMultimap.asMap();
    }

    @Test
    //BiMap 是一个用于双向获取key-vale的工具
    public void test3() {
        BiMap<String, Integer> userId = HashBiMap.create();
        userId.put("Jhon", 2019010);
        userId.put("Simon", 2013010);
        userId.put("Jira", 2017010);

        Assert.assertEquals(userId.get("Jhon"), new Integer(2019010));
        Assert.assertEquals(userId.inverse().get(2019010), "Jhon");

        Assert.assertEquals(userId.get("Simon"), new Integer(2013010));
        Assert.assertEquals(userId.inverse().get(2013010), "Simon");

        Assert.assertEquals(userId.get("Jira"), new Integer(2017010));
        Assert.assertEquals(userId.inverse().get(2017010), "Jira");


        //只能存储枚举的双向映射
        Map<Ge, ReGe> enumMap = Maps.newHashMap();
        enumMap.put(Ge.G1, ReGe.R1);
        enumMap.put(Ge.G2, ReGe.R3);
        enumMap.put(Ge.G3, ReGe.R2);
        EnumBiMap<Ge, ReGe> enumBiMap = EnumBiMap.create(enumMap);

        Assert.assertEquals(enumBiMap.get(Ge.G1), ReGe.R1);
        Assert.assertEquals(enumBiMap.get(Ge.G2), ReGe.R3);
        Assert.assertEquals(enumBiMap.get(Ge.G3), ReGe.R2);
    }

    public enum Ge {
        G1, G2, G3
    }

    public enum ReGe {
        R1, R2, R3
    }

    @Test
    public void test4() {
        Table<String, String, Integer> weightedGraph = HashBasedTable.create();
        weightedGraph.put("r1", "v1", 4);
        weightedGraph.put("r1", "v2", 20);
        weightedGraph.put("r2", "v1", 5);

        Map<String, Integer> r1 = weightedGraph.row("r1");
        Assert.assertEquals(r1.get("v1"), new Integer(4));
        Assert.assertEquals(r1.get("v2"), new Integer(20));

        Map<String, Integer> v1 = weightedGraph.column("v1");
        Assert.assertEquals(v1.get("r1"), new Integer(4));
        Assert.assertEquals(v1.get("r2"), new Integer(5));

        Table<String, String, Integer> treeBasedTable = TreeBasedTable.create();

        ArrayTable<String, String, Integer> arrayTable = ArrayTable.create(weightedGraph);

        ImmutableTable<String, String, Integer> immutableTable = ImmutableTable.copyOf(weightedGraph);
    }

    @Test
    public void test5() {
        RangeSet<Integer> rangeSet = TreeRangeSet.create();
        rangeSet.add(Range.closed(1, 5));
        rangeSet.add(Range.closedOpen(6, 10));
        rangeSet.add(Range.openClosed(11, 15));
        rangeSet.add(Range.open(16, 20));

        Assert.assertTrue(rangeSet.contains(1));
        Assert.assertTrue(rangeSet.contains(5));

        Assert.assertTrue(rangeSet.contains(6));
        Assert.assertTrue(!rangeSet.contains(10));

        Assert.assertTrue(!rangeSet.contains(11));
        Assert.assertTrue(rangeSet.contains(15));

        Assert.assertTrue(!rangeSet.contains(16));
        Assert.assertTrue(rangeSet.contains(17));
        Assert.assertTrue(!rangeSet.contains(20));

        rangeSet.remove(Range.open(5, 10));

    }

    @Test
    public void test6(){
        RangeMap<Integer, String> rangeMap = TreeRangeMap.create();
        rangeMap.put(Range.closed(1, 10), "foo"); // {[1, 10] => "foo"}
        rangeMap.put(Range.open(3, 6), "bar"); // {[1, 3] => "foo", (3, 6) => "bar", [6, 10] => "foo"}
        rangeMap.put(Range.open(10, 20), "foo"); // {[1, 3] => "foo", (3, 6) => "bar", [6, 10] => "foo", (10, 20) => "foo"}
        rangeMap.remove(Range.closed(5, 11)); // {[1, 3] => "foo", (3, 5) => "bar", (11, 20) => "foo"}
    }
}
