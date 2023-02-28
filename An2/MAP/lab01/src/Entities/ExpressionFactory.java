package Entities;

import Entities.*;


public final class ExpressionFactory {
    private static ExpressionFactory factoryInstance;

    private ExpressionFactory() {
    }

    public static ExpressionFactory getInstance() {
        if(factoryInstance == null) {
            factoryInstance = new ExpressionFactory();
        }

        return factoryInstance;
    }

    public ComplexExpression createExpression(Operation operation, ComplexNum[] args) {
        if (operation == Operation.ADDITION) {
            return new ComplexAddition(operation, args);
        } else if (operation == Operation.SUBTRACTION) {
            return new ComplexSubtraction(operation, args);
        } else if (operation == Operation.MULTIPLICATION) {
            return new ComplexMultiplication(operation, args);
        }
        // Means it's division
        return new ComplexDivision(operation, args);
    }
}
