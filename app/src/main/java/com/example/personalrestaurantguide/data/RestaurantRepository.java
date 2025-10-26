package com.example.personalrestaurantguide.data;
import android.content.Context;
import androidx.lifecycle.LiveData;
import java.util.*;
import java.util.concurrent.*;

public class RestaurantRepository {
    private final RestaurantDao dao;
    private final ExecutorService exec = Executors.newSingleThreadExecutor();

    public RestaurantRepository(Context ctx){ dao = AppDatabase.get(ctx).restaurantDao(); }
    public LiveData<List<RestaurantEntity>> getAll(){ return dao.getAll(); }
    public LiveData<List<RestaurantEntity>> search(String q){ return dao.search(q); }

    public void addNew(String name, String address, String phone, String desc, String tagsCsv){
        RestaurantEntity e = new RestaurantEntity(UUID.randomUUID().toString(), name, address, phone, desc, tagsCsv, 3);
        exec.execute(() -> dao.insert(e));
    }
    public void deleteById(String id){ exec.execute(() -> dao.deleteById(id)); }

    public LiveData<RestaurantEntity> getById(String id) { return dao.getById(id); }

    public void rate(String id, int rating) {
        exec.execute(() -> dao.setRating(id, rating));
    }

}
