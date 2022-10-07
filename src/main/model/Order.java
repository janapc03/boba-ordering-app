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
        this.order = new ArrayList<>();
        this.order.add(drink);
        this.orderTotal = drink.getPrice();
    }

    // MODIFIES: this
    // EFFECTS: adds drink to the order and increases order total
    public void addDrink(Drink drink) {
        this.order.add(drink);
        this.orderTotal = this.orderTotal + drink.getPrice();
    }

    // MODIFIES: this
    // EFFECTS: if the given drink is in the order and the number of drinks in the order is > 1,
    //             - removes drink from the order
    //             - decreases order total
    //             - returns true
    //           else, returns false
    public boolean removeDrink(Drink drink) {
        if (this.order.contains(drink) && (getNumDrinks() > 1)) {
            this.order.remove(drink);
            this.orderTotal = this.orderTotal - drink.getPrice();
            return true;
        }
        return false;
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
        return this.order.size();
    }
}
