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

    public BubbleTeaBlastAppGUI() {
        this.order = new Order("Your Order");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        primaryFrame = new PrimaryFrame();

        bubbleTeaShopImageIcon = createImageIcon("./images/BubbleTeaShopImage.png");

        primaryFrame.add(makeBubbleTeaShopPanel());
        primaryFrame.add(makeMainMenuPanel());
        primaryFrame.pack();
        primaryFrame.setVisible(true);
    }

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

    private void setUpButtons() {
        Font buttonFont = pixelMPlusFont.deriveFont(Font.PLAIN);
        orderNewDrinkButton = new JButton("Order New Drink");
        orderNewDrinkButton.addActionListener(this);
        orderNewDrinkButton.setActionCommand("order new drink");
        orderNewDrinkButton.addMouseListener(this);
        orderNewDrinkButton.setFont(buttonFont);
        viewMyOrderButton = new JButton("View My Order");
        viewMyOrderButton.addActionListener(this);
        viewMyOrderButton.setActionCommand("view my order");
        viewMyOrderButton.addMouseListener(this);
        viewMyOrderButton.setFont(buttonFont);
        saveCurrentOrderButton = new JButton("Save Current Order");
        saveCurrentOrderButton.addActionListener(this);
        saveCurrentOrderButton.setActionCommand("save current order");
        saveCurrentOrderButton.addMouseListener(this);
        saveCurrentOrderButton.setFont(buttonFont);
        loadSavedOrderButton = new JButton("Load Saved Order");
        loadSavedOrderButton.addActionListener(this);
        loadSavedOrderButton.setActionCommand("load saved order");
        loadSavedOrderButton.addMouseListener(this);
        loadSavedOrderButton.setFont(buttonFont);
        finishAndPayButton = new JButton("Finish and Pay");
        finishAndPayButton.addActionListener(this);
        finishAndPayButton.setActionCommand("finish and pay");
        finishAndPayButton.addMouseListener(this);
        finishAndPayButton.setFont(buttonFont);
    }

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

    // EFFECTS: creates an image icon
    private ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = this.getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("order new drink".equals(e.getActionCommand())) {
            OrderDrinkFrame orderDrinkFrame = new OrderDrinkFrame(this.order);

        } else if ("view my order".equals(e.getActionCommand())) {
            ViewOrderFrame viewOrderFrame = new ViewOrderFrame(this.order);

        } else if ("save current order".equals(e.getActionCommand())) {
            saveCurrentOrder();
        } else if ("load saved order".equals(e.getActionCommand())) {
            loadSavedOrder();
        } else if ("finish and pay".equals(e.getActionCommand())) {
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
            JOptionPane.showMessageDialog(primaryFrame, "Your order has been saved!", "Save Successful",
                    JOptionPane.PLAIN_MESSAGE);
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
            JOptionPane.showMessageDialog(primaryFrame, "Your order has been loaded!", "Load Successful",
                    JOptionPane.PLAIN_MESSAGE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    private void finishAndPay() {
        JOptionPane.showMessageDialog(primaryFrame, "Your order total is $" + this.order.getOrderTotal()
                        + ". Thank you for ordering!", "Completed Order", JOptionPane.PLAIN_MESSAGE);
    }

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