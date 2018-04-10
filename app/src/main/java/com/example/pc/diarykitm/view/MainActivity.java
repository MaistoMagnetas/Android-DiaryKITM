package com.example.pc.diarykitm.view;

import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pc.diarykitm.R;
import com.example.pc.diarykitm.view.model.DatabaseHelper;
import com.example.pc.diarykitm.view.model.User;
import com.example.pc.diarykitm.view.model.controller.Validation;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin, btnRegister;
    private ImageView loginImage;
    private EditText etLoginUsername, etLoginPassword;
    private DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize items on startup
        initItems();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getAllEntryData(etLoginUsername.getText().toString(),etLoginPassword.getText().toString())){
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this, "Wrong user name or password", Toast.LENGTH_SHORT).show();
                }
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


    //Method takes ET field as inputs. and loops DB for registered users. If user found returns boolean.
    public boolean getAllEntryData(String username,String password){
        boolean registeredUser = false;
        User user = new User();
        List<User> useriai = new ArrayList<User>();
        myDb = new DatabaseHelper(getApplicationContext());
        Cursor res = myDb.getUserData();
        if(res.getCount() == 0){
            Toast.makeText(this, "Sorry. No data found.", Toast.LENGTH_SHORT).show();
        }else{
            while (res.moveToNext()){
                user.setUsername(res.getString(1));
                user.setPassword(res.getString(3));
                useriai.add(user);
            }
        }
        for(User useris: useriai){
            if(useris.getUsername().toString().equals(username) && useris.getPassword().toString().equals(password)){
                registeredUser = true;
                return registeredUser;
            }
        }
        return registeredUser;

    }
}
