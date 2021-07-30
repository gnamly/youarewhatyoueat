package com.fabian_nico_uni.youarewhatyoueat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.*;
import androidx.annotation.NonNull;
import com.fabian_nico_uni.youarewhatyoueat.data.*;
import com.fabian_nico_uni.youarewhatyoueat.ui.home.AddCalsDialog;
import com.fabian_nico_uni.youarewhatyoueat.ui.profile.CreateFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CurrentProfileUpdateEvent, AddCalsDialog.AddCalsDialogListener {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    private AppBarConfiguration mAppBarConfiguration;

    private ProfileManager profileManager;
    public ProfileManager getProfileManager() {return profileManager;};

    private TextView profileSubheaderNavView;
    private ImageView profileImageNavView;

    private Spinner spinner;
    ArrayAdapter<SimpleProfile> adapter;

    List<SimpleProfile> profileList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        profileManager = ProfileManager.getInstance(this);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, /*R.id.nav_recipes,*/ R.id.nav_overview, R.id.nav_create_profile)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        View navHeaderView = navigationView.getHeaderView(0);
        profileSubheaderNavView = navHeaderView.findViewById(R.id.nav_profile_subheader);
        profileImageNavView = navHeaderView.findViewById(R.id.nav_profile_image);

        //Setup profile spinner in the nav menu with all simple profile models
        spinner = navHeaderView.findViewById(R.id.nav_profile_header_spinner);
        profileList = ProfileManager.getInstance(this).getAllSimple();

        adapter = new ArrayAdapter<SimpleProfile>(this,
                android.R.layout.simple_spinner_item, profileList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SimpleProfile profile = (SimpleProfile) parent.getSelectedItem();
                onSelectedProfile(profile);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //We do nothing
            }
        });

        //register for profilemanager's update event
        profileManager.addCurrentProfileUpdateListener(this);
        onProfileUpdated(profileManager.getCurrent(), false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            case R.id.action_create_profile:
                startActivity(new Intent(this, CreateProfileActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onProfileUpdated(Profile current, boolean newProfile) {
        //if there is no profile the user must create a new one
        if(current == null) {
            Log.w(LOG_TAG, "Profile was updated, main activity has received a null object");
            startActivity(new Intent(this, CreateProfileActivity.class));
            return;
        }
        Log.d(LOG_TAG, "Profile was updated, main activity has new profile info.");
        //safe the current profile for persistend loading
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(getString(R.string.pref_last_profile_key), current.id);
        editor.apply();
        profileSubheaderNavView.setText(current.name);
        if(newProfile) profileList.add(current.getSimple());
        setupSpinner();
    }

    @Override
    public void applyCals(int cal) {
        Profile current = ProfileManager.getInstance(this).getCurrent();
        CaloriesDBHelper.addCalories(current.id, cal);
        ProfileManager.getInstance(this).refreshCurrent();
    }

    public void getSelectedProfile(View v) {
        SimpleProfile profile =  (SimpleProfile) spinner.getSelectedItem();
        onSelectedProfile(profile);
    }

    /**
     * updates the spinner with new profiles and sets to the current profile
     */
    private void setupSpinner() {
        adapter.notifyDataSetChanged();
        SimpleProfile currentSimple = profileList.get(0);
        Profile current = ProfileManager.getInstance(this).getCurrent();
        for(SimpleProfile profile : profileList){
            if(profile.id == current.id){
                currentSimple = profile;
            }
        }
        Log.d(LOG_TAG, "setup Spinner with id: "+currentSimple.id+ " position: "+adapter.getPosition(currentSimple));
        spinner.setSelection(adapter.getPosition(currentSimple));
    }

    private void onSelectedProfile(SimpleProfile profile) {
        Log.d(LOG_TAG, "onSelectedProfile");
        Profile current = ProfileManager.getInstance(this).getCurrent();
        if(current.id != profile.id)
            ProfileManager.getInstance(this).loadProfile(profile.id);
    }
}