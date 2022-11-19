package ui;

import model.Drink;
import model.Order;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

// Represents the frame where user views their order
public class ViewOrderFrame extends JFrame implements ActionListener, MouseListener {
    private Order currentOrder;
    private JPanel yourOrderPanel;
    private Font pixelMPlusFont;
    private JButton removeDrinkButton;
    private JButton closeButton;

    // MODIFIES: this
    // EFFECTS: creates and sets up ordering viewing frame
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

    // EFFECTS: registers custom font and sets it up
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

    // MODIFIES: yourOrderPanel
    // EFFECTS: creates and returns order panel with all components added
    private JPanel makeYourOrderPanel() {
        JLabel yourOrder = new JLabel("Your Order:", JLabel.CENTER);


        yourOrder.setFont(pixelMPlusFont.deriveFont(Font.BOLD));
        yourOrderPanel = new JPanel();

        yourOrderPanel.setLayout(new BoxLayout(yourOrderPanel, BoxLayout.Y_AXIS));
        yourOrderPanel.setSize(200,300);
        yourOrderPanel.setBorder(BorderFactory.createEmptyBorder(40,20,60,20));

        yourOrder.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        yourOrderPanel.add(yourOrder);
        yourOrderPanel.add(makeDisplayOrderPanel());
        yourOrderPanel.add(makeOrderSummaryPanel());
        yourOrderPanel.add(makeBottomButtonsPanel());

        return yourOrderPanel;
    }

    // MODIFIES: drinkPanel
    // EFFECTS: creates and returns panel with each drink in order printed on it
    private JPanel makeDisplayOrderPanel() {
        JPanel drinkPanel = new JPanel();
        drinkPanel.setLayout(new GridLayout(0,1));
        drinkPanel.setBorder(BorderFactory.createEmptyBorder(40,20,20,20));
        for (Drink drink : this.currentOrder.getOrder()) {
            String drinkDescription = " A " + intToSize(drink.getSize()) + " " + drink.getFlavor()
                    + toStringToppings(drink.getToppings()) + " ($" + drink.getPrice() + ")";
            JLabel currentDrink = new JLabel(drinkDescription);
            currentDrink.setIcon(setImageIcon(drink));
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

    // MODIFIES: bobaImage
    // EFFECTS: sets and returns the image icon for the JLabel of the current drink in the order to match the
    //          flavor of the drink
    private ImageIcon setImageIcon(Drink drink) {
        ImageIcon bobaImage;
        if (drink.getFlavor().equals("classic milk tea")) {
            bobaImage = new ImageIcon("src/main/ui/images/ClassicBoba.png");
        } else if (drink.getFlavor().equals("wintermelon milk tea")) {
            bobaImage = new ImageIcon("src/main/ui/images/ClassicBoba.png");
        } else if (drink.getFlavor().equals("matcha milk tea")) {
            bobaImage = new ImageIcon("src/main/ui/images/MatchaBoba.png");
        } else if (drink.getFlavor().equals("strawberry green tea")) {
            bobaImage = new ImageIcon("src/main/ui/images/StrawberryBoba.png");
        } else if (drink.getFlavor().equals("taro milk tea")) {
            bobaImage = new ImageIcon("src/main/ui/images/TaroBoba.png");
        } else {
            bobaImage = new ImageIcon("src/main/ui/images/ThaiBoba.png");
        }
        return bobaImage;
    }

    // EFFECTS: creates and returns a string "small" or "large" depending on the int representation of the drink's size
    private String intToSize(int size) {
        if (size == 1) {
            return "small";
        } else {
            return "large";
        }
    }

    // MODIFIES: orderSummaryPanel
    // EFFECTS: creates returns panel with summary of order number of drinks and total
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

    private JPanel makeBottomButtonsPanel() {
        JPanel bottomButtonsPanel = new JPanel();
        bottomButtonsPanel.setLayout(new BoxLayout(bottomButtonsPanel, BoxLayout.X_AXIS));
        removeDrinkButton = new JButton("Remove a drink");
        removeDrinkButton.setFont(pixelMPlusFont.deriveFont(15f));
        removeDrinkButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        removeDrinkButton.addActionListener(this);
        removeDrinkButton.addMouseListener(this);
        closeButton = new JButton("Close");
        closeButton.setFont(pixelMPlusFont.deriveFont(15f));
        closeButton.addActionListener(this);
        closeButton.addMouseListener(this);

        bottomButtonsPanel.add(removeDrinkButton);
        bottomButtonsPanel.add(closeButton);

        return bottomButtonsPanel;
    }

    // EFFECTS: if the drink has no toppings:
    //              - returns an empty string
    //          if the drink has 1 topping:
    //              - returns a string " with " + topping1
    //          if the drink has 2 toppings:
    //              - returns the string if it has 1 topping + " and " + topping2
    private String toStringToppings(List<String> toppings) {
        String toppingsString;
        if (toppings.size() == 0) {
            return "";
        } else {
            toppingsString = " with " + toppings.get(0);
            if (toppings.size() == 2) {
                toppingsString = toppingsString + " and " + toppings.get(1);
                if (toppings.get(0) == toppings.get(1)) {
                    toppingsString = " with extra " + toppings.get(0);
                }
            }
            return toppingsString;
        }
    }

    // EFFECTS: opens up a removeDrinkFrame when the remove drink button is clicked
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == removeDrinkButton) {
            RemoveDrinkFrame removeDrinkFrame = new RemoveDrinkFrame(this.currentOrder);
            this.setVisible(false);
        }
        if (e.getSource() == closeButton) {
            this.dispose();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    // EFFECTS: when the mouse enters the removeDrinkButton component, the color changes to green
    @Override
    public void mouseEntered(MouseEvent e) {
        Color buttonLightUpColor = new Color(43, 141, 43, 255);
        if (e.getSource() == removeDrinkButton) {
            removeDrinkButton.setOpaque(true);
            removeDrinkButton.setForeground(buttonLightUpColor);
        }
        if (e.getSource() == closeButton) {
            closeButton.setOpaque(true);
            closeButton.setForeground(buttonLightUpColor);
        }
    }

    // EFFECTS: when the mouse leaves the orderDrinkButton component, the color changes to black
    @Override
    public void mouseExited(MouseEvent e) {
        removeDrinkButton.setOpaque(false);
        removeDrinkButton.setForeground(Color.black);
        closeButton.setOpaque(false);
        closeButton.setForeground(Color.black);
    }
}