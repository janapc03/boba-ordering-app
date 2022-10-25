package ui;

import model.Drink;
import model.Order;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Bubble Tea Blast application
public class BubbleTeaBlastApp {
    private Order currentOrder;
    private Drink currentDrink;
    private Scanner input;
    private static final String JSON_STORE = "./data/order.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the Bubble Tea Blast application
    public BubbleTeaBlastApp() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runBubbleTeaBlast();
    }

    // This method was taken from Teller App https://github.students.cs.ubc.ca/CPSC210/TellerApp.git
    // MODIFIES: this
    // EFFECTS: processes user input
    private void runBubbleTeaBlast() {
        boolean keepGoing = true;
        String command = null;

        init();

        System.out.println("Welcome to Bubble Tea Blast!");

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
        System.out.println("\nYour order total is $" + currentOrder.getOrderTotal());
        System.out.println("\nThank you for ordering!");
    }

    // This method was taken from Teller App https://github.students.cs.ubc.ca/CPSC210/TellerApp.git
    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("o")) {
            orderNewDrink();
        } else if (command.equals("c")) {
            System.out.println("Please select the number of the drink you would like to change.");
            printOrder();
            changeDrink();
        } else if (command.equals("v")) {
            viewOrder();
        } else if (command.equals("s")) {
            saveOrder();
        } else if (command.equals("l")) {
            loadOrder();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // This method was taken from Teller App https://github.students.cs.ubc.ca/CPSC210/TellerApp.git
    // MODIFIES: this
    // EFFECTS: initializes the current order
    private void init() {
        currentOrder = new Order("Your order");
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // This method was taken from Teller App https://github.students.cs.ubc.ca/CPSC210/TellerApp.git
    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nWhat would you like to do?");
        System.out.println("\to -> order a new drink");
        System.out.println("\tc -> change a drink in my order");
        System.out.println("\tv -> view my order");
        System.out.println("\ts -> save my order for next time");
        System.out.println("\tl -> load my order from last time");
        System.out.println("\tf -> finish ordering and pay");
    }

    // MODIFIES: currentOrder
    // EFFECTS: adds a new drink to the order based on user input for drink specifications
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

    // MODIFIES: currentDrink
    // REQUIRES: there is at least one drink in the order
    // EFFECTS: sets current drink to be changed to the one chosen by user
    private void changeDrink() {
        int command = 0;
        currentDrink = null;
        command = Integer.parseInt(input.next());

        for (Drink drink : currentOrder.getOrder()) {
            if (command == (currentOrder.getOrder().indexOf(drink) + 1)) {
                currentDrink = drink;
            }
        }
        chooseAspectToChange();
    }

    // EFFECTS: prints the list of all the drinks in the order for the customer to view
    private void viewOrder() {
        System.out.println("Your order currently contains:");
        printOrder();
        System.out.println("\nYour current order total is: $" + currentOrder.getOrderTotal());
    }

    // EFFECTS: prints the aspects of the drink that are available to change
    private void chooseAspectToChange() {
        System.out.println("\nWhich part of this drink would you like to change?");
        System.out.println("\tf -> flavor");
        System.out.println("\ts -> size");
        System.out.println("\tt -> toppings");
        changeAspectOfDrink();
    }

    // MODIFIES: this, currentDrink
    // EFFECTS: changes the flavor, size, or topping of drink based on user input
    private void changeAspectOfDrink() {
        String command = null;
        command = input.next();
        command = command.toLowerCase();

        if (command.equals("f")) {
            displayFlavorChoices();
            String newFlavor = chooseFlavor();
            currentDrink.changeFlavor(newFlavor);
        } else if (command.equals("s")) {
            System.out.println("Would you like a small or large size? (\"s\" or \"l\")");
            currentDrink.changeSize(chooseSize());
        } else if (command.equals("t")) {
            System.out.println("This drink currently has " + currentDrink.getToppings() + " in it.");
            System.out.println("You may only have a maximum of 2 toppings in your drink.");
            assessToppings();
        }
    }

    // EFFECTS: if the drink has < 2 toppings, asks the user if they'd like to add or remove a topping
    //          else, asks the user if they'd like to remove a topping
    private void assessToppings() {
        if (currentDrink.getNumToppings() < 2) {
            System.out.println("\nWould you like to add or remove a topping?");
            System.out.println("\ta -> add");
            System.out.println("\tr -> remove");
            promptAddOrRemoveTopping();
        } else {
            System.out.println("Would you like to remove a topping? (\"y\" or \"n\")");
            askRemoveTopping();
        }
    }

    // EFFECTS: assesses user input on whether they want to remove a topping or not
    private void askRemoveTopping() {
        String command = null;
        command = input.next();
        command = command.toLowerCase();

        if (command.equals("y")) {
            printToppingsInDrink();
            promptRemoveTopping();
        } else if (command.equals("n")) {
            System.out.println("Okay, the toppings in this drink will stay the same!");
        }
    }

    // EFFECTS: assesses user input on whether they want to add or remove a topping
    private void promptAddOrRemoveTopping() {
        String command = null;
        command = input.next();
        command = command.toLowerCase();

        if (command.equals("a")) {
            if (currentDrink.getNumToppings() < 2) {
                displayToppingsChoices();
            }
        } else if (command.equals("r")) {
            printToppingsInDrink();
            promptRemoveTopping();
        }
    }

    // REQUIRES: currentDrink has 1 or 2 toppings
    // EFFECTS: prints the toppings in currentDrink
    private void printToppingsInDrink() {
        System.out.println("Which topping would you like to remove?");
        System.out.println("1 -> " + currentDrink.getToppings().get(0));

        if (currentDrink.getNumToppings() == 2) {
            System.out.println("2 -> " + currentDrink.getToppings().get(1));
        }
    }

    // MODIFIES: toppings in currentDrink
    // EFFECTS: removes a topping in currentDrink based on topping given by user input
    private void promptRemoveTopping() {
        int command = 0;

        if (input.hasNextInt()) {
            command = input.nextInt();
            if (command == 1) {
                currentDrink.getToppings().remove(currentDrink.getToppings().get(0));
            } else if (command == 2) {
                currentDrink.getToppings().remove(currentDrink.getToppings().get(1));
            }
        } else {
            System.out.println("That's not a number!");
        }
        System.out.println("This topping has been removed!");
    }

    // EFFECTS: converts size int into a String representation
    private String stringSize(int size) {
        if (size == 1) {
            return "small";
        }
        return "large";
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

    // EFFECTS: prints the drinks in the order
    private void printOrder() {
        for (Drink drink : currentOrder.getOrder()) {
            System.out.println("\t" + (currentOrder.getOrder().indexOf(drink) + 1) + " - A "
                    + stringSize(drink.getSize()) + " "
                    + drink.getFlavor()
                    + " with toppings "
                    + drink.getToppings()
                    + ", $" + drink.getPrice());
        }
    }

    //!!! This method was taken from JsonSerializationDemo
    // EFFECTS: saves the order to file
    private void saveOrder() {
        try {
            jsonWriter.open();
            jsonWriter.write(currentOrder);
            jsonWriter.close();
            System.out.println("Saved " + currentOrder.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    //!!! This method was taken from JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: loads order from file
    private void loadOrder() {
        try {
            currentOrder = jsonReader.read();
            System.out.println("Loaded " + currentOrder.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
