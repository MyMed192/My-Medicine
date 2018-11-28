package com.example.nichi.mymed;

import android.os.Handler;
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

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nichi.mymed.mFragment.MedicineFragment;
import com.example.nichi.mymed.mFragment.TreatmentsFragment;

public class MainActivity extends AppCompatActivity {
    private TextView textViewMedicineName;
    private TextView textViewMedicineLifeTime;
    private TextView textViewQuantity;
    private TextView textViewComments;
    private Button buttonSave;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView imageView = super.findViewById(R.id.image);
        imageView.setVisibility(View.VISIBLE);
        imageView.setAlpha(1f);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                imageView.setVisibility(View.GONE);
            }
        }, 5000);


        //CheckBox checkBox = findViewById(R.id.checkBoxMedicine);
        ImageButton imageButton = findViewById(R.id.deleteButton);

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

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //fab.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        //        final View mView = getLayoutInflater().inflate(R.layout.layout_dialog, null);
        //        mBuilder.setView(mView);
        //        final AlertDialog dialog = mBuilder.create();
        //        final EditText mName = (EditText) mView.findViewById(R.id.edit_medicineName);
        //        final EditText mLifetime = (EditText) mView.findViewById(R.id.edit_medicineLifeTime);
        //        final EditText mQuantity = (EditText) mView.findViewById(R.id.edit_medicineQuantity);
        //        final EditText mComments = (EditText) mView.findViewById(R.id.edit_medicineComments);
        //        Button mSave = (Button) mView.findViewById(R.id.buttonSave);
        //        Button mCancel = (Button) mView.findViewById(R.id.buttonCancel);
//
        //        mCancel.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View view) {
        //                dialog.dismiss();            }
        //});
        //        mSave.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View view) {
//
        //                if(!mName.getText().toString().isEmpty() &&
       //                        !mLifetime.getText().toString().isEmpty() &&
       //                        !mQuantity.getText().toString().isEmpty() &&
       //                        !mComments.getText().toString().isEmpty()){
       //                    Toast.makeText(MainActivity.this,
       //                            getString(R.string.success_adding_medicine),
       //                            Toast.LENGTH_SHORT).show();
       //                }else {
       //                    Toast.makeText(MainActivity.this,
       //                            getString(R.string.error_adding_medicine),
       //                            Toast.LENGTH_SHORT).show();
       //                }
       //                mName.setText("");
       //                mLifetime.setText("");
        //                mQuantity.setText("");
        //                mComments.setText("");
        //            }
        //        });
        //        dialog.show();
        //    }
        //});

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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
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
