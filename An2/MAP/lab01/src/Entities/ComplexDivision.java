package Entities;

public class ComplexDivision extends ComplexExpression {

    public ComplexDivision(Operation operation, ComplexNum[] args) {
        super(operation, args);
    }

    public ComplexNum executeOneOperation() {
        args[0].divide(args[1]);
        return args[0];
    }
}
