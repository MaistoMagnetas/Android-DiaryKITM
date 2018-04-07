package com.example.pc.diarykitm.view;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pc.diarykitm.R;
import com.example.pc.diarykitm.view.model.DatabaseHelper;

import java.security.KeyStore;

public class EntryActivity extends AppCompatActivity{


    private DatabaseHelper myDb;
    private Button btnEntrySubmit, btnEntryViewAll;
    private EditText etEntryTitle, etEntryDescription, etEntryDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        //Items init
        initItems();


        //On button clicked adds data to DB and gets back to login or stays and show error
        btnEntrySubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEntry();
            }
        });

        //btnView all on clicked gets all DB entry data
        btnEntryViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllEntryData();
            }
        });


    }

    //Method for initialization of items at start
    private void initItems(){
        myDb = new DatabaseHelper(this);
        btnEntrySubmit = (Button) findViewById(R.id.btnEntrySubmit);
        btnEntryViewAll = (Button) findViewById(R.id.btnEntryViewAll);
        etEntryDate = (EditText) findViewById(R.id.etEntryDate);
        etEntryDescription = (EditText) findViewById(R.id.etEntryDescription);
        etEntryTitle = (EditText) findViewById(R.id.etEntryTitle);
    }

    //Method on button clicked adds dentry to DB
    private void addEntry(){
        boolean isInserted = myDb.insertEntry(etEntryDate.getText().toString(),etEntryTitle.getText().toString(),
                etEntryDescription.getText().toString());
        if(isInserted){
            Toast.makeText(EntryActivity.this, "Data inserted", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(EntryActivity.this,LoginActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(EntryActivity.this, "Data not inserted", Toast.LENGTH_SHORT).show();
        }
    }

    //Method get all entry data from DB
    public void getAllEntryData(){
        Cursor res = myDb.getEntryData();
        if(res.getCount() == 0){
            showAlertDialog("Error","No data found");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            buffer.append("ID :" +res.getString(0)+"\n");
            buffer.append("DATE :" +res.getString(1)+"\n");
            buffer.append("TITLE :" +res.getString(2)+"\n");
            buffer.append("DESCRIPTION :" +res.getString(3)+"\n\n");
        }
        showAlertDialog("DATA:",buffer.toString());
        Toast.makeText(this, buffer.toString(), Toast.LENGTH_SHORT).show();
    }

    //Method makes alertDialog window
    public void showAlertDialog(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
    }
}
