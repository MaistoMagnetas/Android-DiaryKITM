package com.example.pc.diarykitm.view.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by PC on 4/7/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper{
    //DATABASE VARIABLE
    public static final String DATABASE_NAME = "diary.db";
    //USER TABLE FOR DATABASE
    public static final String TABLE_USER_NAME = "user_table";
    private static final String COL_1_USER = "ID";
    private static final String COL_2_USER = "USERNAME";
    private static final String COL_3_USER = "EMAIL";
    private static final String COL_4_USER = "PASSWORD";

    //ENTRY TABLE FOR DATABASE
    public static final String TABLE_ENTRY_NAME = "entry_table";
    private static final String COL_1_ENTRY = "ID";
    private static final String COL_2_ENTRY = "DATE";
    private static final String COL_3_ENTRY = "TITLE";
    private static final String COL_4_ENTRY = "DESCRIPTION";
    //TO-DO add more


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //CREATEs USER TABLE
//        String sql = "CREATE TABLE "+TABLE_USER_NAME+" (ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT," +
//                " EMAIL TEXT, PASSWORD TEXT";
//        db.execSQL(sql);

        //CREATES ENTRY TABLE
        String sql1 = "CREATE TABLE "+TABLE_ENTRY_NAME+" (ID INTEGER PRIMARY KEY AUTOINCREMENT, DATE TEXT," +
                " TITLE TEXT, DESCRIPTION TEXT)";
        db.execSQL(sql1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_USER_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_ENTRY_NAME);
        onCreate(db);


    }

    //CREATE and add entry to database
    public boolean insertEntry(String date,String title,String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2_ENTRY,date);
        contentValues.put(COL_3_ENTRY,title);
        contentValues.put(COL_4_ENTRY,description);
        long result = db.insert(TABLE_ENTRY_NAME,null,contentValues);
        if (result == -1){
            return false;
        }else{
            return true;
        }
    }

    //Method for getAllEntries
    public Cursor getEntryData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM "+TABLE_ENTRY_NAME;
        Cursor res = db.rawQuery(sql,null);
        return res;
    }
}
