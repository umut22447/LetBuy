package com.example.myapplication5;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MyProfileActivity extends AppCompatActivity {

    public static final String USERNAME_EXTRA = "username";
    Cursor cursor;
    String usName;
    SQLiteDatabase database;
    String username;
    String password;
    String email;
    String fullname;
    String address;
    double balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {    //AsyncTask kullanılabilir.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile_page);
        Toolbar toolbar = findViewById(R.id.my_profile_toolbar);
        toolbar.setTitle(getResources().getString(R.string.my_profile));
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        usName = intent.getStringExtra(USERNAME_EXTRA);

        LetBuyDatabaseHelper helper = new LetBuyDatabaseHelper(this);
        try{
            database = helper.getReadableDatabase();
            cursor = database.query("USER_DB",new String[]{"USERNAME","PASSWORD","EMAIL","FULLNAME","ADDRESS","BALANCE"},"USERNAME = ?",new String[]{usName},null,null,null);
            if(cursor.moveToNext()){
                username = cursor.getString(0);
                email = cursor.getString(1);
                email = cursor.getString(2);
                fullname = cursor.getString(3);
                address = cursor.getString(4);
                balance = cursor.getDouble(5);
            }
        }
        catch(SQLException e){
            Toast.makeText(this,"Database cannot open.",Toast.LENGTH_SHORT).show();
        }

        TextView usernameEdit = findViewById(R.id.usernameProfile);
        usernameEdit.setText(username);

        TextView emailEdit = findViewById(R.id.emailProfile);
        emailEdit.setText(email);

        TextView fullnameEdit = findViewById(R.id.fullnameProfile);
        fullnameEdit.setText(fullname);

        TextView addressEdit = findViewById(R.id.addressProfile);
        addressEdit.setText(address);

        TextView balanceEdit = findViewById(R.id.balanceProfile);
        balanceEdit.setText(String.valueOf(balance));
    }

    @Override
    public void onRestart() {
        super.onRestart();
        LetBuyDatabaseHelper helper = new LetBuyDatabaseHelper(this);
        try{
            database = helper.getReadableDatabase();
            Cursor newCursor = database.query("USER_DB",new String[]{"USERNAME","PASSWORD","EMAIL","FULLNAME","ADDRESS","BALANCE"},"USERNAME = ?",new String[]{usName},null,null,null);
            if(newCursor.moveToNext()){
                username = newCursor.getString(0);
                password = newCursor.getString(1);
                email = newCursor.getString(2);
                fullname = newCursor.getString(3);
                address = newCursor.getString(4);
                balance = newCursor.getDouble(5);
            }

            cursor = newCursor;
            newCursor.close();
        }
        catch(SQLException e){
            Toast.makeText(this,"Database cannot open.",Toast.LENGTH_SHORT).show();
        }

        TextView usernameEdit = findViewById(R.id.usernameProfile);
        usernameEdit.setText(username);

        TextView emailEdit = findViewById(R.id.emailProfile);
        emailEdit.setText(email);

        TextView fullnameEdit = findViewById(R.id.fullnameProfile);
        fullnameEdit.setText(fullname);

        TextView addressEdit = findViewById(R.id.addressProfile);
        addressEdit.setText(address);

        TextView balanceEdit = findViewById(R.id.balanceProfile);
        balanceEdit.setText(String.valueOf(balance));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cursor.close();
        database.close();
    }


    public void emailChangeClicked(View view) {
        Intent intent = new Intent(this,ProfileUserModification.class);
        intent.putExtra(ProfileUserModification.EXTRA_MESSAGE,"EMAIL");
        intent.putExtra(ProfileUserModification.EXTRA_USERNAME,usName);
        startActivity(intent);
    }

    public void fullnameChangeClicked(View view) {
        Intent intent = new Intent(this,ProfileUserModification.class);
        intent.putExtra(ProfileUserModification.EXTRA_MESSAGE,"FULLNAME");
        intent.putExtra(ProfileUserModification.EXTRA_USERNAME,usName);
        startActivity(intent);
    }

    public void addressChangedClicked(View view) {
        Intent intent = new Intent(this,ProfileUserModification.class);
        intent.putExtra(ProfileUserModification.EXTRA_MESSAGE,"ADDRESS");
        intent.putExtra(ProfileUserModification.EXTRA_USERNAME,usName);
        startActivity(intent);
    }

    public void addBalanceClicked(View view) {
        Intent intent = new Intent(this,ProfileUserModification.class);
        intent.putExtra(ProfileUserModification.EXTRA_MESSAGE,"BALANCE");
        intent.putExtra(ProfileUserModification.EXTRA_USERNAME,usName);
        startActivity(intent);
    }
}
