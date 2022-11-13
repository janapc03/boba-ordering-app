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

        this.add(makeYourOrderPanel());

        this.pack();
        this.setVisible(true);
    }

    private JPanel makeYourOrderPanel() {
        JLabel yourOrder = new JLabel("Your Order:");
        yourOrderPanel = new JPanel();

        yourOrderPanel.setLayout(new GridLayout(2, 1));
        yourOrderPanel.setBorder(BorderFactory.createEmptyBorder(40,60,60,60));

        yourOrderPanel.add(yourOrder);
        yourOrderPanel.add(displayOrder());

        return yourOrderPanel;
    }

    private JPanel displayOrder() {
        JPanel drinkPanel = new JPanel();
        drinkPanel.setBorder(BorderFactory.createEmptyBorder(40,60,60,60));
        for (Drink drink : this.currentOrder.getOrder()) {
            String drinkDescription = (currentOrder.getOrder().indexOf(drink) + 1) + " - "
                    + drink.getSize() + " " + drink.getFlavor() + " " + drink.getToppings();
            JLabel currentDrink = new JLabel(drinkDescription);
            drinkPanel.add(currentDrink);
        }
        return drinkPanel;
    }
}
