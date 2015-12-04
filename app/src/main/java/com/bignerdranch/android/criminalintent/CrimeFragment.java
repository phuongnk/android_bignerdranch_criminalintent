package com.bignerdranch.android.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.UUID;

/**
 * Created by Owner on 11/19/2015.
 */
public class CrimeFragment extends Fragment {
    public static String LOG_TAG = "CriminalIntent";

    private Crime mCrime;
    private EditText mCrimeTitleField;
    private Button mCrimeDateButton;
    private CheckBox mCrimeSolvedCheckbox;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCrime = new Crime();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Log.d(LOG_TAG, "onCreateView begins............");
        // Inflating the view.
        // The 3rd parameter is false because we don’t want to add the fragment’s view
        // to the activity’s yet. That will be done in a different code section
        View v = inflater.inflate(R.layout.fragment_crime, container, false);

        Log.d(LOG_TAG, "onCreateView: wiring widgets............");
        // Wiring (obtaining the handles to) the widget objects
        // You do this by calling findViewById() on the fragment’s view object
        mCrimeTitleField = (EditText)v.findViewById(R.id.fragcrime_crime_title_edittext);
        mCrimeDateButton = (Button)v.findViewById(R.id.fragcrime_crime_date_button);
        mCrimeSolvedCheckbox = (CheckBox) v.findViewById(R.id.fragcrime_crime_solved_checkbox);

        Log.d(LOG_TAG, "onCreateView getting intent obj............");
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            UUID crimeId = (UUID) intent.getSerializableExtra(CrimeListFragment.CRIME_ID_KEY);
            if (crimeId != null) {
                mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
                if (mCrime == null) {
                    Log.d(LOG_TAG, "onCreateView: mCrime is null");
                }
            }
            else {
                Log.d(LOG_TAG, "onCreateView: crimeId is null");
            }
        }
        else {
            Log.d(LOG_TAG, "onCreateView: intent is null");
        }

        Log.d(LOG_TAG, "onCreateView: updating widgets............");
        // Manipulating the widget objects, such as setting labels text or adding listeners
        //java.text.DateFormat dateFormat = android.text.format.DateFormat.getLongDateFormat(getContext());
        //mCrimeDateButton.setText(dateFormat.format(mCrime.getDate()));
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM d, yyyy");
        mCrimeDateButton.setText(dateFormat.format(mCrime.getDate()));
        mCrimeDateButton.setEnabled(false);
        mCrimeTitleField.setText(mCrime.getTitle());
        mCrimeSolvedCheckbox.setChecked(mCrime.isSolved());
        
        mCrimeSolvedCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });

        mCrimeTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ...
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString()); // update the model (data) object
            }

            @Override
            public void afterTextChanged(Editable s) {
                // ...
            }
        });

        return v;
    }
 }
