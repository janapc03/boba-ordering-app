package ui;

import javax.swing.*;
import java.awt.*;

public class PrimaryFrame extends JFrame {

    // MODIFIES: this
    // EFFECTS: creates and sets up frame
    public PrimaryFrame() {
        super("Bubble Tea Blast");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(1, 2));

        this.setSize(960,540);
        this.setVisible(true);
    }
}
