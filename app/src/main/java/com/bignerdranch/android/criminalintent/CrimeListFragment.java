package com.bignerdranch.android.criminalintent;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;


/**
 * Created by Owner on 11/23/2015.
 */
public class CrimeListFragment extends Fragment
{
    private RecyclerView mCrimeRecyclerView;

    private class CrimeHolder extends RecyclerView.ViewHolder {
        public TextView mCrimeTitleTextView;
        public TextView mCrimeDateTextView;
        public CheckBox mCrimeSolvedCheckbox;

        public CrimeHolder(View pItemView)
        {
            super(pItemView);	//this will assign pItemView to the ViewHolder’s itemView field
            mCrimeTitleTextView = (TextView)pItemView.findViewById(R.id.list_item_crime_title_textview);
            mCrimeDateTextView = (TextView)pItemView.findViewById(R.id.list_item_crime_date_textview);
            mCrimeSolvedCheckbox = (CheckBox)pItemView.findViewById(R.id.list_item_crime_solved_checkbox);
        }
    }

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

    private void updateUI() {
        // retrieve the data elements
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimeList = crimeLab.getCrimes();

        // create an adapter that works on this dataset
        CrimeAdapter crimeAdapter = new CrimeAdapter(crimeList);
        // set the RecyclerView's adapter
        mCrimeRecyclerView.setAdapter(crimeAdapter);
    }
}
