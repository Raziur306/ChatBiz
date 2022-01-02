package com.chatbiz.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chatbiz.R;
import com.chatbiz.adapters.ChatAdapter;
import com.chatbiz.model.ChatMessage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private String receiverId;
    private EditText messageSenderBox;
    private FrameLayout sendMsgBtn;
    private String email;
    private ProgressBar chatsLoadingProgress;
    private String name;
    private String receiverPhotoLink;
    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private List<ChatMessage> chatMessageList;
    private ChatAdapter chatAdapter;
    private de.hdodenhof.circleimageview.CircleImageView profilePhoto;
    private TextView chatWithName, chatWithStatus;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase firebaseDatabase;
    private RecyclerView showChats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Bundle bundle = getIntent().getBundleExtra("user");
        receiverId = bundle.getString("uid");
        email = bundle.getString("email");
        name = bundle.getString("name");
        receiverPhotoLink = bundle.getString("photoLink");
        initView();
        chatWithName.setText(name);
        Glide.with(this).load(receiverPhotoLink).into(profilePhoto);
        chatWithStatus.setText("Active");


        sendMsgBtn.setOnClickListener(v -> {
            if (!messageSenderBox.getText().toString().trim().isEmpty()) {
                ReadMessage();
                sendMessage();
            }
        });
        ReadMessage();


    }

    private void sendMessage() {
        HashMap<String, Object> msger = new HashMap<>();
        msger.put("senderId", mAuth.getCurrentUser().getUid());
        msger.put("receiverId", receiverId);
        msger.put("message", messageSenderBox.getText().toString().trim());
        messageSenderBox.setText("");
        msger.put("timeStamp", java.util.Calendar.getInstance().getTime());
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference root = firebaseDatabase.getReference("chats");

        root.push().setValue(msger);

    }

    private void ReadMessage() {

        chatMessageList = new ArrayList<>();

        DatabaseReference root = firebaseDatabase.getReference("chats");
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatMessageList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class);
                    if (chatMessage.getReceiverId().equals(receiverId) && chatMessage.getSenderId().equals(mAuth.getCurrentUser().getUid())
                            ||
                            chatMessage.getReceiverId().equals(mAuth.getCurrentUser().getUid()) && chatMessage.getSenderId().equals(receiverId)) {
                        chatMessageList.add(chatMessage);
                    }

                }
                chatAdapter = new ChatAdapter(ChatActivity.this, chatMessageList, receiverPhotoLink, mAuth.getCurrentUser().getUid());
                showChats.setAdapter(chatAdapter);
                Log.d("Chat list", String.valueOf(chatMessageList.size()));
                chatsLoadingProgress.setVisibility(View.GONE);
                //scroll
                showChats.smoothScrollToPosition(chatAdapter.getItemCount());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    private void initView() {
        profilePhoto = findViewById(R.id.profile_image);
        chatWithName = findViewById(R.id.chatWithName);
        chatWithStatus = findViewById(R.id.chatWithStatus);
        firebaseDatabase = FirebaseDatabase.getInstance();
        sendMsgBtn = findViewById(R.id.sendMessage);
        messageSenderBox = findViewById(R.id.messageSenderBox);
        messageSenderBox.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        showChats = findViewById(R.id.showChatsRecyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        showChats.setLayoutManager(llm);
        chatsLoadingProgress = findViewById(R.id.chatsLoadingPorcessbar);


    }
}