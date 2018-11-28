package com.example.nichi.mymed.mAdapter;


import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.nichi.mymed.R;
import com.example.nichi.mymed.mExpandables.TreatmentChild;
import com.example.nichi.mymed.mExpandables.TreatmentHeader;

import java.util.HashMap;
import java.util.List;

public class TreatmentAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<TreatmentHeader> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<TreatmentChild>> _listDataChild;

    public TreatmentAdapter(Context context, List<TreatmentHeader> listDataHeader, HashMap<String, List<TreatmentChild>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition).toString()).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        TreatmentChild treatmentStartDate = (TreatmentChild) getChild(groupPosition, childPosition);
        TreatmentChild treatmentDescription = (TreatmentChild) getChild(groupPosition, childPosition);
        TreatmentChild treatmentEndDate = (TreatmentChild) getChild(groupPosition, childPosition);


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.treatment_child_items, null);
        }

        TextView tStartDate = (TextView) convertView.findViewById(R.id.dateID);

        TextView tDescr = (TextView) convertView.findViewById(R.id.descriptionID);

        TextView tEndDate = (TextView) convertView.findViewById(R.id.enddateID);

        tStartDate.setText(treatmentStartDate.getmStartDate());
        tDescr.setText(treatmentDescription.getDescription());
        tEndDate.setText(treatmentEndDate.getmEndDate());
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition).toString()).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {

        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        TreatmentHeader headerTitle = (TreatmentHeader) getGroup(groupPosition);
        TreatmentHeader headerStatus = (TreatmentHeader) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.treatment_group_items, null);
        }

        TextView treatTitle = (TextView) convertView.findViewById(R.id.tTitle);
        treatTitle.setTypeface(null, Typeface.BOLD);
        treatTitle.setText(headerTitle.getTitle());

        TextView treatStatus = (TextView) convertView.findViewById(R.id.state);
        treatStatus.setTypeface(null, Typeface.BOLD);
        treatStatus.setText(headerStatus.getState());

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
