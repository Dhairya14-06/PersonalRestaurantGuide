package com.example.personalrestaurantguide.data;
import android.content.Context;
import androidx.room.*;

@Database(entities = {RestaurantEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract RestaurantDao restaurantDao();
    private static volatile AppDatabase INSTANCE;
    public static AppDatabase get(Context ctx){
        if(INSTANCE==null){
            synchronized(AppDatabase.class){
                if(INSTANCE==null){
                    INSTANCE = Room.databaseBuilder(ctx.getApplicationContext(),
                            AppDatabase.class, "prg.db").build();
                }
            }
        }
        return INSTANCE;
    }
}
