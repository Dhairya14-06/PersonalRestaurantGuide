package com.example.personalrestaurantguide.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FakeRestaurantRepository {

    // Mutable in-memory list for the prototype
    private static final List<Restaurant> DATA = new ArrayList<>(Arrays.asList(
            new Restaurant("1","Spice Villa","123 King St, Toronto","416-555-0123",
                    "Modern Indian cuisine with cozy vibe", Arrays.asList("Indian","Dinner"),4),
            new Restaurant("2","Noodle Nook","45 Bay Ave, Toronto","416-555-0456",
                    "Quick ramen with veggie options", Arrays.asList("Ramen","Casual"),5),
            new Restaurant("3","Green Fork","9 Elm Rd, Toronto","416-555-0789",
                    "Healthy bowls & smoothies", Arrays.asList("Vegan","Lunch"),3)
    ));

    public static List<Restaurant> getRestaurants() {
        return DATA;
    }

    public static void addRestaurant(Restaurant r) {
        DATA.add(0, r);
    }
}
