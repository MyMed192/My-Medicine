package com.example.nichi.mymed.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.nichi.mymed.Data.model.Frequency;
import com.example.nichi.mymed.Data.model.Medicine;
import com.example.nichi.mymed.Data.model.Reminder;
import com.example.nichi.mymed.Data.model.Schedule;
import com.example.nichi.mymed.Data.model.TreatmentState;
import com.example.nichi.mymed.Data.model.Treatments;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Logcat tag
    private static final String LOG = "DatabaseHelper";

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

        // insert in frequency

        // insert in state

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

    //--------------------------------------Medicines---------------------------
    //insert a Medicine
    public long insertMedicine(String name, String lifetime, int quantity, String comments){
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();
        Medicine med=new Medicine(name,lifetime,quantity,comments);
        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Medicine.COLUMN_MEDICINE_NAME, med.getName());
        values.put(Medicine.COLUMN_MEDICINE_LIFETIME, med.getLifetime());
        values.put(Medicine.COLUMN_MEDICINE_QUANTITY, med.getQuantity());
        values.put(Medicine.COLUMN_MEDICINE_COMMENTS, med.getComments());

        // insert row
        long id = db.insert(Medicine.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    //delete medicine
    public void deleteMedicine(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Medicine.TABLE_NAME, Medicine._ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    //get one medicine
    public Medicine getMedicine(long id){
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + Medicine.TABLE_NAME + " WHERE "
                + Medicine._ID + " = " + id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Medicine medicine = new Medicine();
        medicine.setId(c.getInt(c.getColumnIndex(Medicine._ID)));
        medicine.setName((c.getString(c.getColumnIndex(Medicine.COLUMN_MEDICINE_NAME))));
        medicine.setLifetime((c.getString(c.getColumnIndex(Medicine.COLUMN_MEDICINE_LIFETIME))));
        medicine.setQuantity((c.getInt(c.getColumnIndex(Medicine.COLUMN_MEDICINE_QUANTITY))));
        medicine.setComments((c.getString(c.getColumnIndex(Medicine.COLUMN_MEDICINE_COMMENTS))));

        return medicine;
    }

    //get all medicines
    public List<Medicine> getAllMedicines(){
        List<Medicine> medicineList=new ArrayList<>();
        String selectQuery="SELECT * FROM "+ Medicine.TABLE_NAME;

        Log.e(LOG,selectQuery);

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor c= db.rawQuery(selectQuery,null);

        //looping through all rows and adding to list
        if(c.moveToFirst()){
            do{
                Medicine medicine = new Medicine();
                medicine.setId(c.getInt(c.getColumnIndex(Medicine._ID)));
                medicine.setName((c.getString(c.getColumnIndex(Medicine.COLUMN_MEDICINE_NAME))));
                medicine.setLifetime((c.getString(c.getColumnIndex(Medicine.COLUMN_MEDICINE_LIFETIME))));
                medicine.setQuantity((c.getInt(c.getColumnIndex(Medicine.COLUMN_MEDICINE_QUANTITY))));
                medicine.setComments((c.getString(c.getColumnIndex(Medicine.COLUMN_MEDICINE_COMMENTS))));
                medicineList.add(medicine);
            }while (c.moveToNext());
        }
        return medicineList;
    }

    public int updateMedicines(Medicine medicine) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Medicine.COLUMN_MEDICINE_NAME, medicine.getName());
        values.put(Medicine.COLUMN_MEDICINE_LIFETIME,medicine.getLifetime());
        values.put(Medicine.COLUMN_MEDICINE_QUANTITY,medicine.getQuantity());
        values.put(Medicine.COLUMN_MEDICINE_COMMENTS,medicine.getComments());

        // updating row
        return db.update(Medicine.TABLE_NAME, values, Medicine._ID+ " = ?",
                new String[]{String.valueOf(medicine.getId())});
    }

    //--------------------------------------Frequency--------------------------
    //insert frequency
    public long insertFrequency(String name){
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();
        Frequency fr1= new Frequency();

        ContentValues values = new ContentValues();

        values.put(Frequency.COLUMN_FREQUENCY,name);

        // insert row
        long f1 = db.insert(Frequency.TABLE_NAME, null, values);
        db.close();

        return f1;
    }

    //delete frequency
    public void deleteFrequency(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Frequency.TABLE_NAME, Frequency._ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    //select frequency
    public Frequency getFrequency(long id){
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + Frequency.TABLE_NAME + " WHERE "
                + Frequency._ID + " = " + id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Frequency frequency=new Frequency();
        frequency.setId(c.getInt(c.getColumnIndex(Frequency._ID)));
        frequency.setName((c.getString(c.getColumnIndex(Frequency.COLUMN_FREQUENCY))));

        return frequency;
    }

    //get all medicines
    public List<Frequency> getAllFrequencies(){
        List<Frequency> frequencyList =new ArrayList<>();
        String selectQuery="SELECT * FROM "+ Frequency.TABLE_NAME;

        Log.e(LOG,selectQuery);

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor c= db.rawQuery(selectQuery,null);

        //looping through all rows and adding to list
        if(c.moveToFirst()){
            do{
                Frequency frequency=new Frequency();
                frequency.setId(c.getInt(c.getColumnIndex(Frequency._ID)));
                frequency.setName((c.getString(c.getColumnIndex(Frequency.COLUMN_FREQUENCY))));
                frequencyList.add(frequency);
            }while (c.moveToNext());
        }
        return frequencyList;
    }

    public int updateFrequency(Frequency frequency) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Frequency.COLUMN_FREQUENCY, frequency.getName());

        // updating row
        return db.update(Frequency.TABLE_NAME, values, Frequency._ID+ " = ?",
                new String[]{String.valueOf(frequency.getId())});
    }

    //--------------------------------------Treatment---------------------------
    //insert Treatment
    public long insertTreatment(String name,String startdate,String endate,int state_id,String comments){
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();
        Treatments treatment=new Treatments(name,startdate,endate,state_id,comments);

        ContentValues values = new ContentValues();

        values.put(Treatments.COLUMN_TREATMENT_NAME,treatment.getName());
        values.put(Treatments.COLUMN_TREATMENT_START_DATE,treatment.getStart_date());
        values.put(Treatments.COLUMN_TREATMENT_END_DATE,treatment.getEnd_date());
        values.put(Treatments.COLUMN_TREATMENT_STATE_ID,treatment.getState_id());
        values.put(Treatments.COLUMN_TREATMENT_DESCRIPTION,treatment.getDescription());

        // insert row
        long f1 = db.insert(Treatments.TABLE_NAME, null, values);
        db.close();

        return f1;
    }

    //delete treatment
    public void deleteTreatment(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Treatments.TABLE_NAME, Treatments._ID + " = ?",
                new String[] { String.valueOf(id) });
    }


    //select treatment
    public Treatments getTreatment(long id){
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + Treatments.TABLE_NAME + " WHERE "
                + Treatments._ID + " = " + id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Treatments treatment=new Treatments();
        treatment.setId(c.getInt(c.getColumnIndex(Treatments._ID)));
        treatment.setName((c.getString(c.getColumnIndex(Treatments.COLUMN_TREATMENT_NAME))));
        treatment.setStart_date((c.getString(c.getColumnIndex(Treatments.COLUMN_TREATMENT_START_DATE))));
        treatment.setEnd_date((c.getString(c.getColumnIndex(Treatments.COLUMN_TREATMENT_END_DATE))));
        treatment.setState_id((c.getInt(c.getColumnIndex(Treatments.COLUMN_TREATMENT_STATE_ID))));
        treatment.setDescription((c.getString(c.getColumnIndex(Treatments.COLUMN_TREATMENT_DESCRIPTION))));

        return treatment;
    }

    //get all medicines
    public List<Treatments> getAllTreatments(){
        List<Treatments> treatmentsList=new ArrayList<>();
        String selectQuery="SELECT * FROM "+ Treatments.TABLE_NAME;

        Log.e(LOG,selectQuery);

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor c= db.rawQuery(selectQuery,null);

        //looping through all rows and adding to list
        if(c.moveToFirst()){
            do{
                Treatments treatment=new Treatments();
                treatment.setId(c.getInt(c.getColumnIndex(Treatments._ID)));
                treatment.setName((c.getString(c.getColumnIndex(Treatments.COLUMN_TREATMENT_NAME))));
                treatment.setStart_date((c.getString(c.getColumnIndex(Treatments.COLUMN_TREATMENT_START_DATE))));
                treatment.setEnd_date((c.getString(c.getColumnIndex(Treatments.COLUMN_TREATMENT_END_DATE))));
                treatment.setState_id((c.getInt(c.getColumnIndex(Treatments.COLUMN_TREATMENT_STATE_ID))));
                treatment.setDescription((c.getString(c.getColumnIndex(Treatments.COLUMN_TREATMENT_DESCRIPTION))));
                treatmentsList.add(treatment);
            }while (c.moveToNext());
        }
        return treatmentsList;
    }

    public int updateTreatment(Treatments treatment) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Treatments.COLUMN_TREATMENT_NAME, treatment.getName());
        values.put(Treatments.COLUMN_TREATMENT_START_DATE,treatment.getStart_date());
        values.put(Treatments.COLUMN_TREATMENT_END_DATE,treatment.getEnd_date());
        values.put(Treatments.COLUMN_TREATMENT_STATE_ID,treatment.getState_id());
        values.put(Treatments.COLUMN_TREATMENT_DESCRIPTION,treatment.getDescription());

        // updating row
        return db.update(Treatments.TABLE_NAME, values, Treatments._ID+ " = ?",
                new String[]{String.valueOf(treatment.getId())});
    }

    //--------------------------------------STATE OF TREATMENT---------------------------
    //insert state
    public long insertState(String name){
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();
        TreatmentState ts=new TreatmentState(name);

        ContentValues values = new ContentValues();

        values.put(TreatmentState.COLUMN_STATE_NAME,ts.getName());

        // insert row
        long f1 = db.insert(TreatmentState.TABLE_NAME, null, values);
        db.close();

        return f1;
    }

    //delete State
    public void deleteState(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TreatmentState.TABLE_NAME, TreatmentState._ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    //select state
    public TreatmentState getStates(long id){
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TreatmentState.TABLE_NAME + " WHERE "
                + TreatmentState._ID + " = " + id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        TreatmentState state=new TreatmentState();
        state.setId(c.getInt(c.getColumnIndex(TreatmentState._ID)));
        state.setName((c.getString(c.getColumnIndex(TreatmentState.COLUMN_STATE_NAME))));

        return state;
    }

    //get all states
    public List<TreatmentState> getAllStates(){
        List<TreatmentState> states=new ArrayList<>();
        String selectQuery="SELECT * FROM "+ TreatmentState.TABLE_NAME;

        Log.e(LOG,selectQuery);

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor c= db.rawQuery(selectQuery,null);

        //looping through all rows and adding to list
        if(c.moveToFirst()){
            do{
                TreatmentState state=new TreatmentState();
                state.setId(c.getInt(c.getColumnIndex(TreatmentState._ID)));
                state.setName((c.getString(c.getColumnIndex(TreatmentState.COLUMN_STATE_NAME))));
                states.add(state);
            }while (c.moveToNext());
        }
        return states;
    }

    public int updateState(TreatmentState state) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TreatmentState.COLUMN_STATE_NAME, state.getName());

        // updating row
        return db.update(TreatmentState.TABLE_NAME, values, TreatmentState._ID+ " = ?",
                new String[]{String.valueOf(state.getId())});
    }

    //--------------------------------------SCHEDULE---------------------------
    //insert schedule
    public long insertSchedule(String time){
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();
        Schedule schedule=new Schedule();
        schedule.setHour(time);

        ContentValues values = new ContentValues();

        values.put(Schedule.COLUMN_HOUR,schedule.getHour());

        // insert row
        long f1 = db.insert(Schedule.TABLE_NAME, null, values);
        db.close();

        return f1;
    }

    //delete schedule
    public void deleteSchedule(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Schedule.TABLE_NAME, Schedule._ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    //select schedule
    public Schedule getSchedule(long id){
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + Schedule.TABLE_NAME + " WHERE "
                + Schedule._ID + " = " + id;
        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Schedule schedule=new Schedule();
        schedule.setId(c.getInt(c.getColumnIndex(Schedule._ID)));
        schedule.setHour((c.getString(c.getColumnIndex(Schedule.COLUMN_HOUR))));

        return schedule;
    }

    //get all schedules
    public List<Schedule> getAllSchedule(){
        List<Schedule> schedules=new ArrayList<>();
        String selectQuery="SELECT * FROM "+ Schedule.TABLE_NAME;

        Log.e(LOG,selectQuery);

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor c= db.rawQuery(selectQuery,null);

        //looping through all rows and adding to list
        if(c.moveToFirst()){
            do{
                Schedule schedule=new Schedule();
                schedule.setId(c.getInt(c.getColumnIndex(Schedule._ID)));
                schedule.setHour((c.getString(c.getColumnIndex(Schedule.COLUMN_HOUR))));
                schedules.add(schedule);
            }while (c.moveToNext());
        }
        return schedules;
    }

    public int updateSchedule(Schedule schedule) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Schedule.COLUMN_HOUR, schedule.getHour());

        // updating row
        return db.update(Schedule.TABLE_NAME, values, Schedule._ID+ " = ?",
                new String[]{String.valueOf(schedule.getId())});
    }

    //--------------------------------------REMINDER---------------------------
    //set reminder
    public long setReminder(int treatment_id, int medicine_id, int times, int frequency_id, int schedule_id){
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();
        Reminder reminder=new Reminder(treatment_id,medicine_id,times,frequency_id,schedule_id);

        ContentValues values = new ContentValues();

        values.put(Reminder.COLUMN_TREATMENT_ID,reminder.getTreatment_id());
        values.put(Reminder.COLUMN_MEDICINE_ID,reminder.getMedicine_id());
        values.put(Reminder.COLUMN_FREQUENCY_ID,reminder.getFrequency_id());
        values.put(Reminder.COLUMN_SCHEDULE_ID,reminder.getSchedule_id());
        values.put(Reminder.COLUMN_TIMES,reminder.getTimes());

        // insert row
        long f1 = db.insert(Reminder.TABLE_NAME, null, values);
        db.close();

        return f1;
    }

    //delete reminder
    public void deleteReminder(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Reminder.TABLE_NAME, Reminder.COLUMN_MEDICINE_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    //select reminder
    public Reminder getReminder(long id){
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + Reminder.TABLE_NAME + " WHERE "
                + Reminder.COLUMN_MEDICINE_ID+ " = " + id;
        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Reminder reminder=new Reminder();
        reminder.setMedicine_id((c.getInt(c.getColumnIndex(Reminder.COLUMN_MEDICINE_ID))));
        reminder.setTreatment_id((c.getInt(c.getColumnIndex(Reminder.COLUMN_TREATMENT_ID))));
        reminder.setFrequency_id((c.getInt(c.getColumnIndex(Reminder.COLUMN_FREQUENCY_ID))));
        reminder.setSchedule_id((c.getInt(c.getColumnIndex(Reminder.COLUMN_SCHEDULE_ID))));
        reminder.setTimes((c.getInt(c.getColumnIndex(Reminder.COLUMN_TIMES))));

        return reminder;
    }

    //get all reminders
    public List<Reminder> getAllReminder(){
        List<Reminder> reminders=new ArrayList<>();
        String selectQuery="SELECT * FROM "+ Reminder.TABLE_NAME;

        Log.e(LOG,selectQuery);

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor c= db.rawQuery(selectQuery,null);

        //looping through all rows and adding to list
        if(c.moveToFirst()){
            do{
                Reminder reminder=new Reminder();
                reminder.setMedicine_id((c.getInt(c.getColumnIndex(Reminder.COLUMN_MEDICINE_ID))));
                reminder.setTreatment_id((c.getInt(c.getColumnIndex(Reminder.COLUMN_TREATMENT_ID))));
                reminder.setFrequency_id((c.getInt(c.getColumnIndex(Reminder.COLUMN_FREQUENCY_ID))));
                reminder.setSchedule_id((c.getInt(c.getColumnIndex(Reminder.COLUMN_SCHEDULE_ID))));
                reminder.setTimes((c.getInt(c.getColumnIndex(Reminder.COLUMN_TIMES))));

                reminders.add(reminder);
            }while (c.moveToNext());
        }
        return reminders;
    }

    public int updateReminder(Reminder reminder) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Reminder.COLUMN_TREATMENT_ID, reminder.getTreatment_id());
        values.put(Reminder.COLUMN_MEDICINE_ID, reminder.getMedicine_id());
        values.put(Reminder.COLUMN_SCHEDULE_ID, reminder.getSchedule_id());
        values.put(Reminder.COLUMN_FREQUENCY_ID, reminder.getFrequency_id());
        values.put(Reminder.COLUMN_TIMES, reminder.getTimes());


        // updating row
        return db.update(Reminder.TABLE_NAME, values, Reminder.COLUMN_MEDICINE_ID+ " = ?",
                new String[]{String.valueOf(reminder.getMedicine_id())});
    }

}
