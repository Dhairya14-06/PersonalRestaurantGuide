package com.example.personalrestaurantguide.ui.list;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personalrestaurantguide.R;
import com.example.personalrestaurantguide.data.RestaurantEntity;
import com.example.personalrestaurantguide.data.RestaurantViewModel;
import com.example.personalrestaurantguide.databinding.FragmentRestaurantListBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class RestaurantListFragment extends Fragment {
    private FragmentRestaurantListBinding binding;
    private RestaurantAdapter adapter;
    private RestaurantViewModel vm;

    // Optional: tiny debounce for search so we don’t requery on every keystroke
    private final Handler searchHandler = new Handler(Looper.getMainLooper());
    private Runnable pendingSearch;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRestaurantListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Click: pass ONLY the ID; Details will load fresh via ViewModel.getById(id)
        adapter = new RestaurantAdapter(new ArrayList<>(), (RestaurantEntity e) -> {
            Bundle args = new Bundle();
            args.putString("id", e.id);
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_list_to_detail, args);
        });
        binding.recyclerView.setAdapter(adapter);

        // ViewModel + initial list observe
        vm = new ViewModelProvider(requireActivity()).get(RestaurantViewModel.class);
        vm.getRestaurants().observe(getViewLifecycleOwner(), list -> adapter.replaceData(list));

        // Search: update query and re-attach observer safely (no leaks)
        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int st, int c, int a) {}
            @Override public void onTextChanged(CharSequence s, int st, int b, int c) {}

            @Override public void afterTextChanged(Editable s) {
                // Debounce ~200ms
                if (pendingSearch != null) searchHandler.removeCallbacks(pendingSearch);
                pendingSearch = () -> {
                    vm.search(s.toString());
                    // swap observer to the latest LiveData returned by vm.getLiveList()
                    vm.getLiveList().removeObservers(getViewLifecycleOwner());
                    vm.getLiveList().observe(getViewLifecycleOwner(), list -> adapter.replaceData(list));
                };
                searchHandler.postDelayed(pendingSearch, 200);
            }
        });

        // Swipe-to-delete
        ItemTouchHelper.SimpleCallback swipe = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override public boolean onMove(RecyclerView rv, RecyclerView.ViewHolder vh, RecyclerView.ViewHolder t){ return false; }
            @Override public void onSwiped(RecyclerView.ViewHolder vh, int direction) {
                int pos = vh.getAdapterPosition();
                RestaurantEntity removed = adapter.getItem(pos);
                adapter.removeAt(pos);
                vm.deleteById(removed.id);
                Snackbar.make(binding.getRoot(), "Deleted", Snackbar.LENGTH_SHORT).show();
            }
        };
        new ItemTouchHelper(swipe).attachToRecyclerView(binding.recyclerView);

        // Add button
        binding.fabAdd.setOnClickListener(v ->
                NavHostFragment.findNavController(this)
                        .navigate(R.id.action_list_to_addEdit)
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Clean up debounce runnable
        if (pendingSearch != null) searchHandler.removeCallbacks(pendingSearch);
        binding = null;
    }
}
