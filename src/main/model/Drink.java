package model;

import java.util.ArrayList;
import java.util.List;

// Represents a drink, having a flavor, size, price, and a maximum of 2 toppings.
public class Drink {
    private String flavor;
    private int size;
    private List<String> toppings;
    private int price;
    public static final int STARTING_PRICE = 5;

    // REQUIRES: size = 1 or 2
    // EFFECTS: constructs a drink having a flavor, size, starting price, and no toppings.
    //          - size 1 represents a small drink
    //          - size 2 represents a large drink
    public Drink(String flavor, int size) {
        this.flavor = flavor;
        this.size = size;
        this.price = STARTING_PRICE;
        this.toppings = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: if the drink has < 2 toppings,
    //            - the new topping is added to the drink
    //            - drink price is increased by $1
    //            - returns true
    //         else, returns false
    public boolean addTopping(String newTopping) {
        if (this.toppings.size() < 2) {
            this.toppings.add(newTopping);
            this.price = this.price + 1;
            return true;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: changes the flavor of the drink from the old one to the specified new flavor
    public void changeFlavor(String newFlavor) {
        this.flavor = newFlavor;
    }

    // REQUIRES: newSize is either 1 or 2
    // MODIFIES: this
    // EFFECTS: changes the size of the drink from the old size to the new size, with 1 being a small and 2 a large
    public void changeSize(int newSize) {
        this.size = newSize;
    }

    // MODIFIES: this
    // EFFECTS: if the drink contains the given topping,
    //            - the topping is removed from the drink
    //            - drink price is decreased by $1
    //            - returns true
    //         else, returns false
    public boolean removeTopping(String topping) {
        if (this.toppings.contains(topping)) {
            this.toppings.remove(topping);
            this.price = this.price - 1;
            return true;
        }
        return false;
    }

    // EFFECTS: returns flavor of the drink
    public String getFlavor() {
        return this.flavor;
    }

    // EFFECTS: returns size of the drink
    public int getSize() {
        return this.size;
    }

    // EFFECTS: returns price of the drink
    public int getPrice() {
        return this.price;
    }

    // EFFECTS: returns toppings in the drink
    public List<String> getToppings() {
        return this.toppings;
    }
}
