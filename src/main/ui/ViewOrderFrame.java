package ui;

import model.Drink;
import model.Order;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ViewOrderFrame extends JFrame {
    private Order currentOrder;
    private JPanel yourOrderPanel;
    private Font pixelMPlusFont;

    // MODIFIES: this
    // EFFECTS: creates and sets up frame
    public ViewOrderFrame(Order order) {
        super("My Order");
        this.currentOrder = order;
        setUpFont();

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(200,300);

        this.add(makeYourOrderPanel());

        this.pack();
        this.setVisible(true);
    }

    private void setUpFont() {
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            pixelMPlusFont = Font.createFont(Font.TRUETYPE_FONT,
                    new File("src/main/ui/fonts/PixelMplus12-Regular.ttf")).deriveFont(20f);
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT,
                    new File("src/main/ui/fonts/PixelMplus12-Regular.ttf")));
        } catch (IOException | FontFormatException e) {
            System.err.println("Couldn't find font file");
        }
    }

    private JPanel makeYourOrderPanel() {
        JLabel yourOrder = new JLabel("Your Order:", JLabel.CENTER);
        yourOrder.setFont(pixelMPlusFont.deriveFont(Font.BOLD));
        yourOrderPanel = new JPanel();

        yourOrderPanel.setLayout(new BoxLayout(yourOrderPanel, BoxLayout.Y_AXIS));
        yourOrderPanel.setSize(200,300);
        yourOrderPanel.setBorder(BorderFactory.createEmptyBorder(40,20,60,20));

        yourOrder.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        yourOrderPanel.add(yourOrder);
        yourOrderPanel.add(displayOrder());
        yourOrderPanel.add(makeOrderSummaryPanel());

        return yourOrderPanel;
    }

    private JPanel displayOrder() {
        JPanel drinkPanel = new JPanel();
        drinkPanel.setLayout(new GridLayout(0,1));
        drinkPanel.setBorder(BorderFactory.createEmptyBorder(40,20,20,20));
        ImageIcon bobaImage = new ImageIcon("src/main/ui/images/TaroBoba.png");
        for (Drink drink : this.currentOrder.getOrder()) {
            String drinkDescription = " A " + intToSize(drink.getSize()) + " " + drink.getFlavor()
                    + toStringToppings(drink.getToppings()) + " ($" + drink.getPrice() + ")";
            JLabel currentDrink = new JLabel(drinkDescription);
            currentDrink.setIcon(bobaImage);
            currentDrink.setFont(pixelMPlusFont.deriveFont(15f));
            drinkPanel.add(currentDrink);
        }
        if (currentOrder.getNumDrinks() == 0) {
            JLabel emptyOrderLabel = new JLabel("Your order is currently empty!");
            emptyOrderLabel.setFont(pixelMPlusFont.deriveFont(15f));
            drinkPanel.add(emptyOrderLabel);
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
        orderSummaryPanel.setLayout(new GridLayout(2, 1));
        orderSummaryPanel.setBorder(BorderFactory.createEmptyBorder(10,20,0,20));

        String numDrinksString = "Total Number of Drinks: " + currentOrder.getNumDrinks();
        JLabel numDrinksLabel = new JLabel(numDrinksString);
        numDrinksLabel.setFont(pixelMPlusFont.deriveFont(17f));

        String orderTotalString = "Order Total: $" + currentOrder.getOrderTotal();
        JLabel orderTotalLabel = new JLabel(orderTotalString);
        orderTotalLabel.setFont(pixelMPlusFont.deriveFont(17f));

        orderSummaryPanel.add(numDrinksLabel);
        orderSummaryPanel.add(orderTotalLabel);

        return orderSummaryPanel;
    }

    private String toStringToppings(List<String> toppings) {
        String toppingsString;
        if (toppings.size() == 0) {
            return "";
        } else {
            toppingsString = " with " + toppings.get(0);
            if (toppings.size() == 2) {
                toppingsString = toppingsString + " and " + toppings.get(1);
            }
            return toppingsString;
        }
    }
}
