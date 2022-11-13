package ui;

import model.Drink;
import model.Order;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrderDrinkFrame extends JFrame implements ActionListener {
    private Order currentOrder;
    private JButton orderDrinkButton;
    private JRadioButton classicButton;
    private JRadioButton wintermelonButton;
    private JRadioButton matchaButton;
    private JRadioButton strawberryButton;
    private JRadioButton taroButton;
    private JRadioButton thaiButton;
    private JRadioButton smallSizeButton;
    private JRadioButton largeSizeButton;
    private JRadioButton bobaOne;
    private JRadioButton puddingOne;
    private JRadioButton jellyOne;
    private JRadioButton noToppingOne;
    private JRadioButton bobaTwo;
    private JRadioButton puddingTwo;
    private JRadioButton jellyTwo;
    private JRadioButton noToppingTwo;
    private String flavor;
    private int size;
    private String topping1;
    private String topping2;

    // MODIFIES: this
    // EFFECTS: creates and sets up frame
    public OrderDrinkFrame(Order order) {
        super("Order New Drink");
        this.currentOrder = order;
        flavor = "classic milk tea";
        size = 1;
        topping1 = null;
        topping2 = null;

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new GridLayout(4,1));
        this.setSize(640,360);

        orderDrinkButton = new JButton("Order Drink");
        orderDrinkButton.setBounds(30,285,100,70);
        orderDrinkButton.addActionListener(this);
        orderDrinkButton.setActionCommand("order drink");

        this.add(makeFlavorPanel());
        this.add(makeSizePanel());
        this.add(makeToppingsPanel());
        this.add(orderDrinkButton);

        this.pack();
        this.setVisible(true);
    }

    private JPanel makeFlavorPanel() {
        JLabel flavorPrompt = new JLabel("What flavor would you like?");

        JPanel flavorPanel = new JPanel();
        flavorPanel.setLayout(new GridLayout(2,1));
        flavorPanel.setSize(640,120);
        flavorPanel.setBorder(BorderFactory.createEmptyBorder(10,30,5,30));
        flavorPanel.add(flavorPrompt);
        flavorPanel.add(makeFlavorButtonsPanel());

        return flavorPanel;
    }

    private JPanel makeFlavorButtonsPanel() {
        setUpFlavorButtons();

        JPanel flavorButtonsPanel = new JPanel();
        flavorButtonsPanel.setLayout(new GridLayout(3, 2));
        flavorButtonsPanel.add(classicButton);
        flavorButtonsPanel.add(wintermelonButton);
        flavorButtonsPanel.add(matchaButton);
        flavorButtonsPanel.add(strawberryButton);
        flavorButtonsPanel.add(taroButton);
        flavorButtonsPanel.add(thaiButton);

        return flavorButtonsPanel;
    }

    private void setUpFlavorButtons() {
        classicButton = new JRadioButton("Classic Milk Tea");
        classicButton.addActionListener(this);
        wintermelonButton = new JRadioButton("Wintermelon Milk Tea");
        wintermelonButton.addActionListener(this);
        matchaButton = new JRadioButton("Matcha Milk Tea");
        matchaButton.addActionListener(this);
        strawberryButton = new JRadioButton("Strawberry Green Tea");
        strawberryButton.addActionListener(this);
        taroButton = new JRadioButton("Taro Milk Tea");
        taroButton.addActionListener(this);
        thaiButton = new JRadioButton("Thai Milk Tea");
        thaiButton.addActionListener(this);

        ButtonGroup flavorButtons = new ButtonGroup();
        flavorButtons.add(classicButton);
        flavorButtons.add(wintermelonButton);
        flavorButtons.add(matchaButton);
        flavorButtons.add(strawberryButton);
        flavorButtons.add(taroButton);
        flavorButtons.add(thaiButton);
    }

    private JPanel makeSizePanel() {
        JLabel sizePrompt = new JLabel("What size would you like?");

        JPanel sizePanel = new JPanel();
        sizePanel.setLayout(new GridLayout(2, 1));
        sizePanel.setSize(640,40);

        sizePanel.setBorder(BorderFactory.createEmptyBorder(5,30,5,30));

        sizePanel.add(sizePrompt);
        sizePanel.add(makeSizeButtonsPanel());

        return sizePanel;
    }

    private JPanel makeSizeButtonsPanel() {
        JPanel sizeButtonsPanel = new JPanel();
        sizeButtonsPanel.setLayout(new GridLayout(1, 2));

        setUpSizeButtons();

        sizeButtonsPanel.add(smallSizeButton);
        sizeButtonsPanel.add(largeSizeButton);

        return sizeButtonsPanel;
    }

    private void setUpSizeButtons() {
        smallSizeButton = new JRadioButton("Small");
        smallSizeButton.addActionListener(this);
        largeSizeButton = new JRadioButton("Large");
        largeSizeButton.addActionListener(this);

        ButtonGroup sizeButtons = new ButtonGroup();
        sizeButtons.add(smallSizeButton);
        sizeButtons.add(largeSizeButton);
    }

    private JPanel makeToppingsPanel() {
        JPanel toppingsPanel = new JPanel();
        toppingsPanel.setLayout(new GridLayout(2, 1));
        toppingsPanel.setSize(640,120);
        toppingsPanel.setBorder(BorderFactory.createEmptyBorder(5,30,5,30));

        JLabel toppingsPrompt = new JLabel("Would you like to add any toppings? (max. 2)");

        toppingsPanel.add(toppingsPrompt);
        toppingsPanel.add(makeToppingsButtonPanel());

        return toppingsPanel;
    }

    private JPanel makeToppingsButtonPanel() {
        JPanel toppingsButtonPanel = new JPanel();
        toppingsButtonPanel.setLayout(new GridLayout(5, 2));
        JLabel toppingOne = new JLabel("Topping 1:");
        JLabel toppingTwo = new JLabel("Topping 2:");
        setUpToppingOneButtons();
        setUpToppingTwoButtons();

        toppingsButtonPanel.add(toppingOne);
        toppingsButtonPanel.add(toppingTwo);
        toppingsButtonPanel.add(bobaOne);
        toppingsButtonPanel.add(bobaTwo);
        toppingsButtonPanel.add(puddingOne);
        toppingsButtonPanel.add(puddingTwo);
        toppingsButtonPanel.add(jellyOne);
        toppingsButtonPanel.add(jellyTwo);
        toppingsButtonPanel.add(noToppingOne);
        toppingsButtonPanel.add(noToppingTwo);

        return toppingsButtonPanel;
    }

    private void setUpToppingOneButtons() {
        bobaOne = new JRadioButton("Boba (+$1)");
        bobaOne.addActionListener(this);
        puddingOne = new JRadioButton("Pudding (+$1)");
        puddingOne.addActionListener(this);
        jellyOne = new JRadioButton("Jelly (+$1)");
        jellyOne.addActionListener(this);
        noToppingOne = new JRadioButton("None");
        noToppingOne.addActionListener(this);

        ButtonGroup toppingOneButtons = new ButtonGroup();
        toppingOneButtons.add(bobaOne);
        toppingOneButtons.add(puddingOne);
        toppingOneButtons.add(jellyOne);
        toppingOneButtons.add(noToppingOne);
    }

    private void setUpToppingTwoButtons() {
        bobaTwo = new JRadioButton("Boba (+$1)");
        bobaTwo.addActionListener(this);
        puddingTwo = new JRadioButton("Pudding (+$1)");
        puddingTwo.addActionListener(this);
        jellyTwo = new JRadioButton("Jelly (+$1)");
        jellyTwo.addActionListener(this);
        noToppingTwo = new JRadioButton("None");
        noToppingTwo.addActionListener(this);

        ButtonGroup toppingTwoButtons = new ButtonGroup();
        toppingTwoButtons.add(bobaTwo);
        toppingTwoButtons.add(puddingTwo);
        toppingTwoButtons.add(jellyTwo);
        toppingTwoButtons.add(noToppingTwo);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        assessFlavor(e);
        assessSize(e);
        assessTopping(e);
        if (e.getSource() == orderDrinkButton) {
            Drink currentDrink = new Drink(flavor, size);
            if (topping1 != null) {
                currentDrink.addTopping(topping1);
            }
            if (topping2 != null) {
                currentDrink.addTopping(topping2);
            }
            this.currentOrder.addDrink(currentDrink);
            this.dispose();
        }
    }

    private void assessFlavor(ActionEvent e) {
        if (e.getSource() == classicButton) {
            flavor = "classic milk tea";
        } else if (e.getSource() == wintermelonButton) {
            flavor = "wintermelon milk tea";
        } else if (e.getSource() == matchaButton) {
            flavor = "matcha milk tea";
        } else if (e.getSource() == strawberryButton) {
            flavor = "strawberry green tea";
        } else if (e.getSource() == taroButton) {
            flavor = "taro milk tea";
        } else if (e.getSource() == thaiButton) {
            flavor = "thai milk tea";
        }
    }

    private void assessSize(ActionEvent e) {
        if (e.getSource() == smallSizeButton) {
            size = 1;
        } else if (e.getSource() == largeSizeButton) {
            size = 2;
        }
    }

    private void assessTopping(ActionEvent e) {
        if (e.getSource() == bobaOne) {
            topping1 = "boba";
        } else if (e.getSource() == puddingOne) {
            topping1 = "pudding";
        } else if (e.getSource() == jellyOne) {
            topping1 = "jelly";
        } else if (e.getSource() == noToppingOne) {
            topping1 = null;
        }
        if (e.getSource() == bobaTwo) {
            topping2 = "boba";
        } else if (e.getSource() == puddingTwo) {
            topping2 = "pudding";
        } else if (e.getSource() == jellyTwo) {
            topping2 = "jelly";
        } else if (e.getSource() == noToppingTwo) {
            topping2 = null;
        }
    }
}
