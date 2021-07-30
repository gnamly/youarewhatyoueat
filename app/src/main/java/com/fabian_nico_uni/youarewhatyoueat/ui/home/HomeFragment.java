package com.fabian_nico_uni.youarewhatyoueat.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        nickname = root.findViewById(R.id.home_nick);
        cals = root.findViewById(R.id.home_cals);
        maxCals = root.findViewById(R.id.home_daylie);
        rootLayout = (ConstraintLayout)root.findViewById(R.id.home_root);

        FloatingActionButton fab = root.findViewById(R.id.home_fab);
        View.OnClickListener onButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        };

        fab.setOnClickListener(onButtonClickListener);

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
        rootLayout.setBackgroundColor(current.color);
    }

    void openDialog() {
        AddCalsDialog addCalcDialog = new AddCalsDialog();
        addCalcDialog.show(getChildFragmentManager(), "add calc dialog");
    }
}