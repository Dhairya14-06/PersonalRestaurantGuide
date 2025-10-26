package com.example.personalrestaurantguide.data;
import androidx.lifecycle.LiveData;
import androidx.room.*;
import java.util.List;

@Dao
public interface RestaurantDao {
    @Query("SELECT * FROM restaurants ORDER BY name ASC")
    LiveData<List<RestaurantEntity>> getAll();

    @Query("SELECT * FROM restaurants WHERE name LIKE '%' || :q || '%' OR tagsCsv LIKE '%' || :q || '%' ORDER BY name ASC")
    LiveData<List<RestaurantEntity>> search(String q);

    @Insert(onConflict = OnConflictStrategy.REPLACE) void insert(RestaurantEntity e);
    @Update void update(RestaurantEntity e);
    @Delete void delete(RestaurantEntity e);

    @Query("DELETE FROM restaurants WHERE id = :id") void deleteById(String id);

    @Query("SELECT COUNT(*) FROM restaurants")
    int countSync();

    @Query("SELECT * FROM restaurants WHERE id = :id LIMIT 1")
    LiveData<RestaurantEntity> getById(String id);

    @Query("UPDATE restaurants SET rating = :rating WHERE id = :id")
    void setRating(String id, int rating);


}
