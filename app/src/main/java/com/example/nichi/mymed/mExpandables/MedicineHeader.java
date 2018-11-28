package com.example.nichi.mymed.mExpandables;

public class MedicineHeader {
    private String headerTitle;
    private String headerDate;

    public MedicineHeader(String headerTitle, String headerDate) {
        this.headerTitle = headerTitle;
        this.headerDate = headerDate;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public String getHeaderDate() {
        return headerDate;
    }

    public void setHeaderDate(String headerDate) {
        this.headerDate = headerDate;
    }
}
