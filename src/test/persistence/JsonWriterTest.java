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
class JsonWriterTest extends JsonTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            Order order = new Order("Your order");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyOrder() {
        try {
            Order order = new Order("Your order");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyOrder.json");
            writer.open();
            writer.write(order);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyOrder.json");
            order = reader.read();
            assertEquals("Your order", order.getName());
            assertEquals(0, order.getOrderTotal());
            assertEquals(0, order.getNumDrinks());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralOrder() {
        try {
            Order order = new Order("Your order");
            Drink drink1 = new Drink("classic milk tea", 2);
            drink1.addTopping("boba");
            Drink drink2 = new Drink("matcha milk tea",1);
            drink2.addTopping("pudding");
            drink2.addTopping("jelly");
            order.addDrink(drink1);
            order.addDrink(drink2);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralOrder.json");
            writer.open();
            writer.write(order);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralOrder.json");
            order = reader.read();
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
            fail("Exception should not have been thrown");
        }
    }
}