package acme.me.designpattern.iterator;

public class BTreeIterator<T extends Comparable<T>> implements Iterator<T> {
    private BTree<T> tree;
    private TreeNode<T> parentItem;
    private TreeNode<T> currItem;

    public BTreeIterator(BTree<T> tree) {
        this.tree = tree;
    }

    public BTree<T> getTree() {
        return tree;
    }

    public void setTree(BTree<T> tree) {
        this.tree = tree;
    }

    public void first() {
        currItem = tree.getRoot();
        parentItem = currItem;
        while (currItem.getLeft()!=null) {
            parentItem = currItem;
            currItem = currItem.getLeft();
        }
    }

    public void next() {
        if(parentItem.getLeft() == currItem){
            currItem = parentItem;
            return;
        }
        if(parentItem == currItem){
            currItem = parentItem.getRight();
            return;
        }
        if(parentItem.getRight() == currItem){
            TreeNode<T> temp = tree.getRoot();
            TreeNode<T> parent = temp;

            while (temp != parentItem) {
                parent = temp;
                if (temp.getValue().compareTo(currItem.getValue()) > 0) {
                    temp = temp.getLeft();
                } else {
                    temp = temp.getRight();
                }
            }
            currItem = parentItem;
            parentItem = parent;
            return;
        }
    }

    public boolean isDone() {
        TreeNode<T> temp = tree.getRoot();
        TreeNode<T> parent = temp;

        while (temp != parentItem) {
            parent = temp;
            if (temp.getValue().compareTo(currItem.getValue()) > 0) {
                temp = temp.getLeft();
            } else {
                temp = temp.getRight();
            }
        }

        return (currItem.getLeft()==null && currItem.getRight()==null && parent==null);
    }

    public T currentItem() {
        return currItem.getValue();
    }

}
