package persistence;

import model.Drink;
import model.Order;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// This class was derived from JsonReader class in JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
// Represents a reader that reads order from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads order from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Order read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseOrder(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses order from JSON object and returns it
    private Order parseOrder(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Order order = new Order(name);
        addDrinks(order, jsonObject);
        return order;
    }

    // MODIFIES: order
    // EFFECTS: parses drinks from JSON object and adds them to order
    private void addDrinks(Order order, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("drinks");
        for (Object json : jsonArray) {
            JSONObject nextDrink = (JSONObject) json;
            addDrink(order, nextDrink);
        }
    }

    // MODIFIES: order
    // EFFECTS: parses drink from JSON object and adds it to order
    private void addDrink(Order order, JSONObject jsonObject) {
        String flavor = jsonObject.getString("flavor");
        int size = jsonObject.getInt("size");
        JSONArray toppings = jsonObject.getJSONArray("toppings");
        Drink drink = new Drink(flavor, size);
        if (toppings.length() == 2) {
            drink.addTopping(toppings.getString(0));
            drink.addTopping(toppings.getString(1));
        } else if (toppings.length() == 1) {
            drink.addTopping(toppings.getString(0));
        }
        order.addDrink(drink);
    }
}
