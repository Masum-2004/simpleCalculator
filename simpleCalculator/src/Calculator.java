import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

public class Calculator extends JFrame implements ActionListener {
    private JTextField textField;
    private String operator = "";
    private BigDecimal num1, num2, result;

    Calculator() {
        // Text Field
        textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 18));
        textField.setBounds(10, 15, 370, 50);
        textField.setEditable(false); // Prevents manual input
        textField.setBorder((new LineBorder(Color.cyan, 2)));
        add(textField);


        // Operators
        addButton("/", 10, 75, 90, 40);
        addButton("%", 110, 75, 90, 40);
        addButton("-", 210, 75, 90, 40);
        addButton("*", 310, 75, 70, 40);

        // Numbers
        addButton("7", 10, 120, 90, 40);
        addButton("8", 110, 120, 90, 40);
        addButton("9", 210, 120, 90, 40);
        addButton("4", 10, 165, 90, 40);
        addButton("5", 110, 165, 90, 40);
        addButton("6", 210, 165, 90, 40);
        addButton("1", 10, 210, 90, 40);
        addButton("2", 110, 210, 90, 40);
        addButton("3", 210, 210, 90, 40);
        addButton("0", 10, 255, 90, 40);
        addButton(".", 110, 255, 90, 40);
        addButton("=", 210, 255, 90, 40);

        // Additional Buttons
        addButton("+", 310, 120, 70, 85);
        addButton("Clear", 310, 210, 70, 85, 15);

        // Frame settings
        setTitle("Calculator");
        setSize(400, 350);
        setLayout(null);
        setLocation(1000, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void addButton(String label, int x, int y, int width, int height) {
        addButton(label, x, y, width, height, 24);
    }

    private void addButton(String label, int x, int y, int width, int height, int fontSize) {
        JButton button = new JButton(label);
        button.setFont(new Font("Arial", Font.TRUETYPE_FONT, fontSize));
        button.setBounds(x, y, width, height);
        button.setForeground(Color.BLUE);
        button.addActionListener(this);
        add(button);
    }

    public static void main(String[] args) {
        new Calculator();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        try {
            if ("0123456789.".contains(command)) {
                String currentText = textField.getText();

                // Prevent multiple decimal points in a single number
                if (command.equals(".") && currentText.contains(".")) {
                    return;
                }

                textField.setText(currentText + command);
            } else if ("+-*/%".contains(command)) {
                if (textField.getText().isEmpty()) {
                    textField.setText("Enter number first");
                    return;
                }

                num1 = new BigDecimal(textField.getText());
                operator = command;
                textField.setText("");
            } else if (command.equals("=")) {
                if (textField.getText().isEmpty()) {
                    textField.setText("Enter second number");
                    return;
                }

                num2 = new BigDecimal(textField.getText());

                switch (operator) {
                    case "+":
                        result = num1.add(num2);
                        break;
                    case "-":
                        result = num1.subtract(num2);
                        break;
                    case "*":
                        result = num1.multiply(num2);
                        break;
                    case "%":
                        result = num1.remainder(num2);
                        break;
                    case "/":
                        if (!num2.equals(BigDecimal.ZERO)) {
                            result = num1.divide(num2, 10, BigDecimal.ROUND_HALF_UP);
                        } else {
                            textField.setText("Error: Cannot divide by 0");
                            return;
                        }
                        break;
                    default:
                        textField.setText("No operator selected");
                        return;
                }
                textField.setText(result.toPlainString());
            } else if (command.equals("Clear")) {
                textField.setText("");
                num1 = num2 = result = null;
                operator = "";
            }
        } catch (NumberFormatException ex) {
            textField.setText("Invalid Input");
        } catch (Exception ex) {
            textField.setText("Error: " + ex.getMessage());
        }
    }
}
