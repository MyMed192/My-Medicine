package com.example.nichi.mymed;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TreatmentsFragment extends Fragment {


    TreatmentAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    Activity mActivity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_treatments, container, false);


        // get the listview
        expListView = (ExpandableListView) view.findViewById(R.id.lvExp2);

        // preparing list data
        prepareListData();

        listAdapter = new TreatmentAdapter(getActivity(), listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                //Toast.makeText(getActivity(),
                //        listDataHeader.get(groupPosition) + " Expanded",
                //        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                //Toast.makeText(getActivity(),
                //        listDataHeader.get(groupPosition) + " Collapsed",
                //        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                //Toast.makeText(
                //        getActivity(),
                //        listDataHeader.get(groupPosition)
                //                + " : "
                //                + listDataChild.get(
                //                listDataHeader.get(groupPosition)).get(
                //                childPosition), Toast.LENGTH_SHORT)
                //        .show();
                return false;
            }
        });

        return view;
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Raceala");
        listDataHeader.add("Gripa");
        listDataHeader.add("Siphilis");

        // Adding child data
        List<String> pastila1 = new ArrayList<String>();
        pastila1.add("25 september 2018");


        List<String> pastila2 = new ArrayList<String>();
        pastila2.add("Mo zarazit Ina si stau de mai mult de o luna racit");


        List<String> pastila3 = new ArrayList<String>();
        pastila3.add("A full pack(30pills)");


        listDataChild.put(listDataHeader.get(0), pastila1); // Header, Child data
        listDataChild.put(listDataHeader.get(1), pastila2);
        listDataChild.put(listDataHeader.get(2), pastila3);
    }

}
