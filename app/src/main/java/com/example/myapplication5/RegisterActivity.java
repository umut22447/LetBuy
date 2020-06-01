package com.example.myapplication5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);
        Intent intent = getIntent();
        s = intent.getStringExtra("message");
        EditText usernameEdit = findViewById(R.id.usernameRegister);
        usernameEdit.setText(s);

    }

    public void registerButtonClicked(View view){
        new InsertUserTask().execute(s);
    }

    public boolean isPassSame(String pass1, String pass2){
        return pass1.equals(pass2);

    }

    public void hasAccountClicked(View view) {
        Intent backIntent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(backIntent);
    }


    private class InsertUserTask extends AsyncTask<String,Void,Boolean>{

        EditText usernameEdit;
        EditText passwordEdit;
        EditText passwordAgainEdit;
        EditText fullnameEdit;
        EditText emailEdit;
        EditText addressEdit;
        TextView warningText;
        Spinner emailSpinner;
        String warningMessage;

        @Override
        protected void onPreExecute(){
            usernameEdit = findViewById(R.id.usernameRegister);
            passwordEdit = findViewById(R.id.passwordRegister);
            passwordAgainEdit = findViewById(R.id.passAgainRegister);
            fullnameEdit = findViewById(R.id.fullnameRegister);
            emailEdit = findViewById(R.id.emailRegister);
            addressEdit = findViewById(R.id.addressRegister);
            warningText = findViewById(R.id.samePassRegister);
            emailSpinner = findViewById(R.id.emailSpinner);
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            String username = usernameEdit.getText().toString();
            String password = passwordEdit.getText().toString();
            String passwordAgain = passwordAgainEdit.getText().toString();
            String fullname = fullnameEdit.getText().toString();
            String email = "";
            email += emailEdit.getText().toString() + "@" + emailSpinner.getSelectedItem().toString();
            String address = addressEdit.getText().toString();


            if(isPassSame(password,passwordAgain)) {
                User newUser = new User(username, password, email, fullname, address);
                UserManager users = new UserManager(RegisterActivity.this);
                if(users.isUsernameValid(newUser)){
                    LetBuyDatabaseHelper helper = new LetBuyDatabaseHelper(RegisterActivity.this);
                    try{
                        SQLiteDatabase database = helper.getWritableDatabase();
                        helper.insertUser(database,newUser);
                        database.close();
                        return true;
                    }
                    catch (SQLException e){
                        warningMessage = "Database cannot opened.";
                        return false;
                    }

                }
                else{
                    warningMessage = "Account already taken. Please select different username";
                    return false;
                }

            }
            else{
                warningMessage = "Password should be same.";
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success){
            if(success){
                Intent backIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(backIntent);
            }
            else{
                warningText.setText(warningMessage);
            }

        }
    }
}
