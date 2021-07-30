package com.fabian_nico_uni.youarewhatyoueat.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.fabian_nico_uni.youarewhatyoueat.MainActivity;
import com.fabian_nico_uni.youarewhatyoueat.R;

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
        Log.d(LOG_TAG, "Found lastProfile with "+current.nickname);
        fireCurrentProfileUpdate();
    }

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

    public void addCurrentProfileUpdateListener(CurrentProfileUpdateEvent listener){
        currentUpdateListeners.add(listener);
    }

    public boolean createProfile(String name, String nick, String birth, int height, int weight, boolean male){
        long id = ProfileDBHelper.createProfile(ctx, name, nick, birth, height, weight, male);
        if(id == -1) return false;
        current = ProfileDBHelper.getById(id, ctx);
        fireCurrentProfileUpdate(true);
        return true;
    }

    public void refreshCurrent() {
        current = ProfileDBHelper.getById(current.id, ctx);
        fireCurrentProfileUpdate();
    }

    public void deleteCurrent() {
        ProfileDBHelper.deleteProfile(current.id);
        current = ProfileDBHelper.getLast(ctx);
        fireCurrentProfileUpdate();
    }

    public List<SimpleProfile> getAllSimple(){
        return ProfileDBHelper.getAll(ctx);
    }

    public void loadProfile(long id) {
        current = ProfileDBHelper.getById(id, ctx);
        fireCurrentProfileUpdate();
    }

    public void setColor(String color) {
        ProfileDBHelper.updateField("color", color, current.id);
        loadProfile(current.id);
    }

    private void fireCurrentProfileUpdate() {
        fireCurrentProfileUpdate(false);
    }

    private void fireCurrentProfileUpdate(boolean newProfile){
        for(CurrentProfileUpdateEvent listener : currentUpdateListeners){
            listener.onProfileUpdated(current, newProfile);
        }
    }
}
