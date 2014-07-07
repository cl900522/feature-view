package acme.me.designpattern.iterator;

public class BTree<T extends Comparable<T>> {
    private TreeNode<T> root;

    public TreeNode<T> getRoot() {
        return root;
    }

    public BTree(T value) {
        this.root = new TreeNode<T>(value);
    }

    public void add(T value) {
        TreeNode<T> temp = root;
        TreeNode<T> parent = null;

        while (temp != null) {
            parent = temp;
            if (temp.getValue().compareTo(value) > 0) {
                temp = temp.getLeft();
            } else {
                temp = temp.getRight();
            }
        }

        if (parent.getValue().compareTo(value) > 0) {
            parent.setLeft(new TreeNode<T>(value));
        } else {
            parent.setRight(new TreeNode<T>(value));
        }
    }

    public Iterator<T> createIterator() {
        BTreeIterator<T> ite = new BTreeIterator<T>(this);
        return ite;
    }
}
