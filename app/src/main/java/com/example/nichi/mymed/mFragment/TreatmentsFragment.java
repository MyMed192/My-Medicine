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
    List<TreatmentHeader> listDataHeader;
    HashMap<String, List<TreatmentChild>> listDataChild;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_treatments, container, false);

        db = new DataBaseAdapter(getActivity().getApplicationContext());
        //treatmentState = (Spinner) mView.findViewById(R.id.treatmentState);
        //db.deleteTreatment(1);

        FloatingActionButton fab = view.findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getActivity(), AddTreatment.class);
                //startActivity(intent);
                mBuilder = new AlertDialog.Builder(getActivity());
                mView = getLayoutInflater().inflate(R.layout.layout_dialog_treatments, null);
                mBuilder.setView(mView);
                dialog = mBuilder.create();
                treatmentState = (Spinner) mView.findViewById(R.id.treatmentState);
                treatmentTitle = (EditText) mView.findViewById(R.id.treatmentTitle);
                treatmentStartDate = (EditText) mView.findViewById(R.id.treatmentStartDate);
                treatmentEndDate = (EditText) mView.findViewById(R.id.treatmentEndDate);
                treatmentDescription = (EditText) mView.findViewById(R.id.treatmentDescription);
                Button mSave = (Button) mView.findViewById(R.id.saveTreatment);
                Button mCancel = (Button) mView.findViewById(R.id.cancelTreatment);

                String[] strText = { "Done", "In Progress", "To be done" };

                SpinnerAdapter spAdapter = new SpinnerAdapter(getActivity(), R.layout.spinner_row, strText);
                //spAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                treatmentState.setAdapter(spAdapter);

                mCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                mSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(!treatmentTitle.getText().toString().isEmpty() &&
                                !treatmentStartDate.getText().toString().isEmpty() &&
                                !treatmentEndDate.getText().toString().isEmpty() &&
                                !treatmentDescription.getText().toString().isEmpty() &&
                                !treatmentState.getSelectedItem().toString().isEmpty()){
                            Toast.makeText(getActivity(), "Treatment was added successfuly", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }else {
                            Toast.makeText(getActivity(),
                                    getString(R.string.error_adding_medicine),
                                    Toast.LENGTH_SHORT).show();
                        }

                        listDataHeader.add(new TreatmentHeader(treatmentTitle.getText().toString(), treatmentState.getSelectedItem().toString()));
                        List<TreatmentChild> treatmentFinal = new ArrayList<TreatmentChild>();
                        treatmentFinal.add(new TreatmentChild(treatmentStartDate.getText().toString(),treatmentEndDate.getText().toString(),treatmentDescription.getText().toString()));
                        listDataChild.put(listDataHeader.get(listDataHeader.size() - 1).toString(),treatmentFinal);

                        Treatments treatment = new Treatments();
                        try {
                            treatment.setName(treatmentTitle.getText().toString());
                            treatment.setStart_date(treatmentStartDate.getText().toString());
                            treatment.setEnd_date(treatmentEndDate.getText().toString());
                            treatment.setState_id(treatmentState.getSelectedItem().toString());
                            treatment.setDescription(treatmentDescription.getText().toString());
                        } catch ( NullPointerException e){

                        }

                        db.insertTreatment(treatment);

                    }
                });
                dialog.show();
            }
        });

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

    private void viewData() {
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
