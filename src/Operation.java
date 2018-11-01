

/**
 *
 * @author dystu
 */
public class Operation {
    private double leftOperand;
    private double rightOperand;
    private String operator;

    public Operation (double leftOp, String op, double rightOp) {
        leftOperand = leftOp;
        rightOperand = rightOp;
        operator = op;
    }

    public double getLeftOperand() {
        return leftOperand;
    }

    public void setLeftOperand(double leftOperand) {
        this.leftOperand = leftOperand;
    }

    public double getRightOperand() {
        return rightOperand;
    }

    public void setRightOperand(double rightOperand) {
        this.rightOperand = rightOperand;
    }

    public void setOperator (String op){
        this.operator = op;
    }

    public String getOperator (){
        return this.operator;
    }

    public double performOperation () {
        Context context = new Context(OperationsList.getStrategyOperation(operator));
        return context.executeStrategy(getLeftOperand(), getRightOperand());
    }
}
