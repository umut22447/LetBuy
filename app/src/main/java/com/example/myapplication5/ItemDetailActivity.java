package com.example.myapplication5;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class ItemDetailActivity extends AppCompatActivity {

    public static final String ITEM_NO_EXTRA = "itemId";
    public static final String USERNAME_EXTRA = "username";
    private User user;
    private Item item;
    private String username;
    ItemManager itemManager = new ItemManager(this);
    private UserManager userManager = new UserManager(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        Intent itemDetailIntent = getIntent();
        int itemId = itemDetailIntent.getIntExtra(ITEM_NO_EXTRA, 0);
        username = itemDetailIntent.getStringExtra(USERNAME_EXTRA);
        item = itemManager.getItemById(itemId);
        user = userManager.getUser(username);

        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        toolbar.setTitle(R.string.item_detail);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        TextView seller = findViewById(R.id.sellerName);
        ImageView image = findViewById(R.id.itemPicture);
        TextView productName = findViewById(R.id.productName);
        TextView productType = findViewById(R.id.productType);
        TextView price = findViewById(R.id.price);
        TextView description = findViewById(R.id.description);

        seller.setText(item.getSeller());
        image.setImageResource(item.getImageResourceId());
        productName.setText(item.getName());
        productType.setText(item.getType());
        price.setText(Double.toString(item.getPrice()));
        description.setText(item.getDescription());


    }

    public void onBuyButtonClicked(View view) {
        if(!(user.getUsername().equals(item.getSeller()))){
            if(user.getBalance() >= item.getPrice()){
                User seller = userManager.getUser(item.getSeller());
                double buyerBalance = user.getBalance() - item.getPrice();
                double sellerBalance = seller.getBalance() + item.getPrice();
                itemManager.buyItem(item,user);
                LetBuyDatabaseHelper helper = new LetBuyDatabaseHelper(this);
                try{
                    SQLiteDatabase database = helper.getWritableDatabase();
                    ContentValues buyerUpdatedBalance = new ContentValues();
                    buyerUpdatedBalance.put("BALANCE",buyerBalance);
                    database.update("USER_DB",buyerUpdatedBalance,"USERNAME = ?",new String[]{username});
                    ContentValues sellerUpdatedBalance = new ContentValues();
                    sellerUpdatedBalance.put("BALANCE",sellerBalance);
                    database.update("USER_DB",sellerUpdatedBalance,"USERNAME = ?",new String[]{seller.getUsername()});
                    Toast.makeText(getApplicationContext(), R.string.successfully_purchased, Toast.LENGTH_SHORT).show();
                    database.delete("OFFER_DB","BUYERITEMID = ?",new String[]{Integer.toString(item.getItemId())});
                    database.delete("OFFER_DB","SELLERITEMID = ?",new String[]{Integer.toString(item.getItemId())});
                    Intent serviceIntent = new Intent(getApplicationContext(),NotificationIntentService.class);
                    startService(serviceIntent);
                    Intent intent = new Intent(getApplicationContext(),MainMenuActivity.class);
                    intent.putExtra(MainMenuActivity.USERNAME_EXTRA,username);
                    startActivity(intent);
                }
                catch(SQLException e){
                    Toast.makeText(this,"Database cannot open.",Toast.LENGTH_SHORT).show();
                }

            }
            else{
                Snackbar snackbar = Snackbar.make(findViewById(R.id.item_detail_linear),"Insufficient funds. Please add money.",Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        }
        else{
            Snackbar snackbar = Snackbar.make(findViewById(R.id.item_detail_linear),"You cannot buy your own item.",Snackbar.LENGTH_SHORT);
            snackbar.show();
        }

    }

    public void offerButtonClicked(View view) {
        if(!username.equals(item.getSeller())){
            Intent intent = new Intent(this,PrepareOfferActivity.class);
            intent.putExtra(PrepareOfferActivity.BUYER_EXTRA,username);
            intent.putExtra(PrepareOfferActivity.SELLER_EXTRA,item.getSeller());
            intent.putExtra(PrepareOfferActivity.EXTRA_ITEM_ID,item.getItemId());
            startActivity(intent);
        }
        else{
            Snackbar snackbar = Snackbar.make(findViewById(R.id.item_detail_linear),"You cannot offer trade for your own item.",Snackbar.LENGTH_SHORT);
            snackbar.show();
        }

    }
}
