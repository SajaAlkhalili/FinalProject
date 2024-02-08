package edu.birzeit.students.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings); // Consider renaming the layout to activity_settings

        LinearLayout accountButton = findViewById(R.id.accountButton);
        String email = getIntent().getStringExtra("email");
        LinearLayout aboutButton = findViewById(R.id.aboutButton);

        SwitchCompat darkModeSwitch = findViewById(R.id.switch1);
        darkModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });

        accountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Account Activity
                 Intent intent = new Intent(SettingsActivity.this, AccountActivity.class);
                intent.putExtra("email", email);
                 startActivity(intent);
            }
        });

        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to About Activity
                 Intent intent = new Intent(SettingsActivity.this, AboutActivity.class);
                 startActivity(intent);
            }
        });
    }
}
