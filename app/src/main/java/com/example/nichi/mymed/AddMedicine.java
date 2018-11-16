package com.example.nichi.mymed;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class AddMedicine extends AppCompatDialogFragment {
    private EditText editTextName;
    private EditText editTextLifeTime;
    private EditText editTextQuantity;
    private EditText editTextComments;
    private ExampleDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        builder.setView(view)
                .setTitle("Add medicine")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String medicineName = editTextName.getText().toString();
                        String medicineLifeTime = editTextLifeTime.getText().toString();
                        String medicineQuantity = editTextQuantity.getText().toString();
                        String medicineComments = editTextComments.getText().toString();
                        listener.applyTexts(medicineName, medicineLifeTime, medicineQuantity, medicineComments);
                    }
                });

        editTextName = view.findViewById(R.id.edit_medicineName);
        editTextLifeTime = view.findViewById(R.id.edit_medicineLifeTime);
        editTextQuantity = view.findViewById(R.id.edit_medicineQuantity);
        editTextComments = view.findViewById(R.id.edit_medicineComments);
        return builder.create();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        try {
            listener = (ExampleDialogListener) context;
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
