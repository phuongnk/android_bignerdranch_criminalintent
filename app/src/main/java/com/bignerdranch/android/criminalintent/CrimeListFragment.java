package com.bignerdranch.android.criminalintent;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


/**
 * Created by Owner on 11/23/2015.
 */
public class CrimeListFragment extends Fragment
{
    public static String CRIME_ID_KEY = "com.bignerdranch.android.criminalintent.crime_id";

    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mCrimeAdapter;
    private Crime mClickedCrime;


    // The item ViewHolder for the RecyclerView
    // This ViewHolder is also it's itemView's OnClickListener
    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mCrimeTitleTextView;
        public TextView mCrimeDateTextView;
        public CheckBox mCrimeSolvedCheckbox;
        public Crime mCrime;

        public CrimeHolder(View pItemView)
        {
            super(pItemView);	//this will assign pItemView to the ViewHolder’s itemView field
            mCrimeTitleTextView = (TextView)pItemView.findViewById(R.id.list_item_crime_title_textview);
            mCrimeDateTextView = (TextView)pItemView.findViewById(R.id.list_item_crime_date_textview);
            mCrimeSolvedCheckbox = (CheckBox)pItemView.findViewById(R.id.list_item_crime_solved_checkbox);

            //add an OnClickListener to the itemView
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d(App.LOG_TAG, "onClick begins");


            //Toast.makeText(getActivity(), mCrimeTitleTextView.getText() + " pressed", Toast.LENGTH_SHORT).show();
            mClickedCrime = mCrime;
            //Intent intent = new Intent(getActivity(), CrimeActivity.class);
            //intent.putExtra(CRIME_ID_KEY, mCrime.getId());

            // creating a new Intent to start a CrimePagerActivity for the current crime
            Log.d(App.LOG_TAG, "CrimeHolder.onClick: getting an intent from CrimePagerActivity for crime ID " + mCrime.getId() + "...");
            Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getId());
            Log.d(App.LOG_TAG, "CrimeHolder.onClick: intent " + intent + " received. Starting activity.....");
            startActivity(intent);
        }
    }

    // The adapter for the RecyclerView
    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {
        private List<Crime> mCrimes; // the underlying data elements

        public CrimeAdapter(List<Crime> pCrimeList) {
            mCrimes = pCrimeList;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup pParentView, int viewType) {
            // inflate the item View using the predefined layout
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            //View itemView = layoutInflater.inflate(android.R.layout.simple_list_item_1, pParentView, false); // using android-provided layout
            View itemView = layoutInflater.inflate(R.layout.list_item_crime, pParentView, false); // using developer-defined layout
            // return the item ViewHolder to the caller (ie. the RecyclerView)
            return new CrimeHolder(itemView);
        }

        @Override
        public void onBindViewHolder(CrimeHolder pItemViewHolder, int pDataPosition) {
            // get the data element
            Crime crime = mCrimes.get(pDataPosition);
            pItemViewHolder.mCrime = crime;
            // update the item View components according to the data contents
            pItemViewHolder.mCrimeTitleTextView.setText(crime.getTitle());
            pItemViewHolder.mCrimeDateTextView.setText(crime.getDate().toString());
            pItemViewHolder.mCrimeSolvedCheckbox.setChecked(crime.isSolved());
            if (crime.isSolved()) {
                pItemViewHolder.mCrimeTitleTextView.setTextColor(Color.BLUE);
            }
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    } // end of adapter class




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //inflate the fragment layout as usual
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        //locate the RecyclerView within this layout
        mCrimeRecyclerView = (RecyclerView)view.findViewById(R.id.frag_crimelist_recycler_view);
        //determines how the item Views are laid out by specifying a layout manager
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //updateUI();
        int position = CrimeLab.get(getActivity()).getCrimes().indexOf(mClickedCrime);
        Log.d(App.LOG_TAG, "CrimeListFragment.onResume: data may have been changed in position: " + position);
        mCrimeAdapter.notifyItemChanged(position);
        Log.d(App.LOG_TAG, "CrimeListFragment.onResume: position " + position + " View widgets updated");
    }

    // set the RecyclerView's adapter if not done so
    // refresh the data otherwise
    private void updateUI() {
        // retrieve the data elements
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimeList = crimeLab.getCrimes();

        // create an adapter that works on this dataset
        if (mCrimeAdapter == null) {
            mCrimeAdapter = new CrimeAdapter(crimeList);
            // set the RecyclerView's adapter
            mCrimeRecyclerView.setAdapter(mCrimeAdapter);
        }
        else {
            mCrimeAdapter.notifyDataSetChanged();
        }
    }
}
