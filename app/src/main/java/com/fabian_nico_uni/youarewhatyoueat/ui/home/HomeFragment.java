package com.fabian_nico_uni.youarewhatyoueat.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.fabian_nico_uni.youarewhatyoueat.R;
import com.fabian_nico_uni.youarewhatyoueat.data.CaloriesDBHelper;
import com.fabian_nico_uni.youarewhatyoueat.data.CurrentProfileUpdateEvent;
import com.fabian_nico_uni.youarewhatyoueat.data.Profile;
import com.fabian_nico_uni.youarewhatyoueat.data.ProfileManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeFragment extends Fragment implements CurrentProfileUpdateEvent {
    public static final String LOG_TAG = HomeFragment.class.getSimpleName();

    private HomeViewModel homeViewModel;

    TextView nickname;
    TextView cals;
    TextView maxCals;
    ConstraintLayout rootLayout;
    ImageView subDrink;
    ImageView addDrink;
    TextView drink;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        nickname = root.findViewById(R.id.home_nick);
        cals = root.findViewById(R.id.home_cals);
        maxCals = root.findViewById(R.id.home_daylie);
        rootLayout = (ConstraintLayout)root.findViewById(R.id.home_root);

        subDrink = root.findViewById(R.id.home_remove_drink);
        addDrink = root.findViewById(R.id.home_add_drink);
        drink = root.findViewById(R.id.home_drink);

        FloatingActionButton fab = root.findViewById(R.id.home_fab);
        View.OnClickListener onButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        };

        fab.setOnClickListener(onButtonClickListener);

        View.OnClickListener onSubClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileManager.getInstance(getContext()).removeDrink();
            }
        };
        subDrink.setOnClickListener(onSubClickListener);
        View.OnClickListener onAddClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileManager.getInstance(getContext()).addDrink();
            }
        };
        addDrink.setOnClickListener(onAddClickListener);

        ProfileManager.getInstance(getContext()).addCurrentProfileUpdateListener(this);
        onProfileUpdated(ProfileManager.getInstance(getContext()).getCurrent(), false);
        return root;
    }

    @Override
    public void onProfileUpdated(Profile current, boolean newProfile) {
        if(current == null) return;
        nickname.setText("@"+current.nickname);
        cals.setText(current.caloriesToday+" kcal");
        maxCals.setText(current.maxDaylie+" kcal");
        drink.setText(Integer.toString(current.drink));
        rootLayout.setBackgroundColor(current.color);
    }

    void openDialog() {
        AddCalsDialog addCalcDialog = new AddCalsDialog();
        addCalcDialog.show(getChildFragmentManager(), "add calc dialog");
    }
}