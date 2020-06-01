package com.example.myapplication5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileUserModification extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "value";
    public static final String EXTRA_USERNAME = "username";
    String intentString;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user_modification);

        intentString = getIntent().getStringExtra(EXTRA_MESSAGE);
        username = getIntent().getStringExtra(EXTRA_USERNAME);
        if(intentString.equals("BALANCE")){
            TextView newValueText = findViewById(R.id.new_value_text);
            newValueText.setText(getResources().getString(R.string.new_balance_value));
            Button button = findViewById(R.id.new_value_button);
            button.setText(getResources().getString(R.string.add_balance));
            EditText newValueEdit = findViewById(R.id.new_value_edittext);
            newValueEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
        else{
            TextView newValueText2 = findViewById(R.id.new_value_text2);
            newValueText2.setText(intentString);
            EditText newValueEdit = findViewById(R.id.new_value_edittext);
            newValueEdit.setInputType(InputType.TYPE_CLASS_TEXT);
        }
    }

    public void modifyButtonClicked(View view) {
        LetBuyDatabaseHelper helper = new LetBuyDatabaseHelper(this);
        EditText newValue = findViewById(R.id.new_value_edittext);
        try{
            SQLiteDatabase database = helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            if(intentString.equals("BALANCE")){
                try{
                    double addBalance = Double.parseDouble(newValue.getText().toString());
                    Cursor cursor = database.query("USER_DB",new String[]{"USERNAME","PASSWORD","EMAIL","FULLNAME","ADDRESS","BALANCE"},"USERNAME = ?",new String[]{username},null,null,null);
                    if(cursor.moveToNext()){
                        double currentBalance = cursor.getDouble(5);
                        double totalBalance = currentBalance + addBalance;
                        values.put("BALANCE",totalBalance);
                        database.update("USER_DB",values,"USERNAME = ?",new String[]{username});
                        Intent intent = new Intent(this,MyProfileActivity.class);
                        intent.putExtra(MyProfileActivity.USERNAME_EXTRA,username);
                        cursor.close();
                        database.close();
                        Toast.makeText(this, "Money Successfully Added.", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(this, "User cannot found !!", Toast.LENGTH_SHORT).show();
                        cursor.close();
                        database.close();
                    }
                }
                catch (NumberFormatException n){
                    Toast.makeText(this, "Please Enter a Number !!", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                values.put(intentString,newValue.getText().toString());
                database.update("USER_DB",values,"USERNAME = ?",new String[]{username});
                Intent intent = new Intent(this,MyProfileActivity.class);
                intent.putExtra(MyProfileActivity.USERNAME_EXTRA,username);
                startActivity(intent);
                database.close();
            }
        }
        catch(SQLException e){
            Toast.makeText(this, "Database cannot be opened !!!", Toast.LENGTH_SHORT).show();
        }

    }
}
