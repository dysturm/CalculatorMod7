import java.util.HashMap;
import java.util.Map;

public class OperationsList {

    private static Map<String, CalculationStrategy> StrategyList = new HashMap<>();

    //new calculate operations can easily be added in this method
    static  {
        StrategyList.put(Operators.Addition, new OperationAdd());
        StrategyList.put(Operators.Subtract, new OperationSubtract());
        StrategyList.put(Operators.Multiply, new OperationMultiply());
        StrategyList.put(Operators.Divide, new OperationDivide());
        StrategyList.put(Operators.Modulus, new OperationModulus());
    }

    public static CalculationStrategy getStrategyOperation (String operator) {
        return StrategyList.get(operator);
    }
}
