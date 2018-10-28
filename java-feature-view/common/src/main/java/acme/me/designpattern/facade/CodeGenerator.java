package acme.me.designpattern.facade;

import java.io.ByteArrayInputStream;

public abstract class CodeGenerator {
    public abstract void visit(StatementNode node);

    public abstract void visit(ExpressionNode node);

    protected CodeGenerator(ByteArrayInputStream stream) {
        this.output = stream;
    };

    protected ByteArrayInputStream output;
}
