package com.chatbiz.encrypter;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

public class Encryption {
    private Context context = null;
    private String email;
    private String password;
    private SharedPreferences sharedPreferences;
    private MasterKey masterKey;

    public Encryption(Context context, String email, String password) {
        this.context = context;
        this.email = email;
        this.password = password;
    }

    public void encryptSave() {
        try {
            masterKey = new MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build();
            sharedPreferences = EncryptedSharedPreferences.create(context, "FILE", masterKey, EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV, EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("email", email);
            editor.putString("password", password);
            editor.apply();
        } catch (Exception e) {
        }


    }


}
