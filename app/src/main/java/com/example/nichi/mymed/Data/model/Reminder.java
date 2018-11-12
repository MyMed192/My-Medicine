package com.example.nichi.mymed.Data.model;

import java.sql.Time;

public class Reminder {

    public final static String TABLE_NAME = "reminder";
    public final static String COLUMN_TREATMENT_ID = "treatment_id";
    public final static String COLUMN_MEDICINE_ID = "medicine_id";
    public final static String COLUMN_TIMES="times";
    public final static String COLUMN_FREQUENCY_ID="frequency_id";
    public final static String COLUMN_SCHEDULE_ID="schedule_id";

    private int id;
    private int treatment_id;
    private int medicine_id;
    private int times;
    private int frequency_id;
    private int schedule_id;


    public final static String CREATE_TABLE="CREATE TABLE " + TABLE_NAME + " ("
            + COLUMN_MEDICINE_ID + " INTEGER NOT NULL,"
            + COLUMN_TREATMENT_ID +" INTEGER NOT NULL,"
            + COLUMN_TIMES+" INTEGER NOT NULL,"
            + COLUMN_FREQUENCY_ID +" INTEGER NOT NULL,"
            + COLUMN_SCHEDULE_ID +" INTEGER NOT NULL,"
            + "FOREIGN KEY (" + COLUMN_FREQUENCY_ID + ") REFERENCES " + Frequency.TABLE_NAME+"("+Frequency._ID+"),"
            + "FOREIGN KEY (" + COLUMN_SCHEDULE_ID + " ) REFERENCES " + Schedule.TABLE_NAME+"("+Schedule._ID+"),"
            + "FOREIGN KEY (" + COLUMN_MEDICINE_ID + " ) REFERENCES " + Medicine.TABLE_NAME+"("+Medicine._ID+"),"
            + "FOREIGN KEY (" + COLUMN_TREATMENT_ID +" ) REFERENCES " + Treatments.TABLE_NAME+"("+Treatments._ID+"),"
            + "PRIMARY KEY (" + COLUMN_MEDICINE_ID + " , "+ COLUMN_TREATMENT_ID +")" +
            ");";

    public Reminder(int id, int treatment_id, int medicine_id, int times, int frequency_id, int schedule_id) {
        this.id = id;
        this.treatment_id = treatment_id;
        this.medicine_id = medicine_id;
        this.times = times;
        this.frequency_id = frequency_id;
        this.schedule_id = schedule_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTreatment_id() {
        return treatment_id;
    }

    public void setTreatment_id(int treatment_id) {
        this.treatment_id = treatment_id;
    }

    public int getMedicine_id() {
        return medicine_id;
    }

    public void setMedicine_id(int medicine_id) {
        this.medicine_id = medicine_id;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public int getFrequency_id() {
        return frequency_id;
    }

    public void setFrequency_id(int frequency_id) {
        this.frequency_id = frequency_id;
    }

    public int getSchedule_id() {
        return schedule_id;
    }

    public void setSchedule_id(int schedule_id) {
        this.schedule_id = schedule_id;
    }
}
