package com.example.personalrestaurantguide.data;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "restaurants")
public class RestaurantEntity {
    @PrimaryKey @NonNull public String id;
    public String name, address, phone, description, tagsCsv;
    public int rating;

    public RestaurantEntity(@NonNull String id, String name, String address, String phone,
                            String description, String tagsCsv, int rating) {
        this.id=id; this.name=name; this.address=address; this.phone=phone;
        this.description=description; this.tagsCsv=tagsCsv; this.rating=rating;
    }
}
