package com.example.myapplication5;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

public class UserManager {

    Context context;
    private static String lastRequestedUsername;

    public UserManager(Context context){
        this.context = context;
    }

    public User getUser(String usernameParam){
        LetBuyDatabaseHelper helper = new LetBuyDatabaseHelper(context);
        User user = null;
        try{
            SQLiteDatabase database = helper.getReadableDatabase();
            Cursor cursor = database.query("USER_DB",new String[]{"USERNAME","PASSWORD","EMAIL","FULLNAME","ADDRESS","BALANCE"},"USERNAME = ?",new String[]{usernameParam},null,null,null);
            if(cursor.moveToNext()){
                String username = cursor.getString(0);
                String password = cursor.getString(1);
                String email = cursor.getString(2);
                String fullname = cursor.getString(3);
                String address = cursor.getString(4);
                double balance = cursor.getDouble(5);
                user = new User(username,password,email,fullname,address);
                user.setBalance(balance);
            }

            cursor.close();
            database.close();
        }
        catch(SQLException e){
            Toast.makeText(context,"Database cannot open.",Toast.LENGTH_SHORT).show();
        }
        return user;
    }


    public boolean checkUser(User u){
        User user = getUser(u.getUsername());
        if(user == null){
            return false;
        }

        if(u.getPassword().equals(user.getPassword()) && u.getUsername().equals(user.getUsername())){
            lastRequestedUsername = user.getUsername();
            return true;
        }

        return false;
    }

    public boolean isUsernameValid(User u){
        User user = getUser(u.getUsername());
        if(user == null){
            return true;
        }
        return false;
    }

    public String getLastRequestedUsername(){
        return lastRequestedUsername;
    }

}
