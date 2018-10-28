package acme.me.designpattern.facade;

import java.util.Iterator;

public class ExpressionNode extends ProgramNode {

    @Override
    public void getSourcePosition(int line, int index) {
        // TODO Auto-generated method stub

    }

    @Override
    public void traverse(CodeGenerator codeGenerator) {
        codeGenerator.visit(this);

        Iterator<ProgramNode> i = createIterator();
        while (i.hasNext()) {
            i.next().traverse(codeGenerator);
        }
    }
}
