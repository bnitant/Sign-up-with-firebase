package com.example.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button loginBtn, goToSignupBtn;

    //CUSTOM TOAST
    Toast customToast;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        CUSTOM TOAST

        customToast = new Toast(LoginActivity.this);
        View view =getLayoutInflater().inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.custom_toast_container));

        customToast.setView(view);
        customToast.setDuration(Toast.LENGTH_LONG);
        customToast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 50);
        customToast.show();

//        FOR BUTTON CLICK
        loginBtn = findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(this);

        goToSignupBtn = findViewById(R.id.gotosignup_btn);
        goToSignupBtn.setOnClickListener(this);

//        FOR SETTING USERNAME AND PASSWORD AFTER SIGNING UP
        if(Objects.equals(getIntent().getStringExtra("From"), "Sign up")){

            Intent new_intent=getIntent();
            EditText user_name=findViewById(R.id.login_email_input);
            EditText pass_word=findViewById(R.id.login_password_input);
            user_name.setText(new_intent.getStringExtra("Username"));
            pass_word.setText(new_intent.getStringExtra("Password"));
        }
    }

    // FOR BTN CLICKED
    @Override
    public void onClick(View view) {

        int btnClicked = view.getId();

        if (btnClicked == R.id.gotosignup_btn){

            //  IF SIGNUP BTN IS CLICKED
            Intent intent = new Intent(this, SignupActivity.class);
            startActivity(intent);

        } else {

            // IF LOGIN BTN IS CLICKED
            if (verifyDetails()) {
                Toast.makeText(this, "Logged Up Successfully!!", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "Login Failed!!", Toast.LENGTH_SHORT).show();
            }
        }

    }

    // CHECKS IF DETAILS ARE CORRECT OR NOT
    private boolean verifyDetails(){
        return false;

    }

}