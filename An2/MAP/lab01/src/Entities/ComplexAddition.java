package Entities;
import Entities.ComplexNum;
public class ComplexAddition extends ComplexExpression {

    public ComplexAddition(Operation operation, ComplexNum[] args) {
        super(operation, args);
    }

    public ComplexNum executeOneOperation() {
        args[0].add(args[1]);
        return args[0];
    }
}
