package model;

import model.Drink;

import java.util.ArrayList;
import java.util.List;

// Represents an order containing at least one drink
public class Order {
    private List<Drink> order;
    private int orderTotal;

    // EFFECTS: constructs an order containing at least one drink and having an order total of the price of that drink
    public Order(Drink drink) {
        order = new ArrayList<>();
        order.add(drink);
        this.orderTotal = drink.getPrice();
    }

    // MODIFIES: this
    // EFFECTS: adds drink to the order and increases order total
    public void addDrink(Drink drink) {
        //stub
    }

    // REQUIRES: given drink is in the order
    // MODIFIES: this
    // EFFECTS: if the number of drinks in the order is > 1,
    //             - removes drink from the order
    //             - decreases order total
    //             - returns true
    //           else, returns false
    public boolean removeDrink(Drink drink) {
        return false; //stub
    }

    // EFFECTS: returns the given drink in the order
    public Drink getDrink(Drink drink) {
        return drink; //stub
    }

    // EFFECTS: returns the list of drinks in the order
    public List<Drink> getOrder() {
        return this.order;
    }

    // EFFECTS: returns the order total
    public int getOrderTotal() {
        return this.orderTotal;
    }

    // EFFECTS: returns the number of drinks in the order
    public int getNumDrinks() {
        return 0; //stub
    }
}
