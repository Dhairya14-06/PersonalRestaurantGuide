package com.example.personalrestaurantguide.ui.detail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.*;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.personalrestaurantguide.data.RestaurantEntity;
import com.example.personalrestaurantguide.data.RestaurantViewModel;
import com.example.personalrestaurantguide.databinding.FragmentRestaurantDetailBinding;
import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;

public class RestaurantDetailFragment extends Fragment {
    private FragmentRestaurantDetailBinding binding;
    private RestaurantViewModel vm;
    private String id = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRestaurantDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        vm = new ViewModelProvider(requireActivity()).get(RestaurantViewModel.class);

        // Pull id from arguments
        Bundle args = getArguments();
        if (args != null) id = args.getString("id", "");

        // Observe the entity by id (live updates)
        vm.getById(id).observe(getViewLifecycleOwner(), entity -> {
            if (entity == null) return;
            bindUi(entity);
        });

        // Submit rating
        binding.btnRateSubmit.setOnClickListener(v -> {
            int newRating = Math.round(binding.ratingBar.getRating());
            if (newRating < 1 || newRating > 5) {
                Snackbar.make(binding.getRoot(), "Please select a rating 1–5", Snackbar.LENGTH_SHORT).show();
                return;
            }
            vm.rate(id, newRating);
            Snackbar.make(binding.getRoot(), "Thanks! Rating saved.", Snackbar.LENGTH_SHORT).show();
        });
    }

    private void bindUi(RestaurantEntity e) {
        binding.tvName.setText(e.name);
        binding.tvAddress.setText(e.address == null ? "" : e.address);
        binding.tvPhone.setText(e.phone == null ? "" : e.phone);

        // Tags
        binding.chipGroup.removeAllViews();
        if (e.tagsCsv != null && !e.tagsCsv.trim().isEmpty()) {
            for (String raw : e.tagsCsv.split(",")) {
                String t = raw.trim();
                if (t.isEmpty()) continue;
                Chip chip = new Chip(requireContext());
                chip.setText(t);
                chip.setCheckable(false);
                binding.chipGroup.addView(chip);
            }
        }

        // Rating (reflect persisted value)
        float stars = e.rating <= 0 ? 0f : Math.min(e.rating, 5);
        binding.ratingBar.setRating(stars);

        // Actions
        binding.btnShare.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_TEXT, e.name + (e.address == null || e.address.isEmpty() ? "" : " — " + e.address));
            startActivity(Intent.createChooser(i, "Share restaurant"));
        });

        View.OnClickListener maps = v -> {
            String q = (e.address != null && !e.address.isEmpty()) ? e.address : e.name;
            Uri u = Uri.parse("geo:0,0?q=" + Uri.encode(q));
            startActivity(new Intent(Intent.ACTION_VIEW, u));
        };
        binding.btnMap.setOnClickListener(maps);
        binding.btnDirections.setOnClickListener(maps);

        binding.tvPhone.setOnClickListener(v -> {
            if (e.phone != null && !e.phone.trim().isEmpty()) {
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + e.phone)));
            } else {
                Toast.makeText(getContext(), "Phone not available", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() { super.onDestroyView(); binding = null; }
}
