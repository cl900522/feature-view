package acme.me.designpattern.iterator;

public class BTree<T extends Comparable<T>> implements Aggregrate<T>{
    private TreeNode<T> root;

    public TreeNode<T> getRoot() {
        return root;
    }

    public BTree(T value) {
        this.root = new TreeNode<T>(value);
    }
    /**
     * 非递归地增加节点
     * @param value
     */
    public void add(T value) {
        TreeNode<T> temp = root, parent = temp;

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
            parent.getLeft().setParent(parent);
        } else {
            parent.setRight(new TreeNode<T>(value));
            parent.getRight().setParent(parent);
        }
    }

    /**
     * 递归地增加节点
     * @param value
     */
    public void add(TreeNode<T> node, T value) {
        if(node.getValue().compareTo(value)>0){
            if(node.getLeft()!=null){
                add(node.getLeft(),value);
            }else{
                node.setLeft(new TreeNode<T>(value));
            }
        }else{
            if(node.getRight() != null){
                add(node.getRight(),value);
            }else{
                node.setRight(new TreeNode<T>(value));
            }
        }
    }

    public Iterator<T> createIterator() {
        BTreeIterator<T> ite = new BTreeIterator<T>(this);
        return ite;
    }
}
