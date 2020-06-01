package com.example.myapplication5;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class ItemAddActivity extends AppCompatActivity {

    public static final String USERNAME_EXTRA = "username";
    private User user;
    private String username;
    ItemManager itemManager = new ItemManager(this);
    UserManager userManager = new UserManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_add);

        username = getIntent().getStringExtra(USERNAME_EXTRA);

        user = userManager.getUser(username);

        Toolbar toolbar = findViewById(R.id.add_item_toolbar);
        toolbar.setTitle(R.string.add_item);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void listItemButtonClicked(View view) {
        ImageView imageEdit = findViewById(R.id.imageAddActivity);
        EditText itemNameEdit = findViewById(R.id.productNameAddActivity);
        Spinner itemTypeEdit = findViewById(R.id.productTypeAddActivity);
        EditText priceEdit = findViewById(R.id.priceAddActivity);
        EditText descriptionEdit = findViewById(R.id.descriptionAddActivity);

        int image = R.drawable.letbuy_logo;
        String itemName = itemNameEdit.getText().toString();
        String itemType = itemTypeEdit.getSelectedItem().toString();
        double price = Double.valueOf(priceEdit.getText().toString());
        String description = descriptionEdit.getText().toString();

        if(itemType.equals("Electronic")){
            image = R.drawable.electronic_image;
        }
        else if(itemType.equals("Car")){
            image = R.drawable.car_image;
        }
        else if(itemType.equals("Clothes")){
            image = R.drawable.clothes_image;
        }

        Item item = new Item(0,user.getUsername(),itemName,itemType,description,price,image);

        itemManager.addItem(item);

        Intent intent = new Intent(getApplicationContext(),MainMenuActivity.class);
        intent.putExtra(USERNAME_EXTRA,username);
        startActivity(intent);


    }
}
