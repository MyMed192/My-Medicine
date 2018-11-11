package com.example.nichi.mymed;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class MedicineFragment extends Fragment {


    public MedicineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_medicine, container, false);


        String[] medicines = {"Nurofen", "Carbune", "Nolpaza", "Hartil", "Rivotril"};
        ListView medicineListView = (ListView) view.findViewById(R.id.medicinesList);
        ArrayAdapter medicineList = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, medicines);

        medicineListView.setAdapter(medicineList);

        // Inflate the layout for this fragment
        return view;
    }

}
