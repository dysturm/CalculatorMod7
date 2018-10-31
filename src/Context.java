public class Context {
    private CalculationStrategy strategy;

    public Context(CalculationStrategy strategy){
        this.strategy = strategy;
    }

    public double executeStrategy(double num1, double num2){
        return strategy.doOperation(num1, num2);
    }
}
