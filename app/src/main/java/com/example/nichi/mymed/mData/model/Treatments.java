package com.example.nichi.mymed.mData.model;


public class Treatments {

    public final static String TABLE_NAME = "treatments";
    public final static String _ID = "id";
    public final static String COLUMN_TREATMENT_NAME ="name";
    public final static String COLUMN_TREATMENT_START_DATE = "start_date";
    public final static String COLUMN_TREATMENT_END_DATE = "end_date";
    public final static String COLUMN_TREATMENT_STATE_ID = "state_id";
    public final static String COLUMN_TREATMENT_DESCRIPTION = "description";

    private int id;
    private String name;
    private String start_date;
    private String end_date;
    private String state;
    private String description;

    public final static String CREATE_TABLE=  "CREATE TABLE " + TABLE_NAME + " ("
                + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TREATMENT_NAME + " TEXT NOT NULL, "
                + COLUMN_TREATMENT_START_DATE+ " DATE, "
                + COLUMN_TREATMENT_END_DATE + " DATE, "
                + COLUMN_TREATMENT_STATE_ID + " INTEGER, "
                + COLUMN_TREATMENT_DESCRIPTION + " TEXT, "
                + "FOREIGN KEY(state_id) REFERENCES state(id));";

    public Treatments(int id, String name, String start_date, String end_date, String state, String description) {
        this.id = id;
        this.name = name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.state = state;
        this.description = description;
    }

    //public Treatments(String name, String startdate, String endate, int state_id, String comments) {
    //    this.name=name;
    //    this.start_date = startdate;
    //    this.end_date = endate;
    //    this.state_id = state_id;
    //    this.description =  comments;
    //}


    public Treatments(String name, String start_date, String end_date, String state, String description) {
        this.name = name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.state = state;
        this.description = description;
    }

    public Treatments() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getState_id() {
        return state;
    }

    public void setState_id(String state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
