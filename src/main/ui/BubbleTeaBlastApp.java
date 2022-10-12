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
        if (command.equals("o")) {
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
    // EFFECTS: initializes the current order
    // !! NEEDS MODIFICATION !!!
    private void init() {
        currentOrder = new Order();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // This method was taken from xxxxx
    // EFFECTS: displays menu of options to user
    // !!! NEEDS MODIFICATION !!
    private void displayMenu() {
        System.out.println("\nWelcome! What would you like to do?");
        System.out.println("\to -> order a new drink");
        System.out.println("\tc -> change a drink in my order");
        System.out.println("\tv -> view my order");
        System.out.println("\tf -> finish ordering and pay");
    }

    // MODIFIES: currentOrder
    // EFFECTS: adds a new drink to the order
    private void orderNewDrink() {
        String currentFlavor;
        int currentSize;
        displayFlavorChoices();
        currentFlavor = chooseFlavor();
        System.out.println("Would you like a small or large size? (\"s\" or \"l\")");
        currentSize = chooseSize();
        currentDrink = new Drink(currentFlavor, currentSize);
        currentOrder.addDrink(currentDrink);
        System.out.println("There are currently " + currentDrink.getNumToppings() + " toppings in this drink.");
        if (currentDrink.getNumToppings() < 2) {
            System.out.println("Would you like to add a topping to your drink? (\"y\" or \"n\")");
            promptAddToppings();
        } else {
            System.out.println("You've reached the maximum number of toppings for your drink!");
        }
        System.out.println("This drink has been added to your order!");
    }

    // MODIFIES: !!!!
    // EFFECTS: changes an aspect of one drink in the order
    private void changeDrink() {
        //stub
    }

    // EFFECTS: prints the list of all the drinks in the order for the customer to view
    private void viewOrder() {
        //stub
    }

    // EFFECTS: allows user to choose flavor of new drink
    private String chooseFlavor() {
        String command = null;
        command = input.next();
        command = command.toLowerCase();

        if (command.equals("c")) {
            return "classic milk tea";
        } else if (command.equals("m")) {
            return "matcha milk tea";
        } else if (command.equals("w")) {
            return "wintermelon milk tea";
        } else if (command.equals("s")) {
            return "strawberry green tea";
        } else if (command.equals("p")) {
            return "passionfruit green tea";
        } else if (command.equals("k")) {
            return "kiwi green tea";
        } else {
            System.out.println("Sorry, we don't have that flavor... Let me pick for you!");
            return "classic milk tea";
        }
    }

    // EFFECTS: allows user to choose size of new drink
    private int chooseSize() {
        String command = null;
        command = input.next();
        command = command.toLowerCase();

        if (command.equals("s")) {
            return 1;
        } else if (command.equals("l")) {
            return 2;
        } else {
            System.out.println("Sorry, we don't have that size... How about a small?");
            return 1;
        }
    }

    // EFFECTS: allows user to add a maximum of 2 toppings to their drink
    private void addToppings() {
        String command = null;
        command = input.next();
        command = command.toLowerCase();

        if (command.equals("b")) {
            currentDrink.addTopping("boba");
            System.out.println("Boba has been added to this drink!");
        } else if (command.equals("p")) {
            currentDrink.addTopping("pudding");
            System.out.println("Pudding has been added to this drink!");
        } else if (command.equals("j")) {
            currentDrink.addTopping("jelly");
            System.out.println("Jelly has been added to this drink!");
        } else {
            System.out.println("Sorry, we don't have that topping...");
        }

        if (currentDrink.getNumToppings() < 2) {
            System.out.println("Would you like to add another topping to your drink? (\"y\" or \"n\")");
            promptAddToppings();
        } else {
            System.out.println("You've reached the maximum number of toppings for your drink!");
        }
    }

    // EFFECTS: asks user if they want toppings on their new drink
    private void promptAddToppings() {
        String command = null;
        command = input.next();
        command = command.toLowerCase();

        if (command.equals("y")) {
            displayToppingsChoices();
        } else if (command.equals("n")) {
            System.out.println("Okay, no toppings!");
        } else {
            System.out.println("Sorry, we don't have that topping... How about no toppings for this drink?");
        }
    }

    // EFFECTS: displays a list of toppings to choose from
    private void displayToppingsChoices() {
        System.out.println("\nWhat topping would you like?");
        System.out.println("\tb -> boba");
        System.out.println("\tp -> pudding");
        System.out.println("\tj -> jelly");
        addToppings();
    }

    // EFFECTS: displays a list of flavors to choose from
    private void displayFlavorChoices() {
        System.out.println("\nWhat flavor would you like?");
        System.out.println("\tc -> classic milk tea");
        System.out.println("\tm -> matcha milk tea");
        System.out.println("\tw -> wintermelon milk tea");
        System.out.println("\ts -> strawberry green tea");
        System.out.println("\tp -> passionfruit green tea");
        System.out.println("\tk -> kiwi green tea");
    }
}
