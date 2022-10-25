package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {
    private Order testOrder;
    private Drink testDrink1;
    private Drink testDrink2;
    private Drink testDrink3;

    @BeforeEach
    void runBefore() {
        testDrink1 = new Drink("Thai Milk Tea", 2);
        testDrink2 = new Drink("Green Milk Tea", 1);
        testDrink3 = new Drink("Coffee Smoothie", 2);
        testOrder = new Order("Test order");
    }

    @Test
    void testConstructor() {
        assertTrue(testOrder.getOrder().isEmpty());
        assertEquals(0, testOrder.getOrderTotal());
    }

    @Test
    void testAddDrink() {
        assertTrue(testOrder.getOrder().isEmpty());
        assertEquals(0, testOrder.getOrderTotal());
        testOrder.addDrink(testDrink1);
        assertEquals(1, testOrder.getNumDrinks());
        assertTrue(testOrder.getOrder().contains(testDrink1));
        assertEquals(testDrink1.getPrice(), testOrder.getOrderTotal());
    }

    @Test
    void testAddMultipleDrinks() {
        assertTrue(testOrder.getOrder().isEmpty());
        assertEquals(0, testOrder.getOrderTotal());
        testOrder.addDrink(testDrink1);
        assertTrue(testOrder.getOrder().contains(testDrink1));
        assertEquals(testDrink1.getPrice(), testOrder.getOrderTotal());
        assertEquals(testDrink1.getPrice(), testOrder.getOrderTotal());
        assertEquals(1, testOrder.getNumDrinks());
        testOrder.addDrink(testDrink2);
        assertTrue(testOrder.getOrder().contains(testDrink1));
        assertTrue(testOrder.getOrder().contains(testDrink2));
        assertEquals(testDrink1.getPrice() + testDrink2.getPrice(), testOrder.getOrderTotal());
        assertEquals(testDrink1.getPrice() + testDrink2.getPrice(), testOrder.getOrderTotal());
        assertEquals(2, testOrder.getNumDrinks());
        testOrder.addDrink(testDrink3);
        assertTrue(testOrder.getOrder().contains(testDrink1));
        assertTrue(testOrder.getOrder().contains(testDrink2));
        assertTrue(testOrder.getOrder().contains(testDrink3));
        assertEquals(testDrink1.getPrice() + testDrink2.getPrice() + testDrink3.getPrice(),
                testOrder.getOrderTotal());
        assertEquals(testDrink1.getPrice() + testDrink2.getPrice() + testDrink3.getPrice(),
                testOrder.getOrderTotal());
        assertEquals(3, testOrder.getNumDrinks());
    }

    @Test
    void testRemoveValidDrink() {
        assertTrue(testOrder.getOrder().isEmpty());
        assertEquals(0, testOrder.getOrderTotal());
        testOrder.addDrink(testDrink1);
        testOrder.addDrink(testDrink2);
        assertTrue(testOrder.getOrder().contains(testDrink1));
        assertTrue(testOrder.getOrder().contains(testDrink2));
        assertEquals(testDrink1.getPrice() + testDrink2.getPrice(), testOrder.getOrderTotal());
        assertEquals(2, testOrder.getNumDrinks());
        testOrder.removeDrink(testDrink1);
        assertFalse(testOrder.getOrder().contains(testDrink1));
        assertTrue(testOrder.getOrder().contains(testDrink2));
        assertEquals(testDrink2.getPrice(), testOrder.getOrderTotal());
        assertEquals(1, testOrder.getNumDrinks());
    }

    @Test
    void testRemoveDrinkTooFewDrinks() {
        assertTrue(testOrder.getOrder().isEmpty());
        assertEquals(0, testOrder.getOrderTotal());
        testOrder.addDrink(testDrink1);
        assertTrue(testOrder.getOrder().contains(testDrink1));
        assertEquals(testDrink1.getPrice(), testOrder.getOrderTotal());
        assertEquals(1, testOrder.getNumDrinks());
        testOrder.removeDrink(testDrink1);
        assertTrue(testOrder.getOrder().contains(testDrink1));
        assertEquals(testDrink1.getPrice(), testOrder.getOrderTotal());
        assertEquals(1, testOrder.getNumDrinks());
    }

    @Test
    void testRemoveDrinkNotInOrder() {
        assertTrue(testOrder.getOrder().isEmpty());
        assertEquals(0, testOrder.getOrderTotal());
        testOrder.addDrink(testDrink1);
        testOrder.addDrink(testDrink2);
        assertTrue(testOrder.getOrder().contains(testDrink1));
        assertTrue(testOrder.getOrder().contains(testDrink2));
        assertFalse(testOrder.getOrder().contains(testDrink3));
        assertEquals(testDrink1.getPrice() + testDrink2.getPrice(), testOrder.getOrderTotal());
        assertEquals(2, testOrder.getNumDrinks());
        testOrder.removeDrink(testDrink3);
        assertTrue(testOrder.getOrder().contains(testDrink1));
        assertTrue(testOrder.getOrder().contains(testDrink2));
        assertFalse(testOrder.getOrder().contains(testDrink3));
        assertEquals(testDrink1.getPrice() + testDrink2.getPrice(), testOrder.getOrderTotal());
        assertEquals(2, testOrder.getNumDrinks());
    }
}
