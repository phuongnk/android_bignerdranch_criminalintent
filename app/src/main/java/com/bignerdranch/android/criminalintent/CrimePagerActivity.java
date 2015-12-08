package com.bignerdranch.android.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import java.util.List;
import java.util.UUID;

public class CrimePagerActivity extends FragmentActivity {
    public static final String EXTRA_CRIME_ID = "com.bignerdranch.android.criminalintent.crime_id";

    private ViewPager mViewPager;
    private List<Crime> mCrimes;

    // Returns an intent that this activity needs when it's started. This method is to be called from the parent activity
    public static Intent newIntent(Context pPackageContext, UUID pCrimeId) {
        Log.d(App.LOG_TAG, "CrimePagerActivity.newIntent: creating an intent for crime ID " + pCrimeId + "...");
        Intent intent = new Intent(pPackageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, pCrimeId);
        Log.d(App.LOG_TAG, "CrimePagerActivity.newIntent: returning intent " + intent + "...");
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);
        mViewPager = (ViewPager)findViewById(R.id.activity_crime_pager_view_pager);

        mCrimes = CrimeLab.get(this).getCrimes();

        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            // create a fragment for the data element at the specified position
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            // return the number of data elements
            public int getCount() {
                return mCrimes.size();
            }
        });

        // get the crime ID that was passed from the calling activity
        UUID crimeId = (UUID)getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        Log.d(App.LOG_TAG, "CrimePagerActivity.onCreate: intent's crimeID: " + crimeId);
        if (crimeId != null) {
            for (int i = 0; i < mCrimes.size(); i++) {
                if (mCrimes.get(i).getId().equals(crimeId)) {
                    Log.d(App.LOG_TAG, "CrimePagerActivity.onCreate: matching crime found: " + mCrimes.get(i).getTitle());
                    mViewPager.setCurrentItem(i);
                    break;
                }
            }
        }
    }
}

