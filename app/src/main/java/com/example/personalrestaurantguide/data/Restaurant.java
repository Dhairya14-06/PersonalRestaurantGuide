package com.example.personalrestaurantguide.data;
import java.util.List;

public class Restaurant {
    private String id, name, address, phone, description;
    private List<String> tags;
    private int rating;

    public Restaurant(String id, String name, String address, String phone,
                      String description, List<String> tags, int rating) {
        this.id = id; this.name = name; this.address = address; this.phone = phone;
        this.description = description; this.tags = tags; this.rating = rating;
    }

    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getPhone() { return phone; }
    public String getDescription() { return description; }
    public List<String> getTags() { return tags; }
    public int getRating() { return rating; }
}
