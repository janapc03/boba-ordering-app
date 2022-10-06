package model;

import model.Drink;
import java.util.List;

// Represents an order containing at least one drink
public class Order {
    private List<Drink> order;
    private int orderTotal;

    // EFFECTS: constructs an order containing at least one drink and having an order total of the price of that drink
    public Order(Drink drink) {
        order.add(drink);
        this.orderTotal = drink.getPrice();
    }

    // MODIFIES: this
    // EFFECTS: adds drink to the order and increases order total
    private void addDrink(Drink drink) {
        //stub
    }

    // REQUIRES: given drink is in the order
    // MODIFIES: this
    // EFFECTS: removes drink to the order and decreases order total
    private void removeDrink(Drink drink) {
        //stub
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
}
