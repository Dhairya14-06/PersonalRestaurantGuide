package com.example.personalrestaurantguide;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.personalrestaurantguide.data.AppDatabase;
import com.example.personalrestaurantguide.data.RestaurantRepository;
import com.example.personalrestaurantguide.databinding.ActivitySplashBinding;

/**
 * Splash screen: shows briefly, seeds dummy restaurants if database is empty,
 * then navigates to MainActivity.
 */
public class SplashActivity extends AppCompatActivity {
    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // --- Seed database once on first run ---
        new Thread(() -> {
            SharedPreferences prefs = getSharedPreferences("prg_prefs", MODE_PRIVATE);
            boolean seeded = prefs.getBoolean("seeded_once", false);
            if (!seeded) {
                try {
                    if (AppDatabase.get(this).restaurantDao().countSync() == 0) {
                        RestaurantRepository repo = new RestaurantRepository(this);
                        repo.addNew("Spice Villa", "123 King St", "4165550123",
                                "Indian classics, cozy space", "Indian,Dinner");
                        repo.addNew("Noodle Nook", "45 Queen Ave", "4165550456",
                                "Ramen & bowls", "Ramen,Casual");
                        repo.addNew("Green Fork", "9 Bay Rd", "4165550789",
                                "Vegan lunch spot", "Vegan,Lunch");
                    }
                    prefs.edit().putBoolean("seeded_once", true).apply();
                } catch (Exception ignored) {}
            }
        }).start();
        // --- End seed block ---

        // Delay and navigate to MainActivity
        binding.getRoot().postDelayed(() -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }, 1200);
    }
}
