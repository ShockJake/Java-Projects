// Proszę napisać kalkulator z graficznym (okienkowym) interfejsem użytkownika.

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class Calculator {
    // Atributes
    static String operator, firstOperand, secondOperand;
    static JTextField jTextField = new JTextField(17);

    // Action Listener for all buttons;
    static ActionListener action = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            String s = e.getActionCommand();
            if ((s.charAt(0) >= '0' && s.charAt(0) <= '9') || s.charAt(0) == '.') {
                if (!operator.equals("")) {
                    secondOperand = secondOperand + s;
                } else {
                    firstOperand = firstOperand + s;
                }
                jTextField.setText(firstOperand + " " + operator + " " + secondOperand);
            } else if (s.charAt(0) == 'C') {
                firstOperand = operator = secondOperand = "";

                jTextField.setText(firstOperand + " " + operator + " " + secondOperand);
            } else if (s.charAt(0) == '=') {
                double result = 0.0;
                if (operator.equals("+")) {
                    result = (Double.parseDouble(firstOperand) + Double.parseDouble(secondOperand));
                } else if (operator.equals("-")) {
                    result = (Double.parseDouble(firstOperand) - Double.parseDouble(secondOperand));
                } else if (operator.equals("/")) {
                    result = (Double.parseDouble(firstOperand) / Double.parseDouble(secondOperand));
                } else if (operator.equals("*")) {
                    result = (Double.parseDouble(firstOperand) * Double.parseDouble(secondOperand));
                }
                jTextField.setText(firstOperand + " " + operator + " " + secondOperand + " = " + result);
                firstOperand = Double.toString(result);
                operator = secondOperand = "";
            } else {
                if (operator.equals("") || secondOperand.equals("")) {
                    operator = s;
                }

                else {
                    double result = 0.0;
                    if (operator.equals("+")) {
                        result = (Double.parseDouble(firstOperand) + Double.parseDouble(secondOperand));
                    } else if (operator.equals("-")) {
                        result = (Double.parseDouble(firstOperand) - Double.parseDouble(secondOperand));
                    } else if (operator.equals("/")) {
                        result = (Double.parseDouble(firstOperand) / Double.parseDouble(secondOperand));
                    } else if (operator.equals("*")) {
                        result = (Double.parseDouble(firstOperand) * Double.parseDouble(secondOperand));
                    }
                    firstOperand = Double.toString(result);
                    operator = s;
                    secondOperand = "";
                }
                jTextField.setText(firstOperand + " " + operator + " " + secondOperand);
            }
        }
    };

    // Creating buttons and adding them to Panel
    private static JPanel createAndAssignButtons(JPanel jp) {
        JButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
        JButton addButton, subButton, divButton, mulButton, dotButton, clearButton, equalsButton;

        button0 = new JButton("0");
        button1 = new JButton("1");
        button2 = new JButton("2");
        button3 = new JButton("3");
        button4 = new JButton("4");
        button5 = new JButton("5");
        button6 = new JButton("6");
        button7 = new JButton("7");
        button8 = new JButton("8");
        button9 = new JButton("9");
        addButton = new JButton("+");
        subButton = new JButton("-");
        divButton = new JButton("/");
        mulButton = new JButton("*");
        dotButton = new JButton(".");
        clearButton = new JButton("C");
        equalsButton = new JButton("=");

        int button_width = 104;
        int button_height = 50;

        List<JButton> ls = List.of(button1, button2, button3, addButton, button4, button5, button6, subButton, button7,
                button8, button9,
                mulButton, dotButton, button0, clearButton, divButton, equalsButton);

        for (JButton jButton : ls) {
            jButton.setPreferredSize(new Dimension(button_width, button_height));
            jButton.addActionListener(action);
            jp.add(jButton);
        }

        return jp;
    }

    // Creating frame and setting window atributes
    private static JFrame createFrame() {

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        JFrame jFrame = new JFrame("Calculator");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        operator = "";
        firstOperand = "";
        secondOperand = "";

        jTextField.setFont(new Font(Font.SERIF, Font.BOLD, 30));
        jTextField.setEditable(false);
        jTextField.setPreferredSize(new Dimension(440, 60));

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        JPanel jPanel = new JPanel();

        jPanel.add(jTextField);
        jPanel = createAndAssignButtons(jPanel);
        jPanel.setBackground(Color.GRAY);

        jFrame.add(jPanel);
        jFrame.setBounds(dimension.width / 2 - 125, dimension.height / 2 - 112, 500, 400);
        jFrame.setVisible(true);
        jFrame.setResizable(false);
        return jFrame;
    }

    public static void main(String[] args) throws Exception {
        createFrame();
    }
}
