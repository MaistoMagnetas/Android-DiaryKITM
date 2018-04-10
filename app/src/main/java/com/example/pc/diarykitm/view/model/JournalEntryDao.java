package com.example.pc.diarykitm.view.model;

import android.database.Cursor;

/**
 * Created by PC on 4/7/2018.
 */

public interface JournalEntryDao {

    //Get all entries data for listView
    Cursor getEntryData();
    //Add entry to database
    boolean insertEntry(String date,String title,String description, String mood,String pace, String type);
    //DELETE ENTRY
    void deleteJournalEntry(String title,String description);
    //UPDATE ENTRY
    boolean updateJournalEntry(String firstTitle, String title,String description, String mood, String pace, String type);
    //GET ID for update - not used
    Cursor getJournalId(String title,String description);

}
