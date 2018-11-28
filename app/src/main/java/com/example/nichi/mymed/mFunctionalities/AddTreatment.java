package com.example.nichi.mymed.mFunctionalities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.nichi.mymed.R;

public class AddTreatment extends AppCompatDialogFragment {
    private EditText treatmentTitle;
    private EditText treatmentStartDate;
    private EditText treatmentDescription;
    private EditText treatmentEndDate;
    private AddMedicine.ExampleDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_treatments, null);

        builder.setView(view)
                .setTitle("Add treatment")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String medicineName = treatmentTitle.getText().toString();
                        String medicineLifeTime = treatmentStartDate.getText().toString();
                        String medicineQuantity = treatmentDescription.getText().toString();
                        String medicineComments = treatmentEndDate.getText().toString();
                        listener.applyTexts(medicineName, medicineLifeTime, medicineQuantity, medicineComments);
                    }
                });

        treatmentTitle = view.findViewById(R.id.treatmentTitle);
        treatmentStartDate = view.findViewById(R.id.treatmentStartDate);
        treatmentDescription = view.findViewById(R.id.treatmentDescription);
        treatmentEndDate = view.findViewById(R.id.treatmentEndDate);
        return builder.create();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        try {
            listener = (AddMedicine.ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "Must implement ExampleDialogListener");
        }
    }

    public interface ExampleDialogListener{
        void applyTexts(String medicineName, String medicineLifeTime,
                        String medicineQuantity, String medicineComments);
    }
}
