package com.chatbiz.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;


import com.chatbiz.R;
import com.chatbiz.activities.fragments.CallActivity;
import com.chatbiz.activities.fragments.ChatsActivity;
import com.chatbiz.activities.fragments.FriendsActivity;
import com.chatbiz.activities.fragments.ProfileActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class
MainActivity extends AppCompatActivity {
    private FrameLayout fragmentViewer;
    private  FragmentTransaction transaction;
    private  final Fragment allFragment[] ={new ChatsActivity(),new FriendsActivity(),new CallActivity(),new ProfileActivity()};
    private int fIndex=0;
    private BottomNavigationView bottomNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentViewer = findViewById(R.id.fragmentViewer);
        bottomNav = findViewById(R.id.bottomNavigationView);


        transaction = getSupportFragmentManager().beginTransaction();
        if(fIndex==0)
        {
            transaction.replace(R.id.fragmentViewer,allFragment[fIndex]).commit();
        }
       bottomNav.setOnItemSelectedListener(item->{
           transaction = getSupportFragmentManager().beginTransaction();
           if(item.getItemId()==R.id.navChat)
           {
               fIndex=0;
               transaction.replace(R.id.fragmentViewer,allFragment[fIndex]).commit();
           }
           else if(item.getItemId()==R.id.navPeople)
           {
               fIndex=1;
               transaction.replace(R.id.fragmentViewer,allFragment[fIndex]).commit();
           }
           else if(item.getItemId()==R.id.navCall)
           {
               fIndex=2;
               transaction.replace(R.id.fragmentViewer,allFragment[fIndex]).commit();
           }
           else{
               fIndex=3;
               transaction.replace(R.id.fragmentViewer,allFragment[fIndex]).commit();
           }


           return true;
       });




    }
}