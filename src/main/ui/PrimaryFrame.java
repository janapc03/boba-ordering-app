package ui;

import javax.swing.*;
import java.awt.*;

// Represents the primary frame of the application
public class PrimaryFrame extends JFrame {

    // MODIFIES: this
    // EFFECTS: creates and sets up primary frame
    public PrimaryFrame() {
        super("Bubble Tea Blast");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(1, 2));

        this.setSize(960,540);
    }
}
