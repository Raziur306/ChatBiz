package com.chatbiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {
    EditText regEmail, regPass;
    Button signUpBtn;
    TextView alertMsg;
    TextView regName;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final Pattern emailPatternValidation = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        intView();
        signUpBtn.setOnClickListener(v -> {
            validation();
        });

    }

    private void validation() {
        String email = regEmail.getText().toString().trim();
        String pass = regPass.getText().toString();
        String name = regName.getText().toString().trim();
        if(email.isEmpty() || pass.length()<8 || name.length()<3)
        {
            alertMsg.setVisibility(View.VISIBLE);
        }else if(!emailPatternValidation.matcher(email).find())
        {
            alertMsg.setVisibility(View.VISIBLE);
        }
        else{
            registration(email,pass,name);
        }


    }

    private void registration(String email, String pass, String name) {
        mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    String uid = mAuth.getUid();
                   // firestorePush(uid,);
                }
                else
                {
                    Toast.makeText(RegistrationActivity.this, "Server Communication Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void intView() {
        regName = findViewById(R.id.regName);
        regEmail = findViewById(R.id.regEmail);
        regPass = findViewById(R.id.regPass);
        signUpBtn = findViewById(R.id.signUpBtn);
        alertMsg = findViewById(R.id.errorMsg);
    }
}