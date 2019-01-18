package com.example.nichi.mymed;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nichi.mymed.mAdapter.MedicineAdapter;
import com.example.nichi.mymed.mAdapter.SpinnerAdapter;
import com.example.nichi.mymed.mAdapter.TreatmentAdapter;
import com.example.nichi.mymed.mData.DataBaseAdapter;
import com.example.nichi.mymed.mData.model.Medicine;
import com.example.nichi.mymed.mData.model.Treatments;
import com.example.nichi.mymed.mExpandables.MedicineChild;
import com.example.nichi.mymed.mExpandables.MedicineHeader;
import com.example.nichi.mymed.mExpandables.TreatmentChild;
import com.example.nichi.mymed.mExpandables.TreatmentHeader;
import com.example.nichi.mymed.mFragment.MedicineFragment;
import com.example.nichi.mymed.mFragment.TreatmentsFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView textViewMedicineName;
    private TextView textViewMedicineLifeTime;
    private TextView textViewQuantity;
    private TextView textViewComments;
    private Button buttonSave;
    FloatingActionButton fab, fab1, fab2;
    Animation FabOpen, FabClose, FabRClockwise, FabRAntiClockwise;
    boolean isOpen = false;

    public int mYear, mMonth, mDay, mHour, mMinute;

    /////MEDICINE ATRIBUTES
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
    DataBaseAdapter db;
    /////MEDICINE ATRIBUTES

    ///TREATMENT ATRIBUTES
    TreatmentAdapter listAdapter;
    ExpandableListView expListView;
    List<TreatmentHeader> listDataHeaderTreat = new ArrayList<>();
    HashMap<String, List<TreatmentChild>> listDataChildTreat = new HashMap<>();
    final Calendar myCalendar = Calendar.getInstance();
    private Button setStartDate;
    private Button setEndDate;
    private EditText treatmentTitle;
    private EditText treatmentStartDate;
    private EditText treatmentEndDate;
    private EditText treatmentDescription;
    private Spinner treatmentState;
    ///TREATMENT ATRIBUTES

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;
    private ViewGroup vg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vg = findViewById(R.id.main_activity);

        final ImageView imageView = super.findViewById(R.id.image);
        imageView.setVisibility(View.VISIBLE);
        imageView.setAlpha(1f);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                imageView.setVisibility(View.GONE);
            }
        }, 5000);


        //while (checkBox.isChecked()) {
        //    imageButton.setVisibility(View.VISIBLE);
        //}

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab2);
        fab2 = (FloatingActionButton) findViewById(R.id.fab1);
        FabOpen = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_open);
        FabClose = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        FabRClockwise = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_clockwise);
        FabRAntiClockwise = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_anticlockwise);

        db = new DataBaseAdapter(MainActivity.this.getApplicationContext());

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isOpen){
                    fab2.startAnimation(FabClose);
                    fab1.startAnimation(FabClose);
                    fab.startAnimation(FabRAntiClockwise);
                    fab2.setClickable(false);
                    fab1.setClickable(false);
                    isOpen=false;
                }
                else {
                    fab2.startAnimation(FabOpen);
                    fab1.startAnimation(FabOpen);
                    fab.startAnimation(FabRClockwise);
                    fab2.setClickable(true);
                    fab1.setClickable(true);
                    isOpen=true;
                }

                ///ADD MEDICINE
                fab1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mBuilder = new AlertDialog.Builder(MainActivity.this);
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
                                    Toast.makeText(MainActivity.this,
                                            getString(R.string.success_adding_medicine),
                                            Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }else {
                                    Toast.makeText(MainActivity.this,
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

                            }
                        });

                        dialog.show();
                    }
                });
                ///ADD MEDICINE


                ///ADD TREATMENT
                fab2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    protected Object clone() throws CloneNotSupportedException {
                        return super.clone();
                    }

                    @Override
                    public void onClick(View v) {
                        //Intent intent = new Intent(getActivity(), AddTreatment.class);
                        //startActivity(intent);
                        mBuilder = new AlertDialog.Builder(MainActivity.this);
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

                        SpinnerAdapter spAdapter = new SpinnerAdapter(MainActivity.this, R.layout.spinner_row, strText);
                        spAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                        treatmentState.setAdapter(spAdapter);

                        setStartDate = (Button) mView.findViewById(R.id.setStartDate);
                        setEndDate = (Button) mView.findViewById(R.id.setEndDate);

                       setStartDate.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               final Calendar c = Calendar.getInstance();
                               mYear = c.get(Calendar.YEAR);
                               mMonth = c.get(Calendar.MONTH);
                               mDay = c.get(Calendar.DAY_OF_MONTH);


                               DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(),
                                       new DatePickerDialog.OnDateSetListener() {

                                           @Override
                                           public void onDateSet(DatePicker view, int year,
                                                                 int monthOfYear, int dayOfMonth) {

                                               treatmentStartDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                           }
                                       }, mYear, mMonth, mDay);
                               datePickerDialog.show();
                           }
                       });

                       setEndDate.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               final Calendar c = Calendar.getInstance();
                               mYear = c.get(Calendar.YEAR);
                               mMonth = c.get(Calendar.MONTH);
                               mDay = c.get(Calendar.DAY_OF_MONTH);


                               DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(),
                                       new DatePickerDialog.OnDateSetListener() {

                                           @Override
                                           public void onDateSet(DatePicker view, int year,
                                                                 int monthOfYear, int dayOfMonth) {

                                               treatmentEndDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                           }
                                       }, mYear, mMonth, mDay);
                               datePickerDialog.show();
                           }

                       });

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
                                    Toast.makeText(MainActivity.this, "Treatment was added successfuly", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }else {
                                    Toast.makeText(MainActivity.this,
                                            getString(R.string.error_adding_medicine),
                                            Toast.LENGTH_SHORT).show();
                                }

                                listDataHeaderTreat.add(new TreatmentHeader(treatmentTitle.getText().toString(), treatmentState.getSelectedItem().toString()));
                                List<TreatmentChild> treatmentFinal = new ArrayList<TreatmentChild>();
                                treatmentFinal.add(new TreatmentChild(treatmentStartDate.getText().toString(),treatmentEndDate.getText().toString(),treatmentDescription.getText().toString()));
                                listDataChildTreat.put(listDataHeaderTreat.get(listDataHeaderTreat.size() - 1).toString(),treatmentFinal);


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

                ///ADD TREATMENT
            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }


        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));


            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    MedicineFragment tab1 = new MedicineFragment();
                    return tab1;
                case 1:
                    TreatmentsFragment tab2 = new TreatmentsFragment();
                    return tab2;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
