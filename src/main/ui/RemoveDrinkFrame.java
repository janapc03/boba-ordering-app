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

// Represents the frame where a user can remove a drink from their order
public class RemoveDrinkFrame extends JFrame implements ActionListener, MouseListener {
    private Order currentOrder;
    private Font pixelMPlusFont;
    private JButton removeDrinkButton;
    private Drink currentDrink;
    private JRadioButton currentDrinkButton;

    // MODIFIES: this
    // EFFECTS: creates and sets up the frame for removing a drink
    public RemoveDrinkFrame(Order order) {
        super("Remove a Drink");
        this.currentOrder = order;
        currentDrink = null;
        setUpFont();

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(200, 300);

        this.add(makeRemoveDrinkPanel());

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

    // MODIFIES: removeDrinkPanel
    // EFFECTS: creates and returns the removeDrinkPanel
    private JPanel makeRemoveDrinkPanel() {
        JPanel removeDrinkPanel = new JPanel();
        JLabel removePrompt = new JLabel("Which drink would you like to remove?");
        removePrompt.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        removePrompt.setFont(pixelMPlusFont.deriveFont(Font.BOLD));

        removeDrinkButton = new JButton("Remove Drink");
        removeDrinkButton.setFont(pixelMPlusFont.deriveFont(15f));
        removeDrinkButton.addActionListener(this);
        removeDrinkButton.addMouseListener(this);
        removeDrinkButton.setAlignmentX(JButton.CENTER_ALIGNMENT);

        removeDrinkPanel.setLayout(new BoxLayout(removeDrinkPanel, BoxLayout.Y_AXIS));
        removeDrinkPanel.setBorder(BorderFactory.createEmptyBorder(40, 20, 30, 20));
        removeDrinkPanel.setSize(200, 300);

        removeDrinkPanel.add(removePrompt);
        removeDrinkPanel.add(makeDisplayOrderPanel());
        removeDrinkPanel.add(removeDrinkButton);

        return removeDrinkPanel;
    }

    // MODIFIES: drinkPanel
    // EFFECTS: creates and returns panel with each drink in order displayed as a JRadioButton on it
    private JPanel makeDisplayOrderPanel() {
        JPanel drinkPanel = new JPanel();
        ButtonGroup buttonGroup = new ButtonGroup();
        drinkPanel.setLayout(new GridLayout(0, 1));
        drinkPanel.setBorder(BorderFactory.createEmptyBorder(40, 20, 20, 20));
        for (Drink drink : this.currentOrder.getOrder()) {
            String drinkDescription = " A " + intToSize(drink.getSize()) + " " + drink.getFlavor()
                    + toStringToppings(drink.getToppings()) + " ($" + drink.getPrice() + ")";
            currentDrinkButton = new JRadioButton(drinkDescription);
            currentDrinkButton.addActionListener(this);
            currentDrinkButton.setActionCommand("drink " + this.currentOrder.getOrder().indexOf(drink));
            currentDrinkButton.setFont(pixelMPlusFont.deriveFont(15f));
            buttonGroup.add(currentDrinkButton);
            drinkPanel.add(currentDrinkButton);
        }
        if (currentOrder.getNumDrinks() == 0) {
            JLabel emptyOrderLabel = new JLabel("Your order is currently empty!");
            emptyOrderLabel.setFont(pixelMPlusFont.deriveFont(15f));
            drinkPanel.add(emptyOrderLabel);
        }
        return drinkPanel;
    }

    // EFFECTS: creates and returns a string "small" or "large" depending on the int representation of the drink's size
    private String intToSize(int size) {
        if (size == 1) {
            return "small";
        } else {
            return "large";
        }
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

    // MODIFIES: this
    // EFFECTS: removes user's chosen drink from the order, closes the frame and displays confirmation message, then
    //          reopens viewOrderFrame
    @Override
    public void actionPerformed(ActionEvent e) {
        assessDrink(e);
        if (e.getSource() == removeDrinkButton) {
            if (this.currentOrder.getOrder().size() == 1) {
                makePopupMessage("Drink remove failed: cannot remove last drink in order",
                        "Failed to Remove Drink");
            } else {
                makePopupMessage("This drink has been removed!", "Completed Order");
            }
            this.currentOrder.removeDrink(currentDrink);
            ViewOrderFrame viewOrderFrame = new ViewOrderFrame(this.currentOrder);
            viewOrderFrame.setVisible(true);
            this.dispose();

        }
    }

    // EFFECTS: creates a popup message for the user
    private void makePopupMessage(String text, String title) {
        JLabel messageLabel = new JLabel(text);
        messageLabel.setFont(pixelMPlusFont.deriveFont(Font.PLAIN));
        JOptionPane messagePane = new JOptionPane(messageLabel, JOptionPane.PLAIN_MESSAGE);
        JPanel messagePanel = (JPanel) messagePane.getComponent(1);
        JButton okButton = (JButton) messagePanel.getComponent(0);
        okButton.setFont(pixelMPlusFont.deriveFont(Font.PLAIN));
        JDialog dialog = messagePane.createDialog(null, title);
        dialog.setVisible(true);
    }

    // MODIFIES: currentDrink
    // EFFECTS: sets user's drink choice as the drink to remove
    private void assessDrink(ActionEvent e) {
        for (Drink drink : this.currentOrder.getOrder()) {
            if (e.getActionCommand().equals("drink " + this.currentOrder.getOrder().indexOf(drink))) {
                currentDrink = drink;
            }
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
        removeDrinkButton.setOpaque(true);
        removeDrinkButton.setForeground(new Color(43, 141, 43, 255));
    }

    // EFFECTS: when the mouse leaves the orderDrinkButton component, the color changes to black
    @Override
    public void mouseExited(MouseEvent e) {
        removeDrinkButton.setOpaque(false);
        removeDrinkButton.setForeground(Color.black);
    }
}
