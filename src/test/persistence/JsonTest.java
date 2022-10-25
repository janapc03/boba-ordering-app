package persistence;

import model.Drink;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

// This method was derived from JsonTest in JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonTest {
    protected void checkDrink(String flavor, int size, List<String> toppings, int price, Drink drink) {
        assertEquals(flavor, drink.getFlavor());
        assertEquals(size, drink.getSize());
        assertEquals(toppings, drink.getToppings());
        assertEquals(price, drink.getPrice());
    }
}
