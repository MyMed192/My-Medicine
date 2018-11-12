package com.example.nichi.mymed.Data.model;

import java.sql.Time;

public class Schedule {

    public final static String TABLE_NAME = "schedule";
    public final static String _ID = "id";
    public final static String COLUMN_HOUR ="hour";

    private int id;
    private Time hour;

    public final static String CREATE_TABLE="CREATE TABLE " + TABLE_NAME + " ("
            + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_HOUR+ " TIME" +
            ");";

    public Schedule(int id, Time hour) {
        this.id = id;
        this.hour = hour;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Time getHour() {
        return hour;
    }

    public void setHour(Time hour) {
        this.hour = hour;
    }
}
