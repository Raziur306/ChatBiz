package com.chatbiz.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chatbiz.R;
import com.chatbiz.adapters.ChatAdapter;
import com.chatbiz.model.ChatMessage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private String uid;
    private String email;
    private String name;
    private String photoLink;
    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private List<ChatMessage> chatMessageList;
    private ChatAdapter chatAdapter;
    private de.hdodenhof.circleimageview.CircleImageView profilePhoto;
    private TextView chatWithName, chatWithStatus;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Bundle bundle = getIntent().getBundleExtra("user");
        uid = bundle.getString("uid");
        email = bundle.getString("email");
        name = bundle.getString("name");
        photoLink = bundle.getString("photoLink");
        initView();
        chatWithName.setText(name);
        Glide.with(this).load(photoLink).into(profilePhoto);
        chatWithStatus.setText("Active");

        chatAdapter = new ChatAdapter(chatMessageList,photoLink,mAuth.getUid());
        sendMessage();
    }

    private void sendMessage() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference root = firebaseDatabase.getReference("chat");



    }

    private void initView() {
        profilePhoto = findViewById(R.id.profile_image);
        chatWithName = findViewById(R.id.chatWithName);
        chatWithStatus = findViewById(R.id.chatWithStatus);
        firebaseDatabase = FirebaseDatabase.getInstance();
    }
}