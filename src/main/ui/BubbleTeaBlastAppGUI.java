package ui;

import model.Order;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Represents the graphical user interface of Bubble Tea Blast
public class BubbleTeaBlastAppGUI implements ActionListener, MouseListener {
    private Order order;
    private PrimaryFrame primaryFrame;
    private ImageIcon bubbleTeaShopImageIcon;
    private JLabel bubbleTeaShopLabel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JLabel welcomeLabel;
    private JLabel bubbleTeaBlastLabel;
    private JLabel promptLabel;
    private JButton orderNewDrinkButton;
    private JButton viewMyOrderButton;
    private JButton saveCurrentOrderButton;
    private JButton loadSavedOrderButton;
    private JButton finishAndPayButton;
    private static final String JSON_STORE = "./data/order.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private Font bubbleWorldFont;
    private Font pixelMPlusFont;

    // EFFECTS: constructs the primary frame with all components added
    public BubbleTeaBlastAppGUI() {
        this.order = new Order("Your Order");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        primaryFrame = new PrimaryFrame();

        bubbleTeaShopImageIcon = new ImageIcon("src/main/ui/images/BubbleTeaShopImage.png");

        primaryFrame.add(makeBubbleTeaShopPanel());
        primaryFrame.add(makeMainMenuPanel());
        primaryFrame.pack();
        primaryFrame.setVisible(true);
    }

    // EFFECTS: creates and returns the left panel containing the bubble tea shop image
    private JPanel makeBubbleTeaShopPanel() {
        Image image = bubbleTeaShopImageIcon.getImage();
        Image newImg = image.getScaledInstance(480, 540,  Image.SCALE_SMOOTH);
        bubbleTeaShopImageIcon = new ImageIcon(newImg);
        bubbleTeaShopLabel = new JLabel();
        bubbleTeaShopLabel.setIcon(bubbleTeaShopImageIcon);

        leftPanel = new JPanel();
        leftPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setBounds(0,0, 480,540);
        leftPanel.add(bubbleTeaShopLabel, BorderLayout.WEST);
        return leftPanel;
    }

