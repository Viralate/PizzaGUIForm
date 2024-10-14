import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PizzaGUIFrame extends JFrame {
    private JRadioButton thinCrust, regularCrust, deepDishCrust;
    private JComboBox<String> sizeComboBox;
    private JCheckBox[] toppings;
    private JTextArea orderTextArea;
    private JButton orderButton, clearButton, quitButton;
    private ButtonGroup crustGroup;  // <--- Now it's a class-level variable
    
    private static final double[] SIZE_PRICES = {8.00, 12.00, 16.00, 20.00};
    private static final double TOPPING_PRICE = 1.00;
    private static final double TAX_RATE = 0.07;

    public PizzaGUIFrame() {
        setTitle("Pizza Order Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLayout(new BorderLayout());

        // Crust panel
        JPanel crustPanel = new JPanel();
        crustPanel.setBorder(new TitledBorder("Choose Crust"));
        thinCrust = new JRadioButton("Thin");
        regularCrust = new JRadioButton("Regular");
        deepDishCrust = new JRadioButton("Deep Dish");
        crustGroup = new ButtonGroup();  // <-- Move it outside constructor
        crustGroup.add(thinCrust);
        crustGroup.add(regularCrust);
        crustGroup.add(deepDishCrust);
        crustPanel.add(thinCrust);
        crustPanel.add(regularCrust);
        crustPanel.add(deepDishCrust);

        // Size panel
        JPanel sizePanel = new JPanel();
        sizePanel.setBorder(new TitledBorder("Choose Size"));
        String[] sizes = {"Small ($8)", "Medium ($12)", "Large ($16)", "Super ($20)"};
        sizeComboBox = new JComboBox<>(sizes);
        sizePanel.add(sizeComboBox);

        // Toppings panel
        JPanel toppingsPanel = new JPanel();
        toppingsPanel.setBorder(new TitledBorder("Choose Toppings ($1 each)"));
        String[] toppingNames = {"Pepperoni", "Mushrooms", "Onions", "Bacon", "Sausage", "Green Peppers"};
        toppings = new JCheckBox[toppingNames.length];
        for (int i = 0; i < toppingNames.length; i++) {
            toppings[i] = new JCheckBox(toppingNames[i]);
            toppingsPanel.add(toppings[i]);
        }

        // Order display panel
        JPanel displayPanel = new JPanel();
        orderTextArea = new JTextArea(10, 40);
        orderTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(orderTextArea);
        displayPanel.add(scrollPane);

        // Button panel
        JPanel buttonPanel = new JPanel();
        orderButton = new JButton("Order");
        clearButton = new JButton("Clear");
        quitButton = new JButton("Quit");

        buttonPanel.add(orderButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(quitButton);

        add(crustPanel, BorderLayout.NORTH);
        add(sizePanel, BorderLayout.WEST);
        add(toppingsPanel, BorderLayout.EAST);
        add(displayPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Button functionality
        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processOrder();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Quit Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }

    // Process the pizza order
    private void processOrder() {
        if (!thinCrust.isSelected() && !regularCrust.isSelected() && !deepDishCrust.isSelected()) {
            JOptionPane.showMessageDialog(null, "Please select a crust type.");
            return;
        }

        String crust = thinCrust.isSelected() ? "Thin" :
                       regularCrust.isSelected() ? "Regular" : "Deep Dish";
        
        int sizeIndex = sizeComboBox.getSelectedIndex();
        double basePrice = SIZE_PRICES[sizeIndex];
        String size = sizeComboBox.getSelectedItem().toString();

        double toppingsCost = 0;
        StringBuilder toppingsSelected = new StringBuilder();
        for (JCheckBox topping : toppings) {
            if (topping.isSelected()) {
                toppingsSelected.append(topping.getText()).append("\n");
                toppingsCost += TOPPING_PRICE;
            }
        }

        double subTotal = basePrice + toppingsCost;
        double tax = subTotal * TAX_RATE;
        double total = subTotal + tax;

        StringBuilder receipt = new StringBuilder();
        receipt.append("=========================================\n")
               .append("Crust: ").append(crust).append("\n")
               .append("Size: ").append(size).append("\n")
               .append("Price: $").append(String.format("%.2f", basePrice)).append("\n")
               .append("-----------------------------------------\n")
               .append("Toppings:\n").append(toppingsSelected)
               .append("-----------------------------------------\n")
               .append("Sub-total: $").append(String.format("%.2f", subTotal)).append("\n")
               .append("Tax: $").append(String.format("%.2f", tax)).append("\n")
               .append("=========================================\n")
               .append("Total: $").append(String.format("%.2f", total)).append("\n")
               .append("=========================================\n");

        orderTextArea.setText(receipt.toString());
    }

    // Clear the form
    private void clearForm() {
        crustGroup.clearSelection();  // <--- Fix: Now crustGroup is accessible
        sizeComboBox.setSelectedIndex(0);
        for (JCheckBox topping : toppings) {
            topping.setSelected(false);
        }
        orderTextArea.setText("");
    }
}
