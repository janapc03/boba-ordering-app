package ui;

import model.Drink;
import model.Order;

import javax.swing.*;
import java.awt.*;

public class ViewOrderFrame extends JFrame {
    private Order currentOrder;
    private JPanel yourOrderPanel;

    // MODIFIES: this
    // EFFECTS: creates and sets up frame
    public ViewOrderFrame(Order order) {
        super("My Order");
        this.currentOrder = order;

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(200,400);

        this.add(makeYourOrderPanel());

        this.pack();
        this.setVisible(true);
    }

    private JPanel makeYourOrderPanel() {
        JLabel yourOrder = new JLabel("Your Order:");
        yourOrderPanel = new JPanel();

        yourOrderPanel.setLayout(new GridLayout(3, 1));
        yourOrderPanel.setBorder(BorderFactory.createEmptyBorder(40,60,60,60));

        yourOrderPanel.add(yourOrder);
        yourOrderPanel.add(displayOrder());
        yourOrderPanel.add(makeOrderSummaryPanel());

        return yourOrderPanel;
    }

    private JPanel displayOrder() {
        JPanel drinkPanel = new JPanel();
        drinkPanel.setLayout(new GridLayout(0,1));
        drinkPanel.setBorder(BorderFactory.createEmptyBorder(40,60,60,60));
        for (Drink drink : this.currentOrder.getOrder()) {
            String drinkDescription = (currentOrder.getOrder().indexOf(drink) + 1) + " - a "
                    + intToSize(drink.getSize()) + " " + drink.getFlavor() + " " + drink.getToppings();
            JLabel currentDrink = new JLabel(drinkDescription);
            drinkPanel.add(currentDrink);
        }
        return drinkPanel;
    }

    private String intToSize(int size) {
        if (size == 1) {
            return "small";
        } else {
            return "large";
        }
    }

    private JPanel makeOrderSummaryPanel() {
        JPanel orderSummaryPanel = new JPanel();
        orderSummaryPanel.setLayout(new GridLayout(1, 2));
        orderSummaryPanel.setBorder(BorderFactory.createEmptyBorder(40,60,60,60));

        String numDrinksString = "Total Number of Drinks: " + currentOrder.getNumDrinks();
        JLabel numDrinksLabel = new JLabel(numDrinksString);

        String orderTotalString = "Order Total: $" + currentOrder.getOrderTotal();
        JLabel orderTotalLabel = new JLabel(orderTotalString);

        orderSummaryPanel.add(numDrinksLabel);
        orderSummaryPanel.add(orderTotalLabel);

        return orderSummaryPanel;
    }
}
