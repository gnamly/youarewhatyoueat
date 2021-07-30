package com.fabian_nico_uni.youarewhatyoueat.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.fabian_nico_uni.youarewhatyoueat.MainActivity;
import com.fabian_nico_uni.youarewhatyoueat.R;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProfileManager {
    public static final String LOG_TAG = ProfileManager.class.getSimpleName();

    private static ProfileManager instance;
    private static Context ctx;

    private static Profile current;

    private List<CurrentProfileUpdateEvent> currentUpdateListeners;

    private ProfileManager(Context context) {
        Log.d(LOG_TAG, "Creating new ProfileManager");
        currentUpdateListeners = new ArrayList<>();

        SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        long lastProfile = prefs.getLong(context.getString(R.string.pref_last_profile_key), 0);
        //Search for Profile
        Log.d(LOG_TAG, "Searching for last profile with id "+lastProfile);
        current = ProfileDBHelper.getById(lastProfile, context);
        if(current != null) Log.d(LOG_TAG, "Found lastProfile with "+current.nickname);
        else Log.d(LOG_TAG, "Can't find lastProfile");
        fireCurrentProfileUpdate();
    }

    /**
     * returns the singleton
     */
    public static ProfileManager getInstance(Context context) {
        if(instance == null) {
            instance = new ProfileManager(context);
            ctx = context;
        }
        return instance;
    }

    public Profile getCurrent() {
        return current;
    }

    /**
     * Registers a listener to the list
     */
    public void addCurrentProfileUpdateListener(CurrentProfileUpdateEvent listener){
        currentUpdateListeners.add(listener);
    }

    /**
     * Safe a new profile into the DB and set it as current
     * @param weight A initial weight
     * @return returns success
     */
    public boolean createProfile(String name, String nick, String birth, int height, int weight, boolean male){
        long id = ProfileDBHelper.createProfile(ctx, name, nick, birth, height, weight, male);
        if(id == -1) return false;
        current = ProfileDBHelper.getById(id, ctx);
        fireCurrentProfileUpdate(true);
        return true;
    }

    /**
     * Loads the current profile from the DB
     */
    public void refreshCurrent() {
        current = ProfileDBHelper.getById(current.id, ctx);
        fireCurrentProfileUpdate();
    }

    /**
     * Removes a profile from the DB
     */
    public void deleteCurrent() {
        ProfileDBHelper.deleteProfile(current.id);
        current = ProfileDBHelper.getLast(ctx);
        fireCurrentProfileUpdate();
    }

    /**
     * A List of all profiles in a simple form without extra infos
     */
    public List<SimpleProfile> getAllSimple(){
        return ProfileDBHelper.getAll(ctx);
    }

    /**
     * loads a specific profile as the current one
     * @param id
     */
    public void loadProfile(long id) {
        current = ProfileDBHelper.getById(id, ctx);
        fireCurrentProfileUpdate();
    }

    /**
     * Sets the color for a profile
     * @param color as rgb hex value
     */
    public void setColor(String color) {
        ProfileDBHelper.updateField(ProfileDBHelper.COLUMN_COLOR, color, current.id);
        loadProfile(current.id);
    }

    /**
     * Subtract 1 from the drinks limited to 0
     */
    public void removeDrink() {
        int newDrink = current.drink-1;
        if(newDrink < 0) newDrink = 0;
        ProfileDBHelper.updateField(ProfileDBHelper.COLUMN_DRINK, newDrink, current.id);
        ProfileDBHelper.updateField(ProfileDBHelper.COLUMN_DRINK_TS, LocalDate.now().toString(), current.id);
        loadProfile(current.id);
    }

    /**
     * Add 1 to the drinks
     */
    public void addDrink() {
        int newDrink = current.drink+1;
        ProfileDBHelper.updateField(ProfileDBHelper.COLUMN_DRINK, newDrink, current.id);
        ProfileDBHelper.updateField(ProfileDBHelper.COLUMN_DRINK_TS, LocalDate.now().toString(), current.id);
        loadProfile(current.id);
    }

    public void addWeight(int weight) {
        WeightDBHelper.addWeight(current.id, weight);
        loadProfile(current.id);
    }

    /**
     * Overload of the fire Event with auto newProfile value
     */
    private void fireCurrentProfileUpdate() {
        fireCurrentProfileUpdate(false);
    }

    /**
     * calls the onProfileUpdated interface on all registered listeners
     * @param newProfile
     */
    private void fireCurrentProfileUpdate(boolean newProfile){
        for(CurrentProfileUpdateEvent listener : currentUpdateListeners){
            listener.onProfileUpdated(current, newProfile);
        }
    }
}
