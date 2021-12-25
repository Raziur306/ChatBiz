package com.chatbiz.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chatbiz.R;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private TextView log_to_reg,loginWarning;
    private EditText loginPass, loginEmail;
    private Button loginBtn;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

        log_to_reg.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
        });

        loginBtn.setOnClickListener(v->{
            validation();
        });
    }

    private void validation() {
        String pass = loginPass.getText().toString();
        String email = loginEmail.getText().toString().trim();
        if(pass.isEmpty() || email.isEmpty())
        {
            loginWarning.setVisibility(View.VISIBLE);
        }
        else
        {
            LogIn(pass,email);
        }
    }

    private void LogIn(String pass, String email) {
        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(task->{
           if(task.isSuccessful())
           {
               startActivity(new Intent(LoginActivity.this,MainActivity.class));
               finish();
           }
           else
           {
               loginWarning.setVisibility(View.VISIBLE);
           }

        });
    }

    private void initView() {
        log_to_reg = findViewById(R.id.log_to_reg);
        loginPass = findViewById(R.id.loginPass);
        loginEmail = findViewById(R.id.loginEmail);
        loginBtn = findViewById(R.id.loginBtn);
        loginWarning = findViewById(R.id.loginWarning);
    }
}