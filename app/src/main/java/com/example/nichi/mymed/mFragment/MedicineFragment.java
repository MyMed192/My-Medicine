package com.example.nichi.mymed.mFragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
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
    List<Medicine> medicines = new ArrayList<>();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_medicine, container, false);

        db = new DataBaseAdapter(getActivity().getApplicationContext());


        // get the listview
        expListView = (ExpandableListView) view.findViewById(R.id.lvExp);
        // preparing list data
        //prepareListData();

        listAdapter = new MedicineAdapter(getActivity(), listDataHeader, listDataChild);

        expListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                CharSequence colors[] = new CharSequence[]{"Edit", "Delete"};

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Choose option");
                builder.setItems(colors, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            showMedicineDialog(medicines.get(position), position);
                        } else {
                            String name = listDataHeader.get(position).getHeaderTitle();
                            db.deleteMedicine(name);
                            listDataHeader.remove(position);
                            expListView.invalidateViews();
                        }
                    }
                });
                builder.show();
                return true;
            }
        });


        listAdapter.notifyDataSetChanged();

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

    private void showMedicineDialog(final Medicine medicine, final int position) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getContext());
        View view = layoutInflaterAndroid.inflate(R.layout.edit_dialog, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(getActivity());
        alertDialogBuilderUserInput.setView(view);

        final EditText mName = view.findViewById(R.id.edit_medicineName);
        final EditText mQuantity = view.findViewById(R.id.edit_medicineQuantity);
        final EditText mExpirationDate = view.findViewById(R.id.edit_medicineExpirationDate);
        final EditText mComments = view.findViewById(R.id.edit_medicineComments);

        TextView dialogTitle = view.findViewById(R.id.dialog_title);
        dialogTitle.setText(getString(R.string.lbl_edit_note_title));

        if (medicine != null) {
            mName.setText(medicine.getName());
            mQuantity.setText(medicine.getQuantity());
            mExpirationDate.setText(medicine.getLifetime());
            mComments.setText(medicine.getComments());
        }
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton( "update" , new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {
                        if (medicine != null) {
                            // update note by it's id
                            Medicine updMed=new Medicine(mName.getText().toString(),mExpirationDate.getText().toString(),mQuantity.getText().toString(),mComments.getText().toString());
                            updateMedicine(updMed, position);
                            expListView.invalidateViews();
                        }
                    }
                })
                .setNegativeButton("cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();

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
    public void viewData() {
        Cursor cursor=db.getAllMedicines();

       if(cursor.getCount()==0)
           Toast.makeText(getActivity(),"No medicines",Toast.LENGTH_SHORT).show();
       else
           while (cursor.moveToNext()){
           listDataHeader.add(new MedicineHeader(cursor.getString(1),cursor.getString(3)));
           List<MedicineChild> pastilaCursor = new ArrayList<MedicineChild>();
           pastilaCursor.add(new MedicineChild(cursor.getString(2),cursor.getString(4)));
           listDataChild.put(listDataHeader.get(listDataHeader.size()- 1).toString(),pastilaCursor);
               Medicine m=new Medicine();
               m.setId(cursor.getInt(0));
               m.setName(cursor.getString(1));
               m.setQuantity(cursor.getString(2));
               m.setLifetime(cursor.getString(3));
               m.setComments(cursor.getString(4));
               medicines.add(m);

           }

           MedicineAdapter adapter = new MedicineAdapter(getActivity(), listDataHeader, listDataChild);

       expListView.setAdapter(adapter);

    }

    void updateMedicine(Medicine m,int position){
        Medicine n = medicines.get(position);
        // updating note text
        n.setName(m.getName());
        n.setQuantity(m.getQuantity());
        n.setLifetime(m.getLifetime());
        n.setComments(m.getComments());

        // updating note in db
        db.updateMedicines(n);

        // refreshing the list
        medicines.set(position, n);
        listAdapter.notifyDataSetChanged();
    }

}




