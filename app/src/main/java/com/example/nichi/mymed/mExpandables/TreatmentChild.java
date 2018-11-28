package com.example.nichi.mymed.mExpandables;

public class TreatmentChild {
    private String mStartDate;
    private String mEndDate;
    private String Description;
    private String InclMed;

    public TreatmentChild(String startDate, String endDate, String description) {
        mStartDate = startDate;
        mEndDate = endDate;
        Description = description;
    }

    public String getmStartDate() {
        return mStartDate;
    }

    public void setmStartDate(String mStartDate) {
        this.mStartDate = mStartDate;
    }

    public String getmEndDate() {
        return mEndDate;
    }

    public void setmEndDate(String mEndDate) {
        this.mEndDate = mEndDate;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

}
