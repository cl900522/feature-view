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

        tree.add(b);
        tree.add(c);

        Iterator<Weight> ite = tree.createIterator();
        ite.first();
        while(!ite.isDone()){
            System.out.println(ite.currentItem().getWeght());
            ite.next();
        }
    }
}
