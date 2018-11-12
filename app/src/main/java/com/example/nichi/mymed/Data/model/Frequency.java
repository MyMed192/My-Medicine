package com.example.nichi.mymed.Data.model;

public class Frequency {

    public final static String TABLE_NAME = "frequency";
    public final static String _ID = "id";
    //can be: daily, weekly, monthly
    public final static String COLUMN_FREQUENCY="name";

    private int id;
    private String name;

    public final static String CREATE_TABLE="CREATE TABLE " + TABLE_NAME + " ("
            + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_FREQUENCY+ " TEXT NOT NULL" +
            ");";

    public Frequency(int id, String name) {
        this.id = id;
        this.name = name;
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
}
