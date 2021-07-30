package com.fabian_nico_uni.youarewhatyoueat;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MenuItem;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.widget.Toast;
import com.fabian_nico_uni.youarewhatyoueat.data.ProfileManager;
import com.fabian_nico_uni.youarewhatyoueat.ui.settings.SelectColorDialog;
import com.fabian_nico_uni.youarewhatyoueat.ui.settings.SettingsFragment;

public class SettingsActivity extends AppCompatActivity implements SelectColorDialog.SelectColorDialogListener {
    public static final String LOG_TAG = SettingsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getString(R.string.action_settings));

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings_container, new SettingsFragment())
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void applyColor(String color) {
        ProfileManager.getInstance(this).setColor(color);
    }
}
