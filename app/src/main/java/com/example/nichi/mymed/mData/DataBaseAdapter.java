package com.example.nichi.mymed.mData;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.nichi.mymed.mData.model.Frequency;
import com.example.nichi.mymed.mData.model.Medicine;
import com.example.nichi.mymed.mData.model.Reminder;
import com.example.nichi.mymed.mData.model.Schedule;
import com.example.nichi.mymed.mData.model.TreatmentState;
import com.example.nichi.mymed.mData.model.Treatments;

import java.util.ArrayList;

import static com.example.nichi.mymed.mData.model.TreatmentState.TABLE_NAME;

public class DataBaseAdapter {
    Context c;
    SQLiteDatabase db;
    DatabaseHelper helper;
    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    public DataBaseAdapter(Context c) {
        this.c = c;
        helper = new DatabaseHelper(c);
    }
//
//    public boolean saveMedicine(Medicine medicine) {
//        try {
//            db = helper.getWritableDatabase();
//
//            ContentValues cv = new ContentValues();
//            cv.put(Medicine.COLUMN_MEDICINE_NAME, medicine.getName());
//            cv.put(Medicine.COLUMN_MEDICINE_QUANTITY, medicine.getQuantity());
//
//            long result = db.insert(Medicine.TABLE_NAME, Medicine._ID, cv);
//            if (result > 0) {
//                return true;
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            helper.close();
//        }
//
//        return false;
//    }
//
//    public ArrayList<Medicine> retrieveMedicines() {
//        ArrayList<Medicine> medicines=new ArrayList<>();
//
//        try {
//            db = helper.getWritableDatabase();
//
//            Cursor c=db.rawQuery("SELECT * FROM "+Medicine.TABLE_NAME,null);
//
//            Medicine s;
//            medicines.clear();
//
//            while (c.moveToNext())
//            {
//                String medicine_name=c.getString(1);
//                int medicine_quantity=c.getInt(2);
//
//                s=new Medicine();
//                s.setName(medicine_name);
//                s.setQuantity(medicine_quantity);
//
//                medicines.add(s);
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            helper.close();
//        }
//
//        return medicines;
//    }


    //--------------------------------------Medicines---------------------------
    //insert a Medicine
    public boolean insertMedicine(Medicine med){
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Medicine.COLUMN_MEDICINE_NAME, med.getName());
        values.put(Medicine.COLUMN_MEDICINE_LIFETIME, med.getLifetime());
        values.put(Medicine.COLUMN_MEDICINE_QUANTITY, med.getQuantity());
        values.put(Medicine.COLUMN_MEDICINE_COMMENTS, med.getComments());

        // insert row
        long id = db.insert(Medicine.TABLE_NAME, null, values);

        Log.d("id", "a" + ((int)id));

