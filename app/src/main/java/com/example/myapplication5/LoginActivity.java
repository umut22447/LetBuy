package com.example.myapplication5;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void registerButtonClicked(View view){
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        EditText usernameEdit = findViewById(R.id.usernameLogin);
        String s = usernameEdit.getText().toString();
        intent.putExtra("message", s);
        startActivity(intent);

    }

    public void loginButtonClicked(View view){
        TextView infoText = findViewById(R.id.info);
        EditText usernameEdit = findViewById(R.id.usernameLogin);
        EditText passEdit = findViewById(R.id.passwordLogin);

        String user = usernameEdit.getText().toString();
        String pass = passEdit.getText().toString();

        UserManager userManager = new UserManager(this);

        User u = new User(user,pass,user,user,user);

        if(userManager.checkUser(u)){
            infoText.setTextColor(getResources().getColor(R.color.green));
            infoText.setText(R.string.correctLogin);
            Intent i = new Intent(LoginActivity.this, MainMenuActivity.class);
            i.putExtra(MainMenuActivity.USERNAME_EXTRA,u.getUsername());
            startActivity(i);
        }
        else{
            infoText.setText(R.string.wrongLogin);
            usernameEdit.setText("");
            passEdit.setText("");
        }



    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finishAffinity();
                        System.exit(0);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    public void feedBackClicked(View view) {
        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{"umuttvural@gmail.com"});

        email.setType("message/rfc822");

        startActivity(email);
    }
}
