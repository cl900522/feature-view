package acme.me.designpattern.iterator;

public class IteratorView {
    public static void main(String[] args){
        Weight a = new Weight();
        a.setWeght((float)19.0);
        BTree<Weight> tree = new BTree<Weight>(a);

        Weight b = new Weight();
        b.setWeght((float)15.0);

        Weight c = new Weight();
        c.setWeght((float)45.0);

        Weight d = new Weight();
        d.setWeght((float)18.0);

        Weight e = new Weight();
        e.setWeght((float)33.0);

        Weight f = new Weight();
        f.setWeght((float)18.5);

        Weight g = new Weight();
        g.setWeght((float)56.0);

        tree.add(b);
        tree.add(c);
        tree.add(d);
        tree.add(tree.getRoot(),e);
        tree.add(tree.getRoot(),f);
        tree.add(tree.getRoot(),g);

        Iterator<Weight> ite = tree.createIterator();
        ite.first();
        while(!ite.isDone()){
            System.out.println(ite.currentItem());
            ite.next();
        }
    }
}
