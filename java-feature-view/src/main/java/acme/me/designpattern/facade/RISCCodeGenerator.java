package acme.me.designpattern.facade;

import java.io.ByteArrayInputStream;

public class RISCCodeGenerator extends CodeGenerator {

    protected RISCCodeGenerator(ByteArrayInputStream stream) {
        super(stream);
    }

    @Override
    public void visit(StatementNode node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(ExpressionNode node) {
        // TODO Auto-generated method stub

    }

}