    // EFFECTS: creates and returns the right panel containing the main menu with welcome message, action prompt,
    //          and buttons for each possible action
    private JPanel makeMainMenuPanel() {
        setUpFont();
        setUpButtons();
        pixelMPlusFont = pixelMPlusFont.deriveFont(Font.BOLD);
        welcomeLabel = new JLabel("Welcome to", SwingConstants.CENTER);
        bubbleTeaBlastLabel = new JLabel("Bubble Tea Blast!", SwingConstants.CENTER);
        welcomeLabel.setFont(bubbleWorldFont);
        bubbleTeaBlastLabel.setFont(bubbleWorldFont);
        promptLabel = new JLabel("What would you like to do?", SwingConstants.CENTER);
        promptLabel.setFont(pixelMPlusFont);

        rightPanel = new JPanel();
        rightPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        rightPanel.setLayout(new GridLayout(8,1));
        rightPanel.setBounds(0,0, 480,540);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10,60,30,60));
        rightPanel.add(welcomeLabel);
        rightPanel.add(bubbleTeaBlastLabel);
        rightPanel.add(promptLabel);
        rightPanel.add(orderNewDrinkButton);
        rightPanel.add(viewMyOrderButton);
        rightPanel.add(saveCurrentOrderButton);
        rightPanel.add(loadSavedOrderButton);
        rightPanel.add(finishAndPayButton);
        return rightPanel;
    }

    // EFFECTS: sets up main menu buttons with ActionListener, ActionCommand, MouseListener and font
    private void setUpButtons() {
        Font buttonFont = pixelMPlusFont.deriveFont(Font.PLAIN);
        List<JButton> buttons = new ArrayList<>();
        orderNewDrinkButton = new JButton("Order New Drink");
        viewMyOrderButton = new JButton("View My Order");
        saveCurrentOrderButton = new JButton("Save Current Order");
        loadSavedOrderButton = new JButton("Load Saved Order");
        finishAndPayButton = new JButton("Finish and Pay");
        buttons.add(orderNewDrinkButton);
        buttons.add(viewMyOrderButton);
        buttons.add(saveCurrentOrderButton);
        buttons.add(loadSavedOrderButton);
        buttons.add(finishAndPayButton);
        for (JButton button : buttons) {
            button.addActionListener(this);
            button.addMouseListener(this);
            button.setFont(buttonFont);
        }
    }

    // EFFECTS: registers custom fonts and sets them up
    private void setUpFont() {
        try {
            bubbleWorldFont = Font.createFont(Font.TRUETYPE_FONT,
                    new File("src/main/ui/fonts/BubbleWorld-nRG50.ttf")).deriveFont(52f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT,
                    new File("src/main/ui/fonts/BubbleWorld-nRG50.ttf")));
            pixelMPlusFont = Font.createFont(Font.TRUETYPE_FONT,
                    new File("src/main/ui/fonts/PixelMplus12-Regular.ttf")).deriveFont(20f);
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT,
                    new File("src/main/ui/fonts/PixelMplus12-Regular.ttf")));
        } catch (IOException | FontFormatException e) {
            System.err.println("Couldn't find font file");
        }
    }

    // EFFECTS: if orderNewDrinkButton or viewOrderButton is clicked, opens new frame with respective content
    //          if saveCurrentOrderButton is clicked, saves user's order
    //          if loadCurrentOrderButton is click, loads user's previous order
    //          if finishAndPayButton is clicked, displays successful message and closes frame
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == orderNewDrinkButton) {
            OrderDrinkFrame orderDrinkFrame = new OrderDrinkFrame(this.order);

        } else if (e.getSource() == viewMyOrderButton) {
            ViewOrderFrame viewOrderFrame = new ViewOrderFrame(this.order);

        } else if (e.getSource() == saveCurrentOrderButton) {
            saveCurrentOrder();
        } else if (e.getSource() == loadSavedOrderButton) {
            loadSavedOrder();
        } else if (e.getSource() == finishAndPayButton) {
            finishAndPay();
            primaryFrame.dispose();
        }
    }

    // This method was taken from WorkRoomApp class in JsonSerializationDemo
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // EFFECTS: saves the order to file
    private void saveCurrentOrder() {
        try {
            jsonWriter.open();
            jsonWriter.write(this.order);
            jsonWriter.close();
            JLabel successfulSaveLabel = new JLabel("Your order has been saved!");
            successfulSaveLabel.setFont(pixelMPlusFont.deriveFont(Font.PLAIN));
            JOptionPane successfulSavePane = new JOptionPane(successfulSaveLabel, JOptionPane.PLAIN_MESSAGE);
            JPanel successfulSavePanel = (JPanel)successfulSavePane.getComponent(1);
            JButton okButton = (JButton)successfulSavePanel.getComponent(0);
            okButton.setFont(pixelMPlusFont.deriveFont(Font.PLAIN));
            JDialog dialog = successfulSavePane.createDialog(null,"Save Successful");
            dialog.setVisible(true);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // This method was taken from WorkRoomApp class in JsonSerializationDemo
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // MODIFIES: this
    // EFFECTS: loads order from file
    private void loadSavedOrder() {
        try {
            this.order = jsonReader.read();
            JLabel successfulLoadLabel = new JLabel("Your order has been loaded!");
            successfulLoadLabel.setFont(pixelMPlusFont.deriveFont(Font.PLAIN));
            JOptionPane successfulLoadPane = new JOptionPane(successfulLoadLabel, JOptionPane.PLAIN_MESSAGE);
            JPanel successfulLoadPanel = (JPanel)successfulLoadPane.getComponent(1);
            JButton okButton = (JButton)successfulLoadPanel.getComponent(0);
            okButton.setFont(pixelMPlusFont.deriveFont(Font.PLAIN));
            JDialog dialog = successfulLoadPane.createDialog(null,"Load Successful");
            dialog.setVisible(true);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: displays successful order message with order total
    private void finishAndPay() {
        JLabel successfulPayLabel = new JLabel("Your order total is $" + this.order.getOrderTotal()
                + ". Thank you for ordering!");
        successfulPayLabel.setFont(pixelMPlusFont.deriveFont(Font.PLAIN));
        JOptionPane successfulPayPane = new JOptionPane(successfulPayLabel, JOptionPane.PLAIN_MESSAGE);
        JPanel successfulPayPanel = (JPanel)successfulPayPane.getComponent(1);
        JButton okButton = (JButton)successfulPayPanel.getComponent(0);
        okButton.setFont(pixelMPlusFont.deriveFont(Font.PLAIN));
        JDialog dialog = successfulPayPane.createDialog(null,"Completed Order");
        dialog.setVisible(true);
    }

    // EFFECTS: returns the current order
    public Order getOrder() {
        return this.order;
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

    // EFFECTS: when mouse enters button component, respective button turns green
    @Override
    public void mouseEntered(MouseEvent e) {
        Color buttonLightUpColor = new Color(43, 141, 43, 255);
        if (e.getSource() == orderNewDrinkButton) {
            orderNewDrinkButton.setOpaque(true);
            orderNewDrinkButton.setForeground(buttonLightUpColor);
        }
        if (e.getSource() == viewMyOrderButton) {
            viewMyOrderButton.setOpaque(true);
            viewMyOrderButton.setForeground(buttonLightUpColor);
        }
        if (e.getSource() == saveCurrentOrderButton) {
            saveCurrentOrderButton.setOpaque(true);
            saveCurrentOrderButton.setForeground(buttonLightUpColor);
        }
        if (e.getSource() == loadSavedOrderButton) {
            loadSavedOrderButton.setOpaque(true);
            loadSavedOrderButton.setForeground(buttonLightUpColor);
        }
        if (e.getSource() == finishAndPayButton) {
            finishAndPayButton.setOpaque(true);
            finishAndPayButton.setForeground(buttonLightUpColor);
        }
    }

    // EFFECTS: when mouse leaves button component, button turns black
    @Override
    public void mouseExited(MouseEvent e) {
        orderNewDrinkButton.setOpaque(false);
        orderNewDrinkButton.setForeground(Color.black);
        viewMyOrderButton.setOpaque(false);
        viewMyOrderButton.setForeground(Color.black);
        saveCurrentOrderButton.setOpaque(false);
        saveCurrentOrderButton.setForeground(Color.black);
        loadSavedOrderButton.setOpaque(false);
        loadSavedOrderButton.setForeground(Color.black);
        finishAndPayButton.setOpaque(false);
        finishAndPayButton.setForeground(Color.black);
    }
}