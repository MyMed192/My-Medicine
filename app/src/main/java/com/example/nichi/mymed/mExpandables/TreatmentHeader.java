package com.example.nichi.mymed.mExpandables;

public class TreatmentHeader {
    private String Title;
    private String State;

    public TreatmentHeader(String title, String state) {
        Title = title;
        State = state;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }
}
