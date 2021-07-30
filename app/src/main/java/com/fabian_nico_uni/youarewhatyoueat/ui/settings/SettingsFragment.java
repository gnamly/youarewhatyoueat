package com.fabian_nico_uni.youarewhatyoueat.ui.settings;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AlertDialog;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import com.fabian_nico_uni.youarewhatyoueat.R;
import com.fabian_nico_uni.youarewhatyoueat.SettingsActivity;
import com.fabian_nico_uni.youarewhatyoueat.data.ProfileManager;

public class SettingsFragment extends PreferenceFragmentCompat {
    public static final String LOG_TAG = SettingsFragment.class.getSimpleName();

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        //Setup delete button with a warning dialog
        Preference deletePref = (Preference) findPreference("delete_profile");
        deletePref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                Log.d(LOG_TAG, "Clicked on Delete Profile");
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                ProfileManager.getInstance(getContext()).deleteCurrent();
                                getActivity().onBackPressed();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Bist du sicher?").setPositiveButton("Ja", dialogClickListener)
                        .setNegativeButton("Nein", dialogClickListener).show();

                return true;
            }
        });

        //color button with dialog and spinner with predefined colors
        Preference colorPref = (Preference) findPreference("select_color");
        colorPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Log.d(LOG_TAG, "Clicked on Select Color");
                OpenDialog();
                return true;
            }
        });

        //add weight button
        Preference weightPref = (Preference) findPreference("add_weight");
        weightPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                OpenWeightDialog();
                return true;
            }
        });
    }

    void OpenDialog() {
        SelectColorDialog dialog = new SelectColorDialog();
        dialog.show(getChildFragmentManager(), "select color dialog");
    }

    void OpenWeightDialog() {
        AddWeightDialog dialog = new AddWeightDialog();
        dialog.show(getChildFragmentManager(), "add weight dialog");
    }
}
