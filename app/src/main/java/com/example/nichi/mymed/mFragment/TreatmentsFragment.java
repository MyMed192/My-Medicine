package com.example.nichi.mymed.mFragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nichi.mymed.mData.DataBaseAdapter;
import com.example.nichi.mymed.mData.model.Treatments;
import com.example.nichi.mymed.R;
import com.example.nichi.mymed.mAdapter.TreatmentAdapter;
import com.example.nichi.mymed.mExpandables.TreatmentChild;
import com.example.nichi.mymed.mExpandables.TreatmentHeader;
import com.example.nichi.mymed.mAdapter.SpinnerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TreatmentsFragment extends Fragment {


    TreatmentAdapter listAdapter;
    ExpandableListView expListView;
    List<TreatmentHeader> listDataHeader = new ArrayList<>();
    HashMap<String, List<TreatmentChild>> listDataChild = new HashMap<>();
    AlertDialog.Builder mBuilder;
    View mView;
    AlertDialog dialog;
    private EditText treatmentTitle;
    private EditText treatmentStartDate;
    private EditText treatmentEndDate;
    private EditText treatmentDescription;
    private Spinner treatmentState;
    DataBaseAdapter db;
    private static final String DATABASE_NAME = "MyMed_db";


    Activity mActivity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_treatments, container, false);

        db = new DataBaseAdapter(getActivity().getApplicationContext());
        //treatmentState = (Spinner) mView.findViewById(R.id.treatmentState);
        //db.deleteTreatment(1);

        // get the listview
        expListView = (ExpandableListView) view.findViewById(R.id.lvExp2);

        // preparing list data
        //prepareListData();
        expListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String name = listDataHeader.get(position).getTitle();
                db.deleteTreatment(name);
                listDataHeader.remove(position);
                expListView.invalidateViews();
                return false;
            }
        });

        listAdapter = new TreatmentAdapter(getActivity(), listDataHeader, listDataChild);

        listAdapter.notifyDataSetChanged();

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
        expListView = (ExpandableListView) view.findViewById(R.id.lvExp2);
        viewData();
        return view;
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<TreatmentHeader>();
        listDataChild = new HashMap<String, List<TreatmentChild>>();


        listDataHeader.add(new TreatmentHeader("Raceala", "Done"));
        listDataHeader.add(new TreatmentHeader("Gripa", "Done"));
        listDataHeader.add(new TreatmentHeader("Angina", "In Progress"));

        // Adding child data
        List<TreatmentChild> pastila1 = new ArrayList<TreatmentChild>();
        pastila1.add(new TreatmentChild("5 days", "no description", "ibuprfen, nurofen"));


        List<TreatmentChild> pastila2 = new ArrayList<TreatmentChild>();
        pastila2.add(new TreatmentChild("12 days", "take the ascorbinka 3 times a day", "ascorbinka"));


        List<TreatmentChild> pastila3 = new ArrayList<TreatmentChild>();
        pastila3.add(new TreatmentChild("2 days", "no description", "mezim"));


        listDataChild.put(listDataHeader.get(0).toString(), pastila1); // Header, Child data
        listDataChild.put(listDataHeader.get(1).toString(), pastila2);
        listDataChild.put(listDataHeader.get(2).toString(), pastila3);

        //Bundle extras = getActivity().getIntent().getExtras();
        //if (extras != null) {
        //    String treatmentTitle = getActivity().getIntent().getExtras().getString("treatment title");
            //String treatmentDescription = getActivity().getIntent().getExtras().getString("treatment description");
            //String spinnerState = getActivity().getIntent().getExtras().getString("spinner state");
            //listDataHeader.add(treatmentTitle);
            //String strtext = getArguments().getString("my_key");
        //    listDataHeader.add(treatmentTitle);
        //}
    }

    public void viewData() {
        Cursor cursor2 = db.getAllTreatments();

        if(cursor2.getCount()==0)
            Toast.makeText(getActivity(),"No treatments",Toast.LENGTH_SHORT).show();
        else
            while (cursor2.moveToNext()){

            listDataHeader.add(new TreatmentHeader(cursor2.getString(1),cursor2.getString(4)));
            List<TreatmentChild> tratamentList = new ArrayList<>();
            tratamentList.add(new TreatmentChild(cursor2.getString(2),cursor2.getString(3),cursor2.getString(5)));
            listDataChild.put(listDataHeader.get(listDataHeader.size() - 1).toString(),tratamentList);
            }

        TreatmentAdapter adapter = new TreatmentAdapter(getActivity(), listDataHeader, listDataChild);

        expListView.setAdapter(adapter);

    }
}
