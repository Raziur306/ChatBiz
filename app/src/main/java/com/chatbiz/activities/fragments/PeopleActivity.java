package com.chatbiz.activities.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chatbiz.R;
import com.chatbiz.adapters.PeopleAdapter;
import com.chatbiz.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class PeopleActivity extends Fragment {
    ProgressBar progressBar;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    RecyclerView peopleRecycler;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_people, container, false);
        initView(view);
        getUsers();
        return view;
    }


    private void getUsers() {
        progressBar.setVisibility(View.VISIBLE);
        firestore.collection("users").get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                ArrayList<User> users = new ArrayList<>();
                for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                    if (queryDocumentSnapshot.getId().equals(mAuth.getCurrentUser().getUid())) {
                        continue;
                    } else {
                        User user = new User();
                        user.setEmail(queryDocumentSnapshot.getString("email"));
                        user.setName(queryDocumentSnapshot.getString("name"));
                        user.setToken(queryDocumentSnapshot.getString("profileId"));
                        user.setPhotoLink(queryDocumentSnapshot.getString("profile"));
                        users.add(user);
                    }
                }
                if(users.size()>0)
                {
                    PeopleAdapter peopleAdapter = new PeopleAdapter(getContext(),users);
                    LinearLayoutManager llm = new LinearLayoutManager(getContext());
                    llm.setOrientation(LinearLayoutManager.VERTICAL);
                    peopleRecycler.setLayoutManager(llm);
                    peopleRecycler.setAdapter(peopleAdapter);
                    progressBar.setVisibility(View.GONE);
                }

            }

        });


    }

    private void initView(View view) {
        progressBar = view.findViewById(R.id.peopleProgressBar);
        peopleRecycler=view.findViewById(R.id.peopleRecycelrView);
    }
}