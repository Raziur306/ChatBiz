package com.chatbiz.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.MasterKeys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chatbiz.R;
import com.chatbiz.encrypter.Encryption;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private TextView log_to_reg, loginWarning;
    private EditText loginPass, loginEmail;
    private Button loginBtn;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private LinearLayout loginProcess;
    private CheckBox rememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

        log_to_reg.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
        });

        loginBtn.setOnClickListener(v -> {
            validation();
        });
    }

    private void validation() {
        String pass = loginPass.getText().toString();
        String email = loginEmail.getText().toString().trim();
        if (pass.isEmpty() || email.isEmpty()) {
            loginWarning.setVisibility(View.VISIBLE);
        } else {

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            loginProcess.setVisibility(View.VISIBLE);
            LogIn(pass, email, rememberMe.isChecked());

        }
    }

    private void LogIn(String pass, String email, boolean flag) {
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                loginSuccess(email,pass,flag);

            } else {
                loginWarning.setVisibility(View.VISIBLE);
                loginProcess.setVisibility(View.GONE);
            }
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        });
    }

    private void loginSuccess(String email, String pass, boolean flag) {
        if(flag)
        {
            Encryption e = new Encryption(LoginActivity.this,email,pass);
            e.encryptSave();
            loginProcess.setVisibility(View.GONE);
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();

        }
        else {
            loginProcess.setVisibility(View.GONE);
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

    private void initView() {
        log_to_reg = findViewById(R.id.log_to_reg);
        loginPass = findViewById(R.id.loginPass);
        loginEmail = findViewById(R.id.loginEmail);
        loginBtn = findViewById(R.id.loginBtn);
        loginWarning = findViewById(R.id.loginWarning);
        loginProcess = findViewById(R.id.loginProgress);
        rememberMe = findViewById(R.id.rememberMe);
    }
}