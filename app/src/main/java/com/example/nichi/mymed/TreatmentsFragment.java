package com.example.nichi.mymed;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class TreatmentsFragment extends Fragment {


    public TreatmentsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_treatments, container, false);


        String[] treatments = {"Flu", "Headaches", "Bellyache", "Arthrosis", "Osteochondrosis"};
        ListView treatmentListView = (ListView) view.findViewById(R.id.treatmentsList);
        ArrayAdapter treatmentList = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, treatments);

            treatmentListView.setAdapter(treatmentList);

        // Inflate the layout for this fragment
        return view;
    }

}
