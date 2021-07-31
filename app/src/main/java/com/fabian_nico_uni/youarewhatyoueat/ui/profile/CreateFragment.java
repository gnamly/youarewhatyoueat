package com.fabian_nico_uni.youarewhatyoueat.ui.profile;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import java.util.Calendar;

public class CreateFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    public static final String LOG_TAG = CreateFragment.class.getSimpleName();
    private CreateViewModel createViewModel;

    Spinner genderSpinner;
    boolean male = true;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        createViewModel =
                ViewModelProviders.of(this).get(CreateViewModel.class);
        View root = inflater.inflate(R.layout.fragment_create_profile, container, false);

        Button button = root.findViewById(R.id.create_profile_form_submit);
        final EditText nameInput = root.findViewById(R.id.create_profile_input_name);
        final EditText nickInput = root.findViewById(R.id.create_profile_input_nick);
        final TextView ageInput = root.findViewById(R.id.create_profile_input_age);
        DatePickerDialog.OnDateSetListener ageDateListener;
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        ageDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month += 1;
                String date = (dayOfMonth<10 ? "0" : "")+dayOfMonth+"."+(month<10 ? "0" : "")+month+"."+year;
                ageInput.setText(date);
            }
        };

        ageInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(),
                        0,
                        ageDateListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                datePickerDialog.show();
            }
        });

        final EditText heightInput = root.findViewById(R.id.create_profile_input_height);
        final EditText weightInput = root.findViewById(R.id.create_profile_input_weight);
        genderSpinner = root.findViewById(R.id.create_profile_input_gender);

        //Setup the spinner for the gender
        genderSpinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(getContext(), R.array.genders, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);

        //the button for submitting the create form
        View.OnClickListener onButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameInput.getText().toString();
                String nick = nickInput.getText().toString();
                String age = ageInput.getText().toString();
                int height = 0;
                if(!heightInput.getText().toString().equals("")) height = Integer.parseInt(heightInput.getText().toString());
                int weight = 0;
                if(!weightInput.getText().toString().equals("")) weight = Integer.parseInt(weightInput.getText().toString());
                if(height == 0 || weight == 0 || age.equals("") || name.equals("") || nick.equals("")){
                    Toast toast = Toast.makeText(getContext(), "Alle Felder müssen ausgefüllt werden", Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }
                Log.d(LOG_TAG, "Creating Profile with name: "+name+" nick: "+nick+" age: "+age+" height: "+height+" male? "+male);
                boolean result = ProfileManager.getInstance(getContext()).createProfile(name, nick, age, height, weight, male);
                Toast toast = Toast.makeText(getContext(), "Neues Profil angelegt", Toast.LENGTH_SHORT);
                toast.show();
                getActivity().onBackPressed();
            }
        };

        button.setOnClickListener(onButtonClickListener);

        return root;
    }

    //Gender select Spinner is used
    @Override
    public void onItemSelected(AdapterView<?> parent, View arg1, int position, long id) {
        male = ((String) parent.getItemAtPosition(position)).equals("Männlich");
        Log.d(LOG_TAG, "spinner selected "+parent.getItemAtPosition(position)+" set male to "+male);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
