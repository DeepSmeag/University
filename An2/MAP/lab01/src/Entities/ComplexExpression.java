package Entities;
import Entities.Operation;
import Entities.ComplexNum;

public abstract class ComplexExpression {
    Operation operation;
    ComplexNum[] args;

    public ComplexExpression(Operation operation, ComplexNum[] args) {
        this.operation = operation;
        this.args = args;
    }

    public ComplexNum execute() {
        return executeOneOperation();
    }
    abstract public ComplexNum executeOneOperation();

}
