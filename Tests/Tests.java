import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Tests {

    @Test
    void CalculatorAddTest(){
        Calculator mCalc = new Calculator();
        double result;

        Operation addOperation = mCalc.parseExpression("5+4");
        result = mCalc.calculateExpression(addOperation);

        assertEquals(9, result);
    }
    @Test
    void CalculatorDivideTest(){
        Calculator mCalc = new Calculator();
        double result;

        Operation divideOperation = mCalc.parseExpression("10 / 5");
        result = mCalc.calculateExpression(divideOperation);

        assertEquals(2, result);
    }

    @Test
    void getOpTest () {
        Calculator mCalc = new Calculator();


        mCalc.parseExpression("5+4");


        assertTrue(mCalc.printOp(0));
    }

    @Test
    void printHistoryTest() {
        Calculator mCalc = new Calculator();


        mCalc.parseExpression("5+4");
        mCalc.parseExpression("6/4");
        mCalc.parseExpression("12+5");

        mCalc.printHistory();
    }
}
