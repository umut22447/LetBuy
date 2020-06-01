package com.example.myapplication5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class UserInventoryActivity extends AppCompatActivity {

    public static final String USERNAME_EXTRA = "username";
    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_inventory);
        username = getIntent().getStringExtra(USERNAME_EXTRA);
        Toast.makeText(this, R.string.you_can_see_your_item, Toast.LENGTH_LONG).show();
    }
}
