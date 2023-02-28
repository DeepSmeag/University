package Entities;

public class ComplexSubtraction extends ComplexExpression{

    public ComplexSubtraction(Operation operation, ComplexNum[] args) {
        super(operation, args);
    }

    public ComplexNum executeOneOperation() {
        args[0].subtract(args[1]);
        return args[0];
    }
}
