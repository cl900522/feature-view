package acme.me.designpattern.facade;

import java.util.Iterator;
import java.util.List;

public abstract class ProgramNode {
    private List<ProgramNode> children;

    public abstract void getSourcePosition(int line, int index);

    public void add(ProgramNode node) {
        this.children.add(node);
    }

    public void remove(ProgramNode node) {
        this.children.remove(node);
    }

    public Iterator<ProgramNode> createIterator() {
        return this.children.iterator();
    }

    public abstract void traverse(CodeGenerator generator);
}
