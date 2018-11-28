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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.nichi.mymed.mData.DataBaseAdapter;
import com.example.nichi.mymed.mData.model.Medicine;
import com.example.nichi.mymed.mAdapter.MedicineAdapter;
import com.example.nichi.mymed.mExpandables.MedicineChild;
import com.example.nichi.mymed.mExpandables.MedicineHeader;
import com.example.nichi.mymed.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MedicineFragment extends Fragment {

    ArrayList<String> listItem = new ArrayList<>();
    MedicineAdapter listAdapter;
    ExpandableListView expListView;
    List<MedicineHeader> listDataHeader = new ArrayList<MedicineHeader>();
    HashMap<String, List<MedicineChild>> listDataChild = new HashMap<String, List<MedicineChild>>();
    EditText mName;
    AlertDialog dialog;
    EditText mLifetime;
    private static final String DATABASE_NAME = "MyMed_db";
    View mView;
    AlertDialog.Builder mBuilder;
    EditText mQuantity;
    EditText mComments;
    CheckBox checkBox;
    DataBaseAdapter db;

    Activity mActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_medicine, container, false);

        db = new DataBaseAdapter(getActivity().getApplicationContext());

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBuilder = new AlertDialog.Builder(getActivity());
                mView = getLayoutInflater().inflate(R.layout.layout_dialog, null);
                mBuilder.setView(mView);
                dialog = mBuilder.create();
                mName = (EditText) mView.findViewById(R.id.edit_medicineName);
                mLifetime = (EditText) mView.findViewById(R.id.edit_medicineLifeTime);
                mQuantity = (EditText) mView.findViewById(R.id.edit_medicineQuantity);
                mComments = (EditText) mView.findViewById(R.id.edit_medicineComments);
                Button mSave = (Button) mView.findViewById(R.id.buttonSave);
                final Button mCancel = (Button) mView.findViewById(R.id.buttonCancel);

                mCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();            }
                });
                mSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(!mName.getText().toString().isEmpty() &&
                                !mLifetime.getText().toString().isEmpty() &&
                                !mQuantity.getText().toString().isEmpty() &&
                                !mComments.getText().toString().isEmpty()){
                            Toast.makeText(getActivity(),
                                    getString(R.string.success_adding_medicine),
                                    Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }else {
                            Toast.makeText(getActivity(),
                                    getString(R.string.error_adding_medicine),
                                    Toast.LENGTH_SHORT).show();
                        }

                        listDataHeader.add(new MedicineHeader(mName.getText().toString(), mLifetime.getText().toString()));
                        List<MedicineChild> pastilaFinal = new ArrayList<MedicineChild>();
                        pastilaFinal.add(new MedicineChild(mQuantity.getText().toString(), mComments.getText().toString()));
                        listDataChild.put(listDataHeader.get(listDataHeader.size()- 1).toString(), pastilaFinal);

                        Medicine s = new Medicine();
                        try {
                            s.setName(mName.getText().toString());
                            s.setQuantity(mQuantity.getText().toString());
                            s.setComments(mComments.getText().toString());
                            s.setLifetime(mLifetime.getText().toString());
                        } catch ( NullPointerException e){

                        }

                        db.insertMedicine(s);

                        mName.setText("");
                        mLifetime.setText("");
                        mQuantity.setText("");
                        mComments.setText("");


                        //boolean isInsered = db.insertMedicine(s);
                        //if (isInsered) {
                       //     Toast.makeText(getActivity(), "MEDICINE is added successfully!", Toast.LENGTH_SHORT).show();
                        //} else {
                        //    Toast.makeText(getActivity(), "ERROR IN ADDING MEDICINE", Toast.LENGTH_SHORT).show();
                        //}
                    }
                });
                dialog.show();

            }
        });


        // get the listview
        expListView = (ExpandableListView) view.findViewById(R.id.lvExp);
        // preparing list data
        prepareListData();

        listAdapter = new MedicineAdapter(getActivity(), listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);


        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                 //Toast.makeText(getApplicationContext(), "Group Clicked " + listDataHeader.get(groupPosition),
                 //Toast.LENGTH_SHORT).show();
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

        viewData();
        return view;
    }

    private void prepareListData() {

        listDataHeader.add(new MedicineHeader("Ibuprofen", "01.01.2019"));
        listDataHeader.add(new MedicineHeader("Valeriana", "02.05.2020"));
        listDataHeader.add(new MedicineHeader("Nurofen", "14.07.2019"));

        List<MedicineChild> pastila1 = new ArrayList<MedicineChild>();
        pastila1.add(new MedicineChild("23 pills", "Do no exagerate"));

        List<MedicineChild> pastila2 = new ArrayList<MedicineChild>();
        pastila2.add(new MedicineChild("120 pills", "be free"));

        List<MedicineChild> pastila3 = new ArrayList<MedicineChild>();
        pastila3.add(new MedicineChild("20 pills", "no comment"));

        listDataChild.put(listDataHeader.get(0).toString(), pastila1); // Header, Child data
        listDataChild.put(listDataHeader.get(1).toString(), pastila2);
        listDataChild.put(listDataHeader.get(2).toString(), pastila3);

    }
    private void viewData() {
        Cursor cursor=db.getAllMedicines();

       if(cursor.getCount()==0)
           Toast.makeText(getActivity(),"No medicines",Toast.LENGTH_SHORT).show();
       else
           while (cursor.moveToNext()){
           listDataHeader.add(new MedicineHeader(cursor.getString(1),cursor.getString(3)));
           List<MedicineChild> pastilaCursor = new ArrayList<MedicineChild>();
           pastilaCursor.add(new MedicineChild(cursor.getString(2),cursor.getString(4)));
           listDataChild.put(listDataHeader.get(listDataHeader.size()- 1).toString(),pastilaCursor);

           }

           MedicineAdapter adapter = new MedicineAdapter(getActivity(), listDataHeader, listDataChild);

       expListView.setAdapter(adapter);

    }

    public void addMedicine(){

        Medicine s = new Medicine();
        try {
            s.setName(mName.getText().toString());
            s.setQuantity(mQuantity.getText().toString());
            s.setComments(mComments.getText().toString());
            s.setLifetime(mLifetime.getText().toString());
        } catch ( NullPointerException e){


            boolean isInsered = db.insertMedicine(s);
                if (isInsered) {
                    Toast.makeText(getActivity(), "MEDICINE is added successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "ERROR IN ADDING MEDICINE", Toast.LENGTH_SHORT).show();
                }
            }
}

}




