package Entities;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionParser {
    String[] expression;
    int currExpr;
    ExpressionFactory expressionFactory;

    public ExpressionParser(String[] toParse) {
        expression = toParse;
        currExpr = 1;
        expressionFactory = ExpressionFactory.getInstance();
    }

    public boolean check() {
        if ((expression.length & 1) == 0 || expression.length < 3) {
            return false;
        }
        for (int item = 0; item < expression.length; item++) {
            if ((item & 1) == 1) {
                // should be sign
                if (!checkSign(expression[item]))
                    return false;
            } else {
                // should be complex number
                if (!checkNum(expression[item]))
                    return false;
            }
        }
        return true;
    }

    public boolean checkSign(String sign) {
        if (sign.length() > 1) {
            return false;
        }
        char possibleSign = sign.charAt(0);
        return (possibleSign == '+' || possibleSign == '-' || possibleSign == '*' || possibleSign == '/');
    }

    public boolean checkNum(String num) {
//        String complexPatternMatch = "([+-]?(0-9)(\\.(0-9)+)?)?([+-]?i)";
        String simpleMatch = "[0-9]+[+-][0-9]+\\*i"; // only considering ints as valid
        return Pattern.matches(simpleMatch, num);

    }

    public ComplexNum convertToNum(String num) {
        String numPattern = "-?[0-9]+";
        Pattern pattern = Pattern.compile(numPattern);
        Matcher matcher = pattern.matcher(num);
        float rePart = 0.0f, imPart = 0.0f;
        if (matcher.find()) {
            rePart = Float.parseFloat(matcher.group());
        }
        if (matcher.find()) {
            imPart = Float.parseFloat(matcher.group());
        }
        return new ComplexNum(rePart, imPart);
    }


    public void nextExpr(boolean firstOrder) {
        if (firstOrder) {
            currExpr += 2;
        } else {
            currExpr -= 2;
        }
    }

    public ComplexNum parse() {
        parseSecondOrder();
        parseFirstOrder();
        return convertToNum(expression[expression.length-1]);
    }

    public void parseFirstOrder() {
        ComplexNum num;
        ComplexExpression expr;
        currExpr = 1;
        while (currExpr < expression.length) {
            ComplexNum[] args = {convertToNum(expression[currExpr - 1]), convertToNum(expression[currExpr + 1])};
            char operation = expression[currExpr].charAt(0);
            if (operation == '+') {
                expr = expressionFactory.createExpression(Operation.ADDITION, args);
                num = expr.executeOneOperation();
            } else if (operation == '-') {
                //means subtraction
                expr = expressionFactory.createExpression(Operation.SUBTRACTION, args);
                num = expr.executeOneOperation();
            } else {
                num = convertToNum(expression[currExpr - 1]);
            }
            expression[currExpr + 1] = num.toString();
//            System.out.println(num);
            nextExpr(true);
        }

    }

    public void parseSecondOrder() {
        ComplexNum num;
        ComplexExpression expr;
        currExpr = expression.length - 2;
        while (currExpr > 0) {

            ComplexNum[] args = {convertToNum(expression[currExpr - 1]), convertToNum(expression[currExpr + 1])};
            char operation = expression[currExpr].charAt(0);
            if (operation == '*') {
                expr = expressionFactory.createExpression(Operation.MULTIPLICATION, args);
                num = expr.executeOneOperation();
                expression[currExpr - 1] = num.toString();

            } else if (operation == '/') {
                //means subtraction
                expr = expressionFactory.createExpression(Operation.DIVISION, args);
                num = expr.executeOneOperation();
                expression[currExpr - 1] = num.toString();

            }
            nextExpr(false);
        }

    }
}
