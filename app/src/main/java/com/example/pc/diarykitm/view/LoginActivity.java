package com.example.pc.diarykitm.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.example.pc.diarykitm.R;
import com.example.pc.diarykitm.view.model.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {

    private GridView gridView;

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

    }

    //Method for items initialization at start
    private void initItems(){
        gridView = (GridView) findViewById(R.id.gridView);

    }
}
