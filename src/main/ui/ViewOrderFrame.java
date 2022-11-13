package ui;

import model.Order;

import javax.swing.*;

public class ViewOrderFrame extends JFrame {
    private Order currentOrder;

    // MODIFIES: this
    // EFFECTS: creates and sets up frame
    public ViewOrderFrame(Order order) {
        super("My Order");
        this.currentOrder = order;

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.setVisible(true);
    }
}
