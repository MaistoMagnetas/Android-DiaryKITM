package com.example.pc.diarykitm.view;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pc.diarykitm.R;
import com.example.pc.diarykitm.view.model.DatabaseHelper;

import java.security.KeyStore;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EntryActivity extends AppCompatActivity{


    private DatabaseHelper myDb;
    private Button btnEntrySubmit, btnEntryViewAll, btnEntryDelete, btnEntryUpdate;
    private EditText etEntryTitle, etEntryDescription;
    private Spinner entrySpinner;
    private CheckBox entryCB1, entryCB2, entryCB3;
    private RadioButton entryRBFast,entryRBSlow,entryRBNormal;

    String firstTitle = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        //Items init
        initItems();

        //Get intent data from login activity - clicked on list view item (populate textviews)
        Intent intent = getIntent();
        boolean value1 = intent.getBooleanExtra("value",false);
        final String title = intent.getStringExtra("title");
        final String description = intent.getStringExtra("description");
        final String mood = intent.getStringExtra("mood");
        final String pace = intent.getStringExtra("pace");
        final String type = intent.getStringExtra("type");


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
                Intent intent = new Intent(EntryActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        btnEntryUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myDb.updateJournalEntry(firstTitle,etEntryTitle.getText().toString(),etEntryDescription.getText().toString(),
                        entrySpinner.getSelectedItem().toString(),checkedRadioButton(),checkedCheckBox());
                Intent intent = new Intent(EntryActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        btnEntryDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDb.deleteJournalEntry(title,description);
                Intent intent = new Intent(EntryActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        //Looks if came from listview on click or just pressed new Entry
        //Acoordingly fills required data to TextVIews
        if(value1 == false){
            btnEntryDelete.setEnabled(false);
            btnEntryUpdate.setEnabled(false);
        }else{
            btnEntryDelete.setEnabled(true);
            btnEntryUpdate.setEnabled(true);
            etEntryTitle.setText(title);
            etEntryDescription.setText(description);
            entrySpinner.setSelection(selectedSpinner(mood));
            selectedRadioButton(pace);
            selectedCheckBox(type);
            firstTitle = etEntryTitle.getText().toString();
        }

    }

    //Method for CheckBox
    private void selectedCheckBox(String cb){
        if(cb.contains("new")){
            entryCB1.setChecked(true);
        }
        if(cb.contains("people")){
            entryCB2.setChecked(true);
        }
        if(cb.contains("goals")){
            entryCB3.setChecked(true);
        }

    }

    //Method for  Radio butotn from intent
    private void selectedRadioButton(String selectedRB ){
        if(selectedRB.equals("Normal")){
            entryRBNormal.setChecked(true);
        }else if(selectedRB.equals("Fast")){
            entryRBFast.setChecked(true);
        }else{
            entryRBSlow.setChecked(true);
        }
    }

    //Method for intent String to put into Spinner
    private int selectedSpinner(String chosenSpinnerItem){
        int i = 0;
        String[] spinnerItems = {"Happy","Angry","Sad","Lonely","Exhausted","Anxious","Pumped","Loved"};
        for(String spinnerItem: spinnerItems){
            if(chosenSpinnerItem.equals(spinnerItems[i])){
                return i;
            }
            i++;
        }
        return i;
    }


    //Method for initialization of items at start
    private void initItems(){
        myDb = new DatabaseHelper(this);
        btnEntrySubmit = (Button) findViewById(R.id.btnEntrySubmit);
        btnEntryViewAll = (Button) findViewById(R.id.btnEntryViewAll);
        btnEntryDelete = (Button) findViewById(R.id.btnEntryDelete);
        btnEntryUpdate = (Button) findViewById(R.id.btnEntryUpdate);
        etEntryDescription = (EditText) findViewById(R.id.etEntryDescription);
        etEntryTitle = (EditText) findViewById(R.id.etEntryTitle);
        entrySpinner = (Spinner) findViewById(R.id.entrySpinner);
        entryCB1 = (CheckBox) findViewById(R.id.entryCB1);
        entryCB2 = (CheckBox) findViewById(R.id.entryCB2);
        entryCB3 = (CheckBox) findViewById(R.id.entryCB3);
        entryRBFast = (RadioButton) findViewById(R.id.entryRBFast);
        entryRBNormal = (RadioButton) findViewById(R.id.entryRBNormal);
        entryRBSlow = (RadioButton) findViewById(R.id.entryRBSlow);
    }

    //Method on button clicked adds dentry to DB
    private void addEntry(){
        DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
        String date = df.format(Calendar.getInstance().getTime());

        //RB value
        String pace = checkedRadioButton();
        //Spinner
        String spinner = entrySpinner.getSelectedItem().toString();
        //CB value
        String cbValue = checkedCheckBox();
        boolean isInserted = myDb.insertEntry(date,etEntryTitle.getText().toString(),
                etEntryDescription.getText().toString(),spinner,pace,cbValue);
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
            buffer.append("DESCRIPTION :" +res.getString(3)+"\n");
            buffer.append("MOOD:" +res.getString(4)+"\n");
            buffer.append("PACE:" +res.getString(5)+"\n");
            buffer.append("TYPE:" +res.getString(6)+"\n\n");
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

    //Method return a string of a checked radiobutton
    public String checkedRadioButton(){
        String pace = "";
        if(entryRBNormal.isChecked()){
            pace = "Normal";
        }else if(entryRBSlow.isChecked()){
            pace = "Slow";
        }else{
            pace = "Fast";
        }
        return pace;
    }

    public  String checkedCheckBox(){
        String checked = "";
        if(entryCB1.isChecked()){ //new keyword
            checked+="new,";
        }
        if(entryCB2.isChecked()){ //people keyword
            checked+="people,";
        }
        if(entryCB3.isChecked()){ // goals keyword
            checked+="goals";
        }
        return checked;
    }
}
