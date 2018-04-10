package com.example.pc.diarykitm.view.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by PC on 4/7/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper implements UserDao, JournalEntryDao{
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
    private static final String COL_5_ENTRY = "MOOD";
    private static final String COL_6_ENTRY = "PACE";
    private static final String COL_7_ENTRY = "TYPE";
    //TO-DO add more


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //CREATEs USER TABLE
        String sql = "CREATE TABLE "+TABLE_USER_NAME+" (ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT," +
                " EMAIL TEXT, PASSWORD TEXT)";
        db.execSQL(sql);
        //CREATES ENTRY TABLE
        String sql1 = "CREATE TABLE "+TABLE_ENTRY_NAME+" (ID INTEGER PRIMARY KEY AUTOINCREMENT, DATE TEXT," +
                " TITLE TEXT, DESCRIPTION TEXT, MOOD TEXT, PACE TEXT, TYPE TEXT)";
        db.execSQL(sql1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_USER_NAME);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_ENTRY_NAME);
        onCreate(db);


    }


    //Method for user registration
    @Override
    public boolean addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2_USER,user.getUsername());
        contentValues.put(COL_3_USER,user.getEmail());
        contentValues.put(COL_4_USER,user.getPassword());
        long result = db.insert(TABLE_USER_NAME, null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    //Method to get all registered users
    @Override
    public Cursor getUserData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM "+TABLE_USER_NAME;
        Cursor res = db.rawQuery(sql,null);
        return res;
    }

    //CREATE and add entry to database
    @Override
    public boolean insertEntry(String date,String title,String description, String mood,String pace, String type){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2_ENTRY,date);
        contentValues.put(COL_3_ENTRY,title);
        contentValues.put(COL_4_ENTRY,description);
        contentValues.put(COL_5_ENTRY,mood);
        contentValues.put(COL_6_ENTRY,pace);
        contentValues.put(COL_7_ENTRY,type);
        long result = db.insert(TABLE_ENTRY_NAME,null,contentValues);
        if (result == -1){
            return false;
        }else{
            return true;
        }
    }

    //Method for getAllEntries
    @Override
    public Cursor getEntryData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM "+TABLE_ENTRY_NAME;
        Cursor res = db.rawQuery(sql,null);
        return res;
    }

    //Method to delete JournalEntry
    @Override
    public void deleteJournalEntry(String title,String description){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "DELETE FROM "+TABLE_ENTRY_NAME+" WHERE DESCRIPTION = '" +description+"' AND TITLE = '"+title+"'";
        db.execSQL(sql);
    }

    //Method to update JournalEntry
    @Override
    public boolean updateJournalEntry(String firstTitle, String title,String description, String mood, String pace, String type){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_3_ENTRY,title);
        contentValues.put(COL_4_ENTRY,description);
        contentValues.put(COL_5_ENTRY,mood);
        contentValues.put(COL_6_ENTRY,pace);
        contentValues.put(COL_7_ENTRY,type);
        db.update(TABLE_ENTRY_NAME,contentValues, "title = ?",new String[] {firstTitle});
        return true;
    }

    @Override
    public Cursor getJournalId(String title,String description){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT ID FROM "+TABLE_ENTRY_NAME+" WHERE title = '"+title+"' AND description = '"+description+"'";
        Cursor res = db.rawQuery(sql,null);
        return res;
    }
}
