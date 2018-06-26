package com.example.kartikmishra.popularmovies;

import android.content.Intent;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import android.os.Bundle;

import android.widget.Button;
import android.widget.ImageView;



public class SettingsActivity  extends PreferenceActivity implements Preference.OnPreferenceChangeListener {

    private Button popular,topRated;
    private ImageView homeIv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);
        bindPrefernenceSummaryToValue(findPreference(getString(R.string.pref_sorting_criteria_key)));




        //homeIv = findViewById(R.id.home_iv);
//        homeIv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(SettingsActivity.this,MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });



      
    }

    private void bindPrefernenceSummaryToValue(Preference preference) {
        preference.setOnPreferenceChangeListener(this);

        // Trigger the listener immediately with the preference's
        // current value.
        onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));


    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {



               String stringValue = newValue.toString();

        if (preference instanceof ListPreference) {
            // For list preferences, look up the correct display value in
            // the preference's 'entries' list (since they have separate labels/values).
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(stringValue);
            if (prefIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        } else {
            // For other preferences, set the summary to the value's simple string representation.
            preference.setSummary(stringValue);

        }



        return true;
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}

