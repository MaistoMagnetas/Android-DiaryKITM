package com.example.pc.diarykitm.view;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pc.diarykitm.R;
import com.example.pc.diarykitm.view.model.DatabaseHelper;
import com.example.pc.diarykitm.view.model.JournalEntry;
import com.example.pc.diarykitm.view.model.User;
import com.example.pc.diarykitm.view.model.controller.CustomAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<JournalEntry> entries = new ArrayList<JournalEntry>();
    private Button btnEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnEntry = (Button) findViewById(R.id.btnLoginEntry);
        btnEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,EntryActivity.class);
                startActivity(intent);
            }
        });

        initItems();
        //List JournalEntry == to List with database data.
        entries = getAllEntriesToList();
        //Populate listView with DB items
        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), (ArrayList<JournalEntry>) entries);
        listView.setAdapter(customAdapter);

        //List view on click
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickOnListItem(position);
            }
        });

    }

    //Method for items initialization at start
    private void initItems(){
        listView = (ListView) findViewById(R.id.listView);

    }

    //Method to populate List<JournalEntries> with data from database - getAllEntries
    private ArrayList<JournalEntry> getAllEntriesToList(){
        ArrayList<JournalEntry> entries1 = new ArrayList<JournalEntry>();
        DatabaseHelper myDb = new DatabaseHelper(getApplicationContext());
        Cursor res = myDb.getEntryData();
        if(res.getCount() == 0){

        }else{
            while (res.moveToNext()){
                JournalEntry entry = new JournalEntry();
                entry.setDate(res.getString(1));
                entry.setTitle(res.getString(2));
                entry.setDescription(res.getString(3));
                entry.setMood(res.getString(4));
                entry.setPace(res.getString(5));
                entry.setType(res.getString(6));
                entries1.add(entry);
            }
        }
        return entries1;
    }

    private void clickOnListItem(int position){
        Intent intent = new Intent(LoginActivity.this,EntryActivity.class);
        intent.putExtra("value",true);
        intent.putExtra("title",entries.get(position).getTitle().toString());
        intent.putExtra("description",entries.get(position).getDescription().toString());
        intent.putExtra("mood",entries.get(position).getMood().toString());
        intent.putExtra("pace",entries.get(position).getPace().toString());
        intent.putExtra("type",entries.get(position).getType().toString());
        startActivity(intent);
    }

}
