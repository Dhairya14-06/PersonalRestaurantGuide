package com.example.personalrestaurantguide.data;
import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.*;
import java.util.List;

public class RestaurantViewModel extends AndroidViewModel {
    private final RestaurantRepository repo;
    private LiveData<List<RestaurantEntity>> liveList;

    public RestaurantViewModel(@NonNull Application app){
        super(app);
        repo = new RestaurantRepository(app);
        liveList = repo.getAll();
    }
    public LiveData<List<RestaurantEntity>> getRestaurants(){ return liveList; }
    public void search(String q){ liveList = (q==null || q.trim().isEmpty()) ? repo.getAll() : repo.search(q.trim()); }
    public LiveData<List<RestaurantEntity>> getLiveList(){ return liveList; }
    public void add(String name, String address, String phone, String desc, String tagsCsv){ repo.addNew(name,address,phone,desc,tagsCsv); }
    public void deleteById(String id){ repo.deleteById(id); }
    public LiveData<RestaurantEntity> getById(String id) { return repo.getById(id); }
    public void rate(String id, int rating) { repo.rate(id, rating); }

}
