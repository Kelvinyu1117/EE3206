package Tutorial5;
// Student name: Wu Kwun Yu
// Student ID  : 54845500

// Deadline: Friday, 18 Oct, 12 noon

// You need to implement the ActionListener.
// You may follow the program structure suggested to you.
// You may define additional state variables and add your own statements
// in the constructor, if required.

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Calculator extends JFrame {
    private static final int DISPLAY_SIZE = 12;
    private static final int NUMBER_OF_BUTTONS = 20;

    private JButton[] buttons;
    private JTextField display, memoryIndicator;
    private String[] buttonLabels = {"0", "1", "2", "3", "4", "5", "6", "7",
            "8", "9", ".", "C", "+", "-", "*", "/",
            "=", "+/-", "MR/C", "M+"};

    private double memory = 0;  // data saved in "memory" of the calculator

    //Other instance variables required in your design
    private boolean isDecimal;
    private boolean sign; // false -> positive, true -> negative
    private boolean hasInputOpr;
    private ArrayList<Double> operands;
    private ArrayList<Character> opr;
    private double res;
    private int mr;

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        calculator.setTitle("My calculator");
        calculator.setVisible(true);
    }

    public Calculator() {
        final int DEFAULT_FRAME_WIDTH = 320;
        final int DEFAULT_FRAME_HEIGHT = 300;

        setSize(DEFAULT_FRAME_WIDTH, DEFAULT_FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ActionListener listener = new ButtonListener();  //listener to handle action events

        buttons = new JButton[NUMBER_OF_BUTTONS];
        for (int i = 0; i < NUMBER_OF_BUTTONS; i++) {
            int size = 18;
            if (i >= 12 && i <= 17)
                size = 22;
            else if (i > 17)
                size = 14;
            buttons[i] = new JButton(buttonLabels[i]);
            buttons[i].setFont(new Font("Dialog", Font.BOLD, size));
            buttons[i].addActionListener(listener);
        }
        JPanel numberPanel = new JPanel();
        numberPanel.setLayout(new GridLayout(5, 4));

        numberPanel.add(buttons[11]);
        numberPanel.add(buttons[18]);
        numberPanel.add(buttons[19]);
        numberPanel.add(buttons[12]);

        numberPanel.add(buttons[7]);
        numberPanel.add(buttons[8]);
        numberPanel.add(buttons[9]);
        numberPanel.add(buttons[13]);

        numberPanel.add(buttons[4]);
        numberPanel.add(buttons[5]);
        numberPanel.add(buttons[6]);
        numberPanel.add(buttons[14]);

        numberPanel.add(buttons[1]);
        numberPanel.add(buttons[2]);
        numberPanel.add(buttons[3]);
        numberPanel.add(buttons[15]);

        numberPanel.add(buttons[0]);
        numberPanel.add(buttons[10]);
        numberPanel.add(buttons[17]);
        numberPanel.add(buttons[16]);

        display = new JTextField(DISPLAY_SIZE);
        display.setHorizontalAlignment(JTextField.RIGHT);  //text is right-justified
        display.setFont(new Font("Dialog", Font.PLAIN, 24)); //select font and size
        display.setEditable(false); //user is not allowed to edit the text display
        display.setText("0");

        memoryIndicator = new JTextField(2);
        memoryIndicator.setText("");
        memoryIndicator.setEditable(false);

        JPanel textPanel = new JPanel();
        textPanel.add(memoryIndicator);
        textPanel.add(display);

        add(textPanel, "North");
        add(numberPanel, "Center");

        // Your statements, where approrpriate
        sign = true;
        operands = new ArrayList<>();
        opr = new ArrayList<>();
    }

    private double evaluate(char c, double opr1, double opr2)
            throws IllegalArgumentException {

        double result = 0;
        if (c == '+')
            result = opr1 + opr2;
        else if (c == '-')
            result = opr1 - opr2;
        else if (c == '*')
            result = opr1 * opr2;
        else if (c == '/') {
            if (opr2 != 0.0)
                result = opr1 / opr2;
            else
                throw new IllegalArgumentException("divide-by-zero error");
        }
        return result;
    }

    private void showDisplay(double d) {
        String content = Double.toString(d);
        int i, k;
        boolean expRepresentation = false;
        if (content.length() > DISPLAY_SIZE) {
            for (i = content.length() - 1; i > 0; i--) {
                if (content.charAt(i) == 'E') {
                    expRepresentation = true;
                    break;
                }
            }
            if (expRepresentation) {
                k = content.length() - i;
                content = content.substring(0, DISPLAY_SIZE - k)
                        + content.substring(i, content.length());
            } else {
                content = content.substring(0, DISPLAY_SIZE);
            }
        }
        display.setText(content);
    }

    private class ButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            String content;

            // Other variables required in your design

            Object source = event.getSource();
            content = display.getText();
            if (content.equals("Error") && source != buttons[11]) // "Clear button"
                return;

            for (int i = 0; i < NUMBER_OF_BUTTONS; i++) {
                if (source == buttons[i]) {
                    switch (i) {
                        case 0: //button '0'
                            // Your handler for button '0'
                            zeroDigitBtnHandler();
                            break;
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                        case 7:
                        case 8:
                        case 9:
                            // Your handler for buttons '1' to '9'
                            normalNumBtnHandler(i);
                            break;

                        case 10: //button '.'
                            // Your handler for button '.'
                            decimalPointBtnHandler();
                            break;

                        case 11: //Clear button
                            // Your handler for clear button
                            clrBtnHandler();
                            break;

                        case 12: //button '+'
                        case 13: //button '-'
                        case 14: //button '*'
                        case 15: //button '/'
                        case 16: //button '='                            
                            // Your handler for buttons '+', '-', '*', '/', and '='
                            operatorBtnHandler(i);
                            break;

                        case 17: //button '+/-'
                            // Your handler for button '+/-'
                            toggleSignBtnHandler();
                            break;

                        case 18: //button "MR/C"
                            // Your handler
                            memoryRetrieveOrClearBtnHandler();
                            break;

                        case 19: //button "M+"
                            // Your handler
                            memoryStoreBtnHandler();
                            break;
                    }  //end of switch
                    break;
                }
            }  //end of for-loop                      

        }  //end of ActionPerformed
    } //end of ActionListener

    private void zeroDigitBtnHandler() {
        mr = 0;

        if (display.getText().charAt(0) == '0' && !isDecimal || hasInputOpr) {
            display.setText("0");
            hasInputOpr = false;
        } else {
            display.setText(display.getText() + "0");
        }
    }

    private void normalNumBtnHandler(int i) {
        mr = 0;

        if (display.getText().length() == 1 && display.getText().charAt(0) == '0' || hasInputOpr) {
            display.setText(Integer.toString(i));
            hasInputOpr = false;
        } else {
            display.setText(display.getText() + i);
            hasInputOpr = false;
        }
    }

    private void decimalPointBtnHandler() {
        mr = 0;
        if (!isDecimal) {
            display.setText(display.getText() + ".");
            isDecimal = true;
        }
    }

    private void operatorBtnHandler(int i) {
        mr = 0;
        hasInputOpr = true;
        operands.add(Double.parseDouble(display.getText()));

        if (operands.size() == 2 && opr.size() > 0) {
            boolean err = false;
            try {
                res = evaluate(opr.get(0), operands.get(0), operands.get(1));
            } catch (IllegalArgumentException e) {
                display.setText("Error");
                err = true;
            }

            if (!err) {
                sign = res > 0;
                if (i != 16) {
                    operands.set(1, res);
                    operands.remove(0);
                } else {
                    operands.clear();
                }

                opr.remove(0);

                showDisplay(res);
            }
        }

        if (i == 12) {
            opr.add('+');
        } else if (i == 13) {
            opr.add('-');
        } else if (i == 14) {
            opr.add('*');
        } else if (i == 15) {
            opr.add('/');
        }

    }

    private void clrBtnHandler() {

        isDecimal = false;
        sign = true;
        hasInputOpr = false;
        res = 0.0;
        operands.clear();
        opr.clear();
        mr = 0;

        display.setText(Integer.toString(0));

    }

    private void toggleSignBtnHandler() {
        mr = 0;

        if (display.getText().charAt(0) != '0') {
            sign = !sign;
            if (!sign) { // if (-)
                String tmp = "-";
                display.setText(tmp + display.getText());
            } else {
                display.setText(display.getText().substring(1));
            }
        }
    }

    private void memoryStoreBtnHandler() {
        mr = 0;

        memory = Double.parseDouble(display.getText());
        memoryIndicator.setText("M");
    }

    private void memoryRetrieveOrClearBtnHandler() {
        mr++;
        if (mr == 1) {
            showDisplay(memory);
        } else if (mr == 2) {
            memory = 0;
            memoryIndicator.setText("");
        }
    }
}
