

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 *
 * @author dystu
 */
public class Main {

    static final String[] CALC_OPTIONS = {CalculatorOptions.CONSOLE, CalculatorOptions.GUI};

    static Calculator myCalc = new Calculator();
    static Operation myOp = null;

    static Pattern getOpPattern = Pattern.compile("(?i)history (?<index>\\d+)");
    static Pattern clearOpPattern = Pattern.compile("(?i)clear (?<index>\\d+)");


    public static void main(String[] args) {

        Logger logger = LogManager.getLogger(Main.class);
        //logger.trace("Configuration File Defined To Be :: "+System.getProperty("log4j.configurationFile"));

        Font msgFont = new Font (Font.SANS_SERIF, Font.BOLD, 20);
        UIManager.put("OptionPane.messageFont", msgFont);
        UIManager.put("OptionPane.buttonFont", msgFont);

        String calcVersion = (String) JOptionPane.showInputDialog(null,
                "Please select the calculator version:",
                "Select Calculator",
                JOptionPane.QUESTION_MESSAGE,
                null,
                CALC_OPTIONS,
                CALC_OPTIONS[0]);

        if (calcVersion != null) {
            if (calcVersion.equals(CalculatorOptions.GUI))
                activateGUICalculator(logger);
            else if (calcVersion.equals(CalculatorOptions.CONSOLE))
                activateConsoleCalculator(logger);
        }
    }

    private static void activateGUICalculator(Logger logger) {
        JFrame frame = new JFrame("Calculator");
        frame.setLayout(new BorderLayout());

        JTextField displayText = new JTextField();
        displayText.setEditable(false);
        displayText.setBackground(Color.WHITE);
        displayText.setHorizontalAlignment(JTextField.RIGHT);
        Font font = new Font("Times New Roman", Font.BOLD, 40);
        displayText.setFont(font);
        displayText.setToolTipText("Enter Expression");
        frame.add(displayText, BorderLayout.NORTH);

        Action appendText = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                displayText.setText(displayText.getText() + ((JButton)actionEvent.getSource()).getText());
            }
        };

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder( new LineBorder(Color.BLUE));
        buttonPanel.setLayout(new GridLayout(0,5));
        frame.add(buttonPanel, BorderLayout.WEST);

        for (int i = 0; i < 10; i++) {
            JButton button = new JButton(String.valueOf(i));
            formatBtn(button);
            button.setPreferredSize( new Dimension(50, 50));
            button.addActionListener(appendText);
            buttonPanel.add(button);
        }

        JPanel opPanel = new JPanel();
        opPanel.setBorder( new LineBorder(Color.GRAY));
        opPanel.setLayout(new GridLayout(0,3));
        frame.add(opPanel, BorderLayout.EAST);

        for (int i = 0; i < Operators.OPERATORS.length; i++) {
            JButton opBtn = new JButton(Operators.OPERATORS[i]);
            formatBtn(opBtn);
            opBtn.addActionListener(appendText);
            opPanel.add(opBtn);
        }


        JPanel controlBtns = new JPanel();
        controlBtns.setBorder( new LineBorder(Color.DARK_GRAY));
        controlBtns.setLayout( new GridLayout(0,3));
        frame.add(controlBtns, BorderLayout.SOUTH);


        JButton calculateBtn = new JButton("Enter");
        formatBtn(calculateBtn);
        calculateBtn.addActionListener(actionEvent -> {
            myOp = myCalc.parseExpression(displayText.getText());
            if (myOp != null)
                displayText.setText(String.valueOf(myCalc.calculateExpression(myOp)));
            else
                JOptionPane.showMessageDialog(frame,
                        "Invalid Expression",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
        });
        controlBtns.add(calculateBtn);


        JButton clearBtn = new JButton("C");
        formatBtn(clearBtn);
        clearBtn.addActionListener(actionEvent -> {
            displayText.setText("");
        });
        controlBtns.add(clearBtn);


        JButton historyBtn = new JButton("History");
        formatBtn(historyBtn);
        historyBtn.addActionListener(actionEvent -> {
            ArrayList<Operation> opList = myCalc.getHistory();
            String history = "";
            for (Operation op : opList)
                history += op.toString() + " = " + myCalc.calculateExpression(op) + "\n";
            if (history.equals(""))
                history = "No History";
            JOptionPane.showMessageDialog(frame,
                    history,
                    "History",
                    JOptionPane.PLAIN_MESSAGE);
        });
        controlBtns.add(historyBtn);



        frame.setLocationRelativeTo(null);
        frame.setMinimumSize( new Dimension(400,200));
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private static void formatBtn(JButton btn) {
        Font font = new Font("Times New Roman", Font.BOLD, 40);
        btn.setFont(font);
        btn.setBorder( new LineBorder(Color.BLACK));
    }

    private static void activateConsoleCalculator(Logger logger) {
        String expression = "";

        //Prompt the user to enter in an expression
        do {
            System.out.println("Enter expression:");

            expression = getInput();



            if (!expression.equalsIgnoreCase("exit")) {

                Matcher historyMatcher = getOpPattern.matcher(expression);
                Matcher clearHistoryMatcher = clearOpPattern.matcher(expression);

                //check if user wants to print specific op
                if (historyMatcher.find()) {
                    int historyIndex = Integer.parseInt(historyMatcher.group("index"));

                    if (!myCalc.printOp(historyIndex)) {
                        System.out.println("No history at that step");
                        logger.error("History not found at step " + historyIndex);
                    }

                } else if (expression.equalsIgnoreCase("history"))
                    myCalc.printHistory();

                    //check if user wants to clear specific op
                else if (clearHistoryMatcher.find()) {
                    int clearHistoryIndex = Integer.parseInt(clearHistoryMatcher.group("index"));

                    if (myCalc.clearOp(clearHistoryIndex)) {
                        System.out.println("successfully removed index " + clearHistoryMatcher.group("index"));
                        logger.trace("history cleared at step " + clearHistoryIndex);
                    }
                    else {
                        System.out.println("could not clear step");
                        logger.error("unable to clear history at step " + clearHistoryIndex);
                    }
                } else if (expression.equalsIgnoreCase("clear history")) {
                    myCalc.clearHistory();
                    System.out.println("History successfully cleared");
                    logger.trace("history cleared");
                }

                //if no other option was selected, attempt to calculate expression
                else {
                    myOp = myCalc.parseExpression(expression);
                    if (myOp != null) {
                        System.out.println(myCalc.calculateExpression(myOp));
                    } else {
                        //If user enters in invalid expression then prompt the user for a different expression
                        System.out.println("Invalid expression.");
                        logger.trace("Invalid expression entered: " + expression);
                    }
                }
            }



            //Only exit the program if the user types in 'exit'

        } while (!expression.equalsIgnoreCase("exit"));
    }

    public static String getInput (){
        Scanner kby = new Scanner(System.in);
        return kby.nextLine();
    }
}
