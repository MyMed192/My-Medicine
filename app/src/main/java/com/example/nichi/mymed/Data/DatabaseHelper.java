package com.example.nichi.mymed.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.nichi.mymed.Data.model.Frequency;
import com.example.nichi.mymed.Data.model.Medicine;
import com.example.nichi.mymed.Data.model.Reminder;
import com.example.nichi.mymed.Data.model.Schedule;
import com.example.nichi.mymed.Data.model.Treatments;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "MyMed_db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create Medicine table
        db.execSQL(Medicine.CREATE_TABLE);
        // create Schedule table
        db.execSQL(Schedule.CREATE_TABLE);
        // create Frequency table
        db.execSQL(Frequency.CREATE_TABLE);
        // create Treatment table
        db.execSQL(Treatments.CREATE_TABLE);
        // create Reminder table
        db.execSQL(Reminder.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Medicine.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Schedule.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Frequency.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Treatments.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Reminder.TABLE_NAME);
        // Create tables again
        onCreate(db);
    }
}
