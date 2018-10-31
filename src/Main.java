

import java.util.Scanner;

/**
 *
 * @author dystu
 */
public class Main {


    public static void main(String[] args) {
        Calculator myCalc = new Calculator();
        Operation myOp = null;
        String expression = "";

        //Prompt the user to enter in an expression
        do {
            System.out.println("Enter expression:");


            //Parse expression
            do{
                expression = getInput();
                if (!expression.equalsIgnoreCase("exit")) {
                    myOp = myCalc.parseExpression(expression);
                    if (myOp != null){
                        System.out.println(myCalc.calculateExpression(myOp));
                    }
                    else {
                        //If user enters in invalid expression then prompt the user for a different expression
                        System.out.println("Enter a valid expression:");
                    }
                }
            } while (myOp == null && !expression.equalsIgnoreCase("exit"));



            //Only exit the program if the user types in 'exit'

        } while (!expression.equalsIgnoreCase("exit"));
    }

    public static String getInput (){
        Scanner kby = new Scanner(System.in);
        return kby.nextLine();
    }
}
