package Entities;

public class ComplexMultiplication extends ComplexExpression {

    public ComplexMultiplication(Operation operation, ComplexNum[] args) {
        super(operation, args);
    }

    public ComplexNum executeOneOperation() {
        args[0].multiply(args[1]);
        return args[0];
    }
}
