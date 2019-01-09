

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Calculator {

    static ArrayList<Operation> opList = new ArrayList();

    public static void addOperation (Operation op) {
        opList.add(op);
    }

    public static void printHistory () { for (Operation op : opList) System.out.println(op.toString());  }

    public static ArrayList<Operation> getHistory () { return opList; }

    public static void clearHistory () { opList.clear(); }

    public static boolean printOp (int index) {
        if (index >= 0 && index < opList.size()) {
            System.out.println(opList.get(index).toString());
            return true;
        } else
            return false;
    }

    public static boolean clearOp (int index) {
        if (index >= 0 && index < opList.size()) {
            opList.remove(index);
            return true;
        } else
            return false;
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
