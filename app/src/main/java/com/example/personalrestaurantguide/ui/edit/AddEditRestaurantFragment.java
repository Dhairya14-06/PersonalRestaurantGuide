package com.example.personalrestaurantguide.ui.edit;

import android.os.Bundle; import android.text.TextUtils; import android.view.*; import android.widget.Toast;
import androidx.annotation.*; import androidx.fragment.app.Fragment; import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import com.example.personalrestaurantguide.data.RestaurantViewModel;
import com.example.personalrestaurantguide.databinding.FragmentAddEditRestaurantBinding;

public class AddEditRestaurantFragment extends Fragment {
    private FragmentAddEditRestaurantBinding binding;
    private RestaurantViewModel vm;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater i, @Nullable ViewGroup c, @Nullable Bundle s) {
        binding = FragmentAddEditRestaurantBinding.inflate(i, c, false);
        return binding.getRoot();
    }

    @Override public void onViewCreated(@NonNull View v, @Nullable Bundle s) {
        vm = new ViewModelProvider(requireActivity()).get(RestaurantViewModel.class);
        binding.btnSave.setOnClickListener(v1 -> {
            String name = binding.etName.getText().toString().trim();
            if (TextUtils.isEmpty(name)) {
                Toast.makeText(getContext(), "Please enter a name", Toast.LENGTH_SHORT).show();
                return;
            }
            vm.add(
                    name,
                    binding.etAddress.getText().toString().trim(),
                    binding.etPhone.getText().toString().trim(),
                    binding.etDescription.getText().toString().trim(),
                    binding.etTags.getText().toString().trim()
            );
            NavHostFragment.findNavController(this).navigateUp();
        });
    }
    @Override public void onDestroyView(){ super.onDestroyView(); binding=null; }
}
