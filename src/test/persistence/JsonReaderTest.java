package persistence;

import model.Order;
import model.Drink;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// This class was derived from JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
class JsonReaderTest extends JsonTest {

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

    @Test
    void testReaderGeneralOrder() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralOrder.json");
        try {
            Order order = reader.read();
            assertEquals("Your order", order.getName());
            List<Drink> drinks = order.getOrder();
            assertEquals(2, drinks.size());
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

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}