        if(id==-1) return false;
        else return true;
    }

    //delete medicine
    public void deleteMedicine(long id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(Medicine.TABLE_NAME, Medicine._ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    //get one medicine
    public Medicine getMedicine(long id){
        // get readable database as we are not inserting anything
        SQLiteDatabase db = helper.getReadableDatabase();
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
        medicine.setQuantity((c.getString(c.getColumnIndex(Medicine.COLUMN_MEDICINE_QUANTITY))));
        medicine.setComments((c.getString(c.getColumnIndex(Medicine.COLUMN_MEDICINE_COMMENTS))));

        return medicine;
    }
    //get all medicines
    public Cursor getAllMedicines(){
        db = helper.getWritableDatabase();
        Cursor c=db.rawQuery("SELECT * FROM "+ Medicine.TABLE_NAME,null);
        return c;
    }

    public int getNofMedicines() {
        SQLiteDatabase db = helper.getWritableDatabase();
        return (int)DatabaseUtils.queryNumEntries(db,Medicine.TABLE_NAME);
    }

    public int updateMedicines(Medicine medicine) {
        SQLiteDatabase db = helper.getWritableDatabase();

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
        SQLiteDatabase db = helper.getWritableDatabase();
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
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(Frequency.TABLE_NAME, Frequency._ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    //select frequency
    public Frequency getFrequency(long id){
        // get readable database as we are not inserting anything
        SQLiteDatabase db = helper.getReadableDatabase();
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
    public ArrayList<Frequency> getAllFrequencies(){
        ArrayList<Frequency> frequencyList =new ArrayList<>();
        String selectQuery="SELECT * FROM "+ Frequency.TABLE_NAME;

        Log.e(LOG,selectQuery);

        SQLiteDatabase db=helper.getReadableDatabase();
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
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Frequency.COLUMN_FREQUENCY, frequency.getName());

        // updating row
        return db.update(Frequency.TABLE_NAME, values, Frequency._ID+ " = ?",
                new String[]{String.valueOf(frequency.getId())});
    }

    //--------------------------------------Treatment---------------------------
    //insert Treatment
    public long insertTreatment(Treatments treatment){
        // get writable database as we want to write data
        SQLiteDatabase db = helper.getWritableDatabase();
        //Treatments treatment=new Treatments(name,startdate,endate,state,comments);

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
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(Treatments.TABLE_NAME, Treatments._ID + " = ?",
                new String[] { String.valueOf(id) });
    }


    //select treatment
    public Treatments getTreatment(long id){
        // get readable database as we are not inserting anything
        SQLiteDatabase db = helper.getReadableDatabase();
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
        treatment.setState_id((c.getString(c.getColumnIndex(Treatments.COLUMN_TREATMENT_STATE_ID))));
        treatment.setDescription((c.getString(c.getColumnIndex(Treatments.COLUMN_TREATMENT_DESCRIPTION))));

        return treatment;
    }

    public Cursor getAllTreatments(){
        db = helper.getWritableDatabase();
        Cursor c=db.rawQuery("SELECT * FROM "+ Treatments.TABLE_NAME,null);
        return c;
    }


    //get all medicines
    //public ArrayList<Treatments> getAllTreatments(){
    //    ArrayList<Treatments> treatmentsList=new ArrayList<>();
    //    String selectQuery="SELECT * FROM "+ Treatments.TABLE_NAME;

    //    Log.e(LOG,selectQuery);

    //    SQLiteDatabase db=helper.getReadableDatabase();
    //    Cursor c= db.rawQuery(selectQuery,null);

        //looping through all rows and adding to list
    //    if(c.moveToFirst()){
    //            treatment.setName((c.getString(c.getColumnIndex(Treatments.COLUMN_TREATMENT_NAME))));
    //            treatment.setStart_date((c.getString(c.getColumnIndex(Treatments.COLUMN_TREATMENT_START_DATE))));
    //            treatment.setEnd_date((c.getString(c.getColumnIndex(Treatments.COLUMN_TREATMENT_END_DATE))));
    //            treatment.setState_id((c.getString(c.getColumnIndex(Treatments.COLUMN_TREATMENT_STATE_ID))));
    //            treatment.setDescription((c.getString(c.getColumnIndex(Treatments.COLUMN_TREATMENT_DESCRIPTION))));
    //            treatmentsList.add(treatment);
    //        }while (c.moveToNext());
    //    }
    //    return treatmentsList;
    //}

    public int updateTreatment(Treatments treatment) {
        SQLiteDatabase db = helper.getWritableDatabase();

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
        SQLiteDatabase db = helper.getWritableDatabase();
        TreatmentState ts=new TreatmentState(name);

        ContentValues values = new ContentValues();

        values.put(TreatmentState.COLUMN_STATE_NAME,ts.getName());

        // insert row
        long f1 = db.insert(TABLE_NAME, null, values);
        db.close();

        return f1;
    }

    //delete State
    public void deleteState(long id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(TABLE_NAME, TreatmentState._ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    //select state
    public TreatmentState getStates(long id){
        // get readable database as we are not inserting anything
        SQLiteDatabase db = helper.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE "
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
    public ArrayList<TreatmentState> getAllStates(){
        ArrayList<TreatmentState> states=new ArrayList<>();
        String selectQuery="SELECT * FROM "+ TABLE_NAME;

        Log.e(LOG,selectQuery);

        SQLiteDatabase db=helper.getReadableDatabase();
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
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TreatmentState.COLUMN_STATE_NAME, state.getName());

        // updating row
        return db.update(TABLE_NAME, values, TreatmentState._ID+ " = ?",
                new String[]{String.valueOf(state.getId())});
    }

    //--------------------------------------SCHEDULE---------------------------
    //insert schedule
    public long insertSchedule(String time){
        // get writable database as we want to write data
        SQLiteDatabase db = helper.getWritableDatabase();
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
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(Schedule.TABLE_NAME, Schedule._ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    //select schedule
    public Schedule getSchedule(long id){
        // get readable database as we are not inserting anything
        SQLiteDatabase db = helper.getReadableDatabase();

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
    public ArrayList<Schedule> getAllSchedule(){
        ArrayList<Schedule> schedules=new ArrayList<>();
        String selectQuery="SELECT * FROM "+ Schedule.TABLE_NAME;

        Log.e(LOG,selectQuery);

        SQLiteDatabase db=helper.getReadableDatabase();
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
        SQLiteDatabase db = helper.getWritableDatabase();

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
        SQLiteDatabase db = helper.getWritableDatabase();
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
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(Reminder.TABLE_NAME, Reminder.COLUMN_MEDICINE_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    //select reminder
    public Reminder getReminder(long id){
        // get readable database as we are not inserting anything
        SQLiteDatabase db = helper.getReadableDatabase();

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
    public ArrayList<Reminder> getAllReminder(){
        ArrayList<Reminder> reminders=new ArrayList<>();
        String selectQuery="SELECT * FROM "+ Reminder.TABLE_NAME;

        Log.e(LOG,selectQuery);

        SQLiteDatabase db=helper.getReadableDatabase();
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
        SQLiteDatabase db = helper.getWritableDatabase();

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
