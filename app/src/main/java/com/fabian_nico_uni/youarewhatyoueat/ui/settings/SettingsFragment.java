package com.fabian_nico_uni.youarewhatyoueat.ui.settings;

import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;
import com.fabian_nico_uni.youarewhatyoueat.R;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }
}
