package acme.me.designpattern.iterator;

public class TreeNode<T extends Comparable<T>> {
    public TreeNode(T value){
        this.node = value;
    }
    /**
     * 存储变量
     */
    private T node;
    /**
     * 父节点
     */
    private TreeNode<T> parent;
    /**
     * 做节点
     */
    private TreeNode<T> left;
    /**
     * 右节点
     */
    private TreeNode<T> right;

    public TreeNode<T> getParent() {
        return parent;
    }

    public void setParent(TreeNode<T> parent) {
        this.parent = parent;
    }

    public T getValue() {
        return node;
    }

    public void setValue(T node) {
        this.node = node;
    }

    public TreeNode<T> getLeft() {
        return left;
    }

    public void setLeft(TreeNode<T> left) {
        this.left = left;
    }

    public TreeNode<T> getRight() {
        return right;
    }

    public void setRight(TreeNode<T> right) {
        this.right = right;
    }

}
