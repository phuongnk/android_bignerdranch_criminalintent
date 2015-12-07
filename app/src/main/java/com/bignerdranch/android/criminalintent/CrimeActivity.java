package com.bignerdranch.android.criminalintent;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.util.UUID;

public class CrimeActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        CrimeFragment crimeFragment = null;

        Log.d(App.LOG_TAG, "CrimeActivity.createFragment: getting intent obj............");
        Intent intent = getIntent();
        if (intent != null) {
            UUID crimeId = (UUID) intent.getSerializableExtra(CrimeListFragment.CRIME_ID_KEY);
            if (crimeId != null) {
                crimeFragment = CrimeFragment.newInstance(crimeId);
            }
            else {
                Log.d(App.LOG_TAG, "CrimeActivity.createFragment: crimeId is null");
            }
        }
        else {
            Log.d(App.LOG_TAG, "CrimeActivity.createFragment: intent is null");
        }

        return crimeFragment;

    }
}
