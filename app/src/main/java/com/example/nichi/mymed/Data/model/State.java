package com.example.nichi.mymed.Data.model;

class State {
    public final static String TABLE_NAME = "state";

    public final static String _ID = "id";
//can be: In Progress
    //    Done
    public final static String COLUMN_STATE_NAME ="name";

    private int id;
    private String name;

    public final static String CREATE_TABLE="CREATE TABLE " + TABLE_NAME + " ("
            + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_STATE_NAME+ " TEXT NOT NULL" +
            ");";

    public State(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
