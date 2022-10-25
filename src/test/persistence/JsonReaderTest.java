package persistence;

import model.Order;
import model.Drink;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonReaderTest extends JsonTest {

    // This method was derived from JsonReaderTest class in JsonSerializationDemo
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Order order = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    // This method was derived from JsonReaderTest class in JsonSerializationDemo
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    @Test
    void testReaderEmptyOrder() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyOrder.json");
        try {
            Order order = reader.read();
            assertEquals("Your order", order.getName());
            assertEquals(0, order.getOrderTotal());
            assertEquals(0, order.getNumDrinks());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    // This method was derived from JsonReaderTest class in JsonSerializationDemo
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    @Test
    void testReaderGeneralOrder() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralOrder.json");
        try {
            Order order = reader.read();
            assertEquals("Your order", order.getName());
            List<Drink> drinks = order.getOrder();
            assertEquals(3, drinks.size());
            checkDrink("classic milk tea",
                    drinks.get(0).getSize(),
                    drinks.get(0).getToppings(),
                    drinks.get(0).getPrice(),
                    drinks.get(0));
            checkDrink("matcha milk tea",
                    drinks.get(1).getSize(),
                    drinks.get(1).getToppings(),
                    drinks.get(1).getPrice(),
                    drinks.get(1));
            checkDrink("wintermelon milk tea",
                    drinks.get(2).getSize(),
                    drinks.get(2).getToppings(),
                    drinks.get(2).getPrice(),
                    drinks.get(2));

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}