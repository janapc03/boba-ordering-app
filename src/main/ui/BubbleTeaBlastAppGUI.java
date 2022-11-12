package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BubbleTeaBlastAppGUI implements ActionListener {
    private PrimaryFrame primaryFrame;
    private ImageIcon bubbleTeaShopImageIcon;
    private JLabel bubbleTeaShopLabel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JLabel welcomeLabel;
    private JButton orderNewDrinkButton;
    private JButton viewMyOrderButton;
    private JButton saveCurrentOrderButton;
    private JButton loadSavedOrderButton;
    private JButton finishAndPayButton;

    public BubbleTeaBlastAppGUI() {
        primaryFrame = new PrimaryFrame();

        bubbleTeaShopImageIcon = createImageIcon("./images/BubbleTeaShopImage.png");

        primaryFrame.add(makeBubbleTeaShopPanel());
        primaryFrame.add(makeMainMenuPanel());
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
        setUpButtons();
        welcomeLabel = new JLabel("Welcome to Bubble Tea Blast! What would you like to do?");

        rightPanel = new JPanel();
        rightPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        rightPanel.setLayout(new GridLayout(6,1));
        rightPanel.setBounds(0,0, 480,540);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(40,60,80,60));
        rightPanel.add(welcomeLabel);
        rightPanel.add(orderNewDrinkButton);
        rightPanel.add(viewMyOrderButton);
        rightPanel.add(saveCurrentOrderButton);
        rightPanel.add(loadSavedOrderButton);
        rightPanel.add(finishAndPayButton);
        return rightPanel;
    }

    private void setUpButtons() {
        orderNewDrinkButton = new JButton("Order New Drink");
        orderNewDrinkButton.addActionListener(this);
        orderNewDrinkButton.setActionCommand("order new drink");
        viewMyOrderButton = new JButton("View My Order");
        viewMyOrderButton.addActionListener(this);
        viewMyOrderButton.setActionCommand("view my order");
        saveCurrentOrderButton = new JButton("Save Current Order");
        saveCurrentOrderButton.addActionListener(this);
        saveCurrentOrderButton.setActionCommand("save current order");
        loadSavedOrderButton = new JButton("Load Saved Order");
        loadSavedOrderButton.addActionListener(this);
        loadSavedOrderButton.setActionCommand("load saved order");
        finishAndPayButton = new JButton("Finish and Pay");
        finishAndPayButton.addActionListener(this);
        finishAndPayButton.setActionCommand("finish and pay");
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
        //!!!
    }
}