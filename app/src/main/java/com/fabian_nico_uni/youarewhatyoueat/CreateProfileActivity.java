package com.fabian_nico_uni.youarewhatyoueat;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.fabian_nico_uni.youarewhatyoueat.ui.profile.CreateFragment;
import com.fabian_nico_uni.youarewhatyoueat.ui.settings.SettingsFragment;

public class CreateProfileActivity extends AppCompatActivity {
    public static final String LOG_TAG = CreateProfileActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_profile);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getString(R.string.action_create_profile));

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.create_profile_container, new CreateFragment())
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
}
