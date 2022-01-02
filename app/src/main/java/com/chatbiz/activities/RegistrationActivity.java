package com.chatbiz.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chatbiz.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {
    private EditText regEmail, regPass, regConPass;
    private  Button signUpBtn;
    private TextView alertMsg;
    private TextView regName;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
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
        String conPass = regConPass.getText().toString();
        if (email.isEmpty() || pass.length() < 8 || name.length() < 3 || conPass.isEmpty() || !pass.equals(conPass)) {
            alertMsg.setVisibility(View.VISIBLE);
        } else if (!emailPatternValidation.matcher(email).find()) {
            alertMsg.setVisibility(View.VISIBLE);
        } else {
            registration(email, pass, name);
        }


    }

    private void registration(String email, String pass, String name) {
        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this, task -> {
            if (task.isComplete()) {
                setUserData(name, email, mAuth.getUid());
            } else {
                Toast.makeText(RegistrationActivity.this, "Server Communication Failed", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void setUserData(String name, String email, String uid) {
        HashMap<String, Object> user = new HashMap<>();
        user.put("name", name);
        user.put("email", email);
        user.put("profileId",mAuth.getCurrentUser().getUid());
        user.put("about","Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.");
        user.put("phone","+880 1XXXXXXXXX");
        user.put("profile", "https://firebasestorage.googleapis.com/v0/b/chatbiz-d29db.appspot.com/o/prof_img.png?alt=media&token=a7cf21e3-5250-4a7d-8fc4-f8535a095abc");
        firestore.collection("users").document(uid).set(user).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(RegistrationActivity.this, "Server Communication Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void intView() {
        regName = findViewById(R.id.regName);
        regEmail = findViewById(R.id.regEmail);
        regPass = findViewById(R.id.regPass);
        signUpBtn = findViewById(R.id.signUpBtn);
        alertMsg = findViewById(R.id.errorMsg);
        regConPass = findViewById(R.id.regConPass);
    }
}