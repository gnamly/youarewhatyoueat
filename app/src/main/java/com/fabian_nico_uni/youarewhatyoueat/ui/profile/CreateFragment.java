package com.fabian_nico_uni.youarewhatyoueat.ui.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.fabian_nico_uni.youarewhatyoueat.MainActivity;
import com.fabian_nico_uni.youarewhatyoueat.R;
import com.fabian_nico_uni.youarewhatyoueat.data.ProfileManager;

public class CreateFragment extends Fragment {
    public static final String LOG_TAG = CreateFragment.class.getSimpleName();
    private CreateViewModel createViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        createViewModel =
                ViewModelProviders.of(this).get(CreateViewModel.class);
        View root = inflater.inflate(R.layout.fragment_create_profile, container, false);

        Button button = root.findViewById(R.id.create_profile_form_submit);
        final EditText nameInput = root.findViewById(R.id.create_profile_input_name);
        final EditText nickInput = root.findViewById(R.id.create_profile_input_nick);
        final EditText ageInput = root.findViewById(R.id.create_profile_input_age);
        final EditText heightInput = root.findViewById(R.id.create_profile_input_height);
        final EditText weightInput = root.findViewById(R.id.create_profile_input_weight);
        View.OnClickListener onButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameInput.getText().toString();
                String nick = nickInput.getText().toString();
                String age = ageInput.getText().toString();
                int height = Integer.parseInt(heightInput.getText().toString());
                int weight = Integer.parseInt(weightInput.getText().toString());
                boolean result = ProfileManager.getInstance(getContext()).createProfile(name, nick, age, height, weight);
                Toast toas = Toast.makeText(getContext(), "Profile created with "+result, Toast.LENGTH_LONG);
                getActivity().onBackPressed();
            }
        };

        button.setOnClickListener(onButtonClickListener);

        return root;
    }
}
