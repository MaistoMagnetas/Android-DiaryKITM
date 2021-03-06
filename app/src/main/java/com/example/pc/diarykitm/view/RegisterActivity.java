package com.example.pc.diarykitm.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pc.diarykitm.R;
import com.example.pc.diarykitm.view.model.DatabaseHelper;
import com.example.pc.diarykitm.view.model.User;
import com.example.pc.diarykitm.view.model.controller.Validation;

public class RegisterActivity extends AppCompatActivity {

    private Button btnRegisterSubmit, btnRegisterCancel;
    private EditText etRegisterUsername, etRegisterEmail, etRegisterPassword, etRegisterPasswordRepeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Init items ar start up
        initItems();

        //Then clicked on cancel returns to mainActivity and no User is created
        btnRegisterCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToMain(); //Method for returning to main
            }
        });

        //Then clicked on Register:
        //1.return to main 2. Shows toast - user created. 3.Created user and adds it to Database
        btnRegisterSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Validation.isValidCredentials((etRegisterUsername.getText().toString()))){
                    etRegisterUsername.requestFocus();
                    etRegisterUsername.setError("Wrong username format");
                }else if(!Validation.isValidCredentials(etRegisterPassword.getText().toString())){
                    etRegisterPassword.requestFocus();
                    etRegisterPassword.setError("Wrong password format");
                }else if(!etRegisterPassword.getText().toString().equals(etRegisterPasswordRepeat.getText().toString())){
                    etRegisterPasswordRepeat.requestFocus();
                    etRegisterPasswordRepeat.setError("Password doesnt match");
                }else if(!Validation.isValidEmail(etRegisterEmail.getText().toString())){
                    etRegisterEmail.requestFocus();
                    etRegisterEmail.setError("Wrong email format");
                }else{ //Validation complete
                    DatabaseHelper myDb = new DatabaseHelper(getApplicationContext());
                    User user = new User();
                    user.setUsername(etRegisterUsername.getText().toString());
                    user.setEmail(etRegisterEmail.getText().toString());
                    user.setPassword(etRegisterPassword.getText().toString());
                    myDb.addUser(user);
                    returnToMain();
                    toastMessage("User created successfully");
                }
            }
        });


    }

    //Method creates intent for MainActivity
    private void returnToMain(){
        Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
        startActivity(intent);
    }

    //Method for creating items on startup
    private void initItems(){
        btnRegisterSubmit = (Button) findViewById(R.id.btnRegisterSubmit);
        btnRegisterCancel = (Button) findViewById(R.id.btnRegisterCancel);

        etRegisterUsername = (EditText) findViewById(R.id.etRegisterUsername);
        etRegisterEmail = (EditText) findViewById(R.id.etRegisterEmail);
        etRegisterPassword = (EditText) findViewById(R.id.etRegisterPassword);
        etRegisterPasswordRepeat = (EditText) findViewById(R.id.etRegisterPasswordRepeat);
    }

    //Method for Creating Toast more simply
    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
