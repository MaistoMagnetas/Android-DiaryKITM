package com.example.pc.diarykitm.view;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.pc.diarykitm.R;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin, btnRegister;
    private ImageView loginImage;
    private EditText etLoginUsername, etLoginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize items on startup
        initItems();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initItems(){
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        etLoginPassword = (EditText) findViewById(R.id.etLoginPassword);
        etLoginUsername = (EditText) findViewById(R.id.etLoginUsername);
    } // Method for initializing items on startup(Buttons,Edittext, etc)
}
