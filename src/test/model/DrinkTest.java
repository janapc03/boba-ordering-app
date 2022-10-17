package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DrinkTest {
    private Drink testDrink;

    @BeforeEach
    void runBefore() {
        testDrink = new Drink("Mango Milk Tea", 1);
    }

    @Test
    void testConstructor() {
        assertEquals("Mango Milk Tea", testDrink.getFlavor());
        assertEquals(1, testDrink.getSize());
        assertEquals(Drink.STARTING_PRICE, testDrink.getPrice());
        assertTrue(testDrink.getToppings().isEmpty());
    }

    @Test
    void testAddOneTopping() {
        assertTrue(testDrink.getToppings().isEmpty());
        testDrink.addTopping("Boba");
        assertTrue(testDrink.getToppings().contains("Boba"));
        assertEquals(Drink.STARTING_PRICE + 1, testDrink.getPrice());
        assertEquals(1, testDrink.getNumToppings());
    }

    @Test
    void testAddTooManyToppings() {
        assertTrue(testDrink.getToppings().isEmpty());
        testDrink.addTopping("Boba");
        assertTrue(testDrink.getToppings().contains("Boba"));
        assertEquals(Drink.STARTING_PRICE + 1, testDrink.getPrice());
        testDrink.addTopping("Pudding");
        assertTrue(testDrink.getToppings().contains("Boba"));
        assertTrue(testDrink.getToppings().contains("Pudding"));
        assertEquals(Drink.STARTING_PRICE + 2, testDrink.getPrice());
        assertEquals(2, testDrink.getNumToppings());
        testDrink.addTopping("Jelly");
        assertFalse(testDrink.getToppings().contains("Jelly"));
        assertEquals(Drink.STARTING_PRICE + 2, testDrink.getPrice());
        assertEquals(2, testDrink.getNumToppings());
    }

    @Test
    void testChangeFlavor() {
        assertEquals("Mango Milk Tea", testDrink.getFlavor());
        testDrink.changeFlavor("Honey Black Tea");
        assertEquals("Honey Black Tea", testDrink.getFlavor());
    }

    @Test
    void testChangeSize() {
        assertEquals(1, testDrink.getSize());
        testDrink.changeSize(2);
        assertEquals(2, testDrink.getSize());
    }

    @Test
    void testRemoveValidTopping() {
        testDrink.addTopping("Boba");
        testDrink.addTopping("Pudding");
        assertTrue(testDrink.getToppings().contains("Boba"));
        assertTrue(testDrink.getToppings().contains("Pudding"));
        assertEquals(Drink.STARTING_PRICE + 2, testDrink.getPrice());
        assertEquals(2, testDrink.getNumToppings());
        testDrink.removeTopping("Boba");
        assertFalse(testDrink.getToppings().contains("Boba"));
        assertTrue(testDrink.getToppings().contains("Pudding"));
        assertEquals(Drink.STARTING_PRICE + 1, testDrink.getPrice());
        assertEquals(1, testDrink.getNumToppings());
    }

    @Test
    void testRemoveToppingNotInDrink() {
        testDrink.addTopping("Boba");
        testDrink.addTopping("Pudding");
        assertTrue(testDrink.getToppings().contains("Boba"));
        assertTrue(testDrink.getToppings().contains("Pudding"));
        assertEquals(Drink.STARTING_PRICE + 2, testDrink.getPrice());
        assertEquals(2, testDrink.getNumToppings());
        testDrink.removeTopping("Jelly");
        assertTrue(testDrink.getToppings().contains("Boba"));
        assertTrue(testDrink.getToppings().contains("Pudding"));
        assertEquals(Drink.STARTING_PRICE + 2, testDrink.getPrice());
        assertEquals(2, testDrink.getNumToppings());
    }
}