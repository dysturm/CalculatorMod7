

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Calculator {

    static ArrayList<Operation> opList = new ArrayList();

    public static void addOperation (Operation op) {
        opList.add(op);
    }

    public static Operation parseExpression(String expression){

        if (expression == null || expression.trim().equals("")){
            return null;
        }

        Pattern pattern = Pattern.compile("\\s*(\\d*\\.?\\d*)\\s*([-+*/%])\\s*(\\d*\\.?\\d*)\\s*");
        Matcher m = pattern.matcher(expression);

        if (m.find()){
            double leftOperand = Double.parseDouble(m.group(1));
            String operator = m.group(2);
            double rightOperand = Double.parseDouble(m.group(3));

            Operation operation = new Operation(leftOperand, operator, rightOperand);

            addOperation(operation);

            return operation;
        } else {
            return null;
        }
    }

    public static double calculateExpression(Operation myOp) {
        return myOp.performOperation();
    }
}
