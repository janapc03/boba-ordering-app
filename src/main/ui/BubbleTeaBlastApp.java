package ui;

import model.Drink;
import model.Order;

import java.util.Scanner;

// Bubble Tea Blast application
public class BubbleTeaBlastApp {
    private Order currentOrder;
    private Drink currentDrink;
    private Scanner input;

    // EFFECTS: runs the Bubble Tea Blast application
    public BubbleTeaBlastApp() {
        runBubbleTeaBlast();
    }

    // This method was taken from xxxxx
    // MODIFIES: this
    // EFFECTS: processes user input
    // !! NEEDS MODIFICATION !!
    private void runBubbleTeaBlast() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("f")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nThank you for ordering!");
    }

    // This method was taken from xxxx
    // MODIFIES: this
    // EFFECTS: processes user command
    // !!! NEEDS MODIFICATION !!!
    private void processCommand(String command) {
        if (command.equals("a")) {
            orderNewDrink();
        } else if (command.equals("c")) {
            changeDrink();
        } else if (command.equals("v")) {
            viewOrder();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // This method was taken from xxxxxx
    // MODIFIES: this
    // EFFECTS: initializes accounts
    // !! NEEDS MODIFICATION !!!
    private void init() {
   //    cheq = new Account("Joe", 145.00);
      //  sav = new Account("Joe", 256.50);
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // This method was taken from xxxxx
    // EFFECTS: displays menu of options to user
    // !!! NEEDS MODIFICATION !!
    private void displayMenu() {
        System.out.println("\nWelcome! What would you like to do?");
        System.out.println("\ta -> order a new drink");
        System.out.println("\tc -> change a drink in my order");
        System.out.println("\tv -> view my order");
        System.out.println("\tf -> finish ordering and pay");
    }

    private void orderNewDrink() {
        //stub
    }

    private void changeDrink() {
        //stub
    }

    private void viewOrder() {
        //stub
    }
}
