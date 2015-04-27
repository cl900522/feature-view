package acme.me.designpattern.iterator;

import java.util.Stack;

public class BTreeIterator<T extends Comparable<T>> implements Iterator<T> {
    private BTree<T> tree;
    private TreeNode<T> currItem;
    private Stack<TreeNode<T>> stack = new Stack<TreeNode<T>>();

    public BTreeIterator(BTree<T> tree) {
        this.tree = tree;
    }

    public BTree<T> getTree() {
        return tree;
    }

    public void first() {
        stack.empty();
        currItem = tree.getRoot();
        while (currItem != null) {
            stack.push(currItem);
            currItem = currItem.getLeft();
        }
        next();
    }

    public void next() {
        if (stack.isEmpty()) {
            currItem = null;
        }else{
            TreeNode<T> top = stack.pop();
            if (top.getRight() != null) {
                TreeNode<T> temp = top.getRight();
                while (temp != null) {
                    stack.push(temp);
                    temp = temp.getLeft();
                }
            }
            currItem = top;
        }
    }

    public boolean isDone() {
        return (currItem == null);
    }

    public T currentItem() {
        return currItem.getValue();
    }

}
