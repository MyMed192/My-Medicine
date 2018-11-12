package com.example.nichi.mymed.Data.model;

import java.util.Date;

public class Medicine {

        public final static String TABLE_NAME = "medicines";
        public final static String _ID = "id";
        public final static String COLUMN_MEDICINE_NAME ="name";
        public final static String COLUMN_MEDICINE_LIFETIME = "lifetime";
        public final static String COLUMN_MEDICINE_QUANTITY = "quantity";
        public final static String COLUMN_MEDICINE_COMMENTS = "comment";

        private int id;
        private String name;
        private Date lifetime;
        private int quantity;
        private String comments;

        public final static String CREATE_TABLE="CREATE TABLE " + Medicine.TABLE_NAME + " ("
                    + Medicine._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + Medicine.COLUMN_MEDICINE_NAME + " TEXT NOT NULL, "
                    + Medicine.COLUMN_MEDICINE_LIFETIME + " DATE, "
                    + Medicine.COLUMN_MEDICINE_QUANTITY + " INTEGER NOT NULL, "
                    + Medicine.COLUMN_MEDICINE_COMMENTS + " TEXT);";

    public Medicine(int id, String name, Date lifetime, int quantity, String comments) {
        this.id = id;
        this.name = name;
        this.lifetime = lifetime;
        this.quantity = quantity;
        this.comments = comments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Date getLifetime() {
        return lifetime;
    }

    public void setLifetime(Date lifetime) {
        this.lifetime = lifetime;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}

