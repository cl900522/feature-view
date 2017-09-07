package acme.me.designpattern.facade;

public abstract class ProgramNodeBuilder {
    public abstract ProgramNode newVariable(String variableName);

    public abstract ProgramNode newAssignment(ProgramNode variable, ProgramNode expression);

    public abstract ProgramNode newReturnStatement(ProgramNode value);

    public abstract ProgramNode newCondition(ProgramNode condition, ProgramNode truePart, ProgramNode falsePart);

    public abstract ProgramNode getRootNode();

    private ProgramNode node;
}
