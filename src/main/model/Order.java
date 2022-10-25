package model;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents an order containing at least one drink
public class Order implements Writable {
    private List<Drink> order;
    private int orderTotal;
    private String name;

    // EFFECTS: constructs an order with a name containing no drinks and having an order total of $0
    public Order(String name) {
        this.name = name;
        this.order = new ArrayList<>();
        this.orderTotal = 0;
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

    // EFFECTS: returns the name of an order
    public String getName() {
        return this.name;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("drinks", drinksToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray drinksToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Drink drink : order) {
            jsonArray.put(drink.toJson());
        }

        return jsonArray;
    }
}
