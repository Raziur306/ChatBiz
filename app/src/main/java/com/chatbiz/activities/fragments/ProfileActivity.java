package com.chatbiz.activities.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.chatbiz.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ProfileActivity extends Fragment {
    private TextView userName,userEmail,userAbout,userNumber;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    de.hdodenhof.circleimageview.CircleImageView profilePic;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_profile,container,false);
        initView(view);
        fetchData(mAuth.getCurrentUser().getUid());


        return view;
    }

    private void fetchData(String uid) {
        firestore.collection("users").document(uid).get().addOnCompleteListener(task->{
            if(task.isSuccessful() && task.getResult()!=null){
                DocumentSnapshot querySnapshot = task.getResult();
                userName.setText(querySnapshot.getString("name"));
                userEmail.setText(querySnapshot.getString("email"));
                userAbout.setText(querySnapshot.getString("about"));
                userNumber.setText(querySnapshot.getString("phone"));
                Glide.with(getContext()).load(querySnapshot.getString("profile")).into(profilePic);
            }
        });
    }

    private void initView(View view) {
        userName = view.findViewById(R.id.userName);
        userEmail = view.findViewById(R.id.userEmail);
        userAbout = view.findViewById(R.id.userAbout);
        userNumber = view.findViewById(R.id.userNumber);
        profilePic = view.findViewById(R.id.profile_image);
    }
}