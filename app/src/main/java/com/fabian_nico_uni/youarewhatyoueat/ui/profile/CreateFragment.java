package com.fabian_nico_uni.youarewhatyoueat.ui.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.fabian_nico_uni.youarewhatyoueat.MainActivity;
import com.fabian_nico_uni.youarewhatyoueat.R;
import com.fabian_nico_uni.youarewhatyoueat.data.ProfileManager;

import java.lang.reflect.Array;

public class CreateFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    public static final String LOG_TAG = CreateFragment.class.getSimpleName();
    private CreateViewModel createViewModel;

    Spinner genderSpinner;
    boolean male;

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
        genderSpinner = root.findViewById(R.id.create_profile_input_gender);

        genderSpinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        String[] gender = {"Männlich", "Weiblich"};
        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(getContext(), R.array.genders, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);

        View.OnClickListener onButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameInput.getText().toString();
                String nick = nickInput.getText().toString();
                String age = ageInput.getText().toString();
                int height = Integer.parseInt(heightInput.getText().toString());
                int weight = Integer.parseInt(weightInput.getText().toString());
                boolean result = ProfileManager.getInstance(getContext()).createProfile(name, nick, age, height, weight, male);
                Toast toas = Toast.makeText(getContext(), "Profile created with "+result, Toast.LENGTH_LONG);
                getActivity().onBackPressed();
            }
        };

        button.setOnClickListener(onButtonClickListener);

        return root;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View arg1, int position, long id) {
        Log.d(LOG_TAG, "spinner selected "+parent.getItemAtPosition(position));
        male = parent.getItemAtPosition(position) == "Männlich";
        genderSpinner.clearFocus();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
