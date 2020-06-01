package com.example.myapplication5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class OfferDetailActivity extends AppCompatActivity {

    public static final String EXTRA_SELLER = "seller";
    public static final String EXTRA_BUYER = "buyer";
    public static final String EXTRA_SELLER_ITEM = "seller_item";
    public static final String EXTRA_BUYER_ITEM = "buyer_item";
    public static final String EXTRA_AMOUNT = "amount";

    String seller,buyer;
    int sellerItemId, buyerItemId;
    double amount;
    Item sellerItem;
    Item buyerItem;

    TextView sellerItemText, buyerItemText, sellerPriceText, buyerPriceText, offeredAmountText;
    ImageView sellerItemImage, buyerItemImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_detail);

        seller = getIntent().getStringExtra(EXTRA_SELLER);
        buyer = getIntent().getStringExtra(EXTRA_BUYER);
        sellerItemId = getIntent().getExtras().getInt(EXTRA_SELLER_ITEM);
        buyerItemId = getIntent().getExtras().getInt(EXTRA_BUYER_ITEM);
        amount = getIntent().getExtras().getDouble(EXTRA_AMOUNT);

        sellerItemText = findViewById(R.id.user_item_text);
        buyerItemText = findViewById(R.id.offered_item_text);
        sellerPriceText = findViewById(R.id.user_item_price_text);
        buyerPriceText = findViewById(R.id.offered_item_price_text);
        offeredAmountText = findViewById(R.id.offered_amount_text);
        sellerItemImage = findViewById(R.id.seller_item_img);
        buyerItemImage = findViewById(R.id.buyer_item_img);

        ItemManager itemManager = new ItemManager(this);
        sellerItem = itemManager.getItemById(sellerItemId);
        buyerItem = itemManager.getItemById(buyerItemId);

        sellerItemText.setText(sellerItem.getName());
        buyerItemText.setText(buyerItem.getName());
        sellerPriceText.setText(String.valueOf(sellerItem.getPrice()));
        buyerPriceText.setText(String.valueOf(buyerItem.getPrice()));
        offeredAmountText.setText(String.valueOf(amount));
        sellerItemImage.setImageResource(sellerItem.getImageResourceId());
        buyerItemImage.setImageResource(buyerItem.getImageResourceId());
    }

    public void acceptButtonClicked(View view) {
        LetBuyDatabaseHelper helper = new LetBuyDatabaseHelper(this);
        try{
            SQLiteDatabase database = helper.getWritableDatabase();
            helper.insertUserInventory(database,sellerItem.getName(),seller,buyer);
            helper.insertUserInventory(database,buyerItem.getName(),buyer,seller);
            ContentValues values = new ContentValues();
            UserManager userManager = new UserManager(this);
            double oldAmount =  userManager.getUser(seller).getBalance();
            double newAmount = oldAmount + amount;
            values.put("BALANCE", newAmount);
            database.update("USER_DB", values, "USERNAME = ?" , new String[]{seller});
            ContentValues values2 = new ContentValues();
            double buyerOldAmount = userManager.getUser(buyer).getBalance();
            double buyerNewAmount = buyerOldAmount - amount;
            values2.put("BALANCE",buyerNewAmount);
            database.update("USER_DB", values2, "USERNAME = ?" , new String[]{buyer});
            database.delete("ITEM_DB","_id = ?", new String[]{String.valueOf(sellerItemId)});
            database.delete("ITEM_DB","_id = ?", new String[]{String.valueOf(buyerItemId)});
            database.delete("OFFER_DB", "SELLERITEMID = ?", new String[]{String.valueOf(sellerItemId)});
            database.delete("OFFER_DB", "BUYERITEMID = ?", new String[]{String.valueOf(sellerItemId)});
            database.delete("OFFER_DB", "SELLERITEMID = ?", new String[]{String.valueOf(buyerItemId)});
            database.delete("OFFER_DB", "BUYERITEMID = ?", new String[]{String.valueOf(buyerItemId)});
            database.close();
            Toast.makeText(this, "Trade offer accepted. Please check your inventory and your balance.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,MainMenuActivity.class);
            intent.putExtra(MainMenuActivity.USERNAME_EXTRA,seller);
            startActivity(intent);
        }
        catch(SQLException e){
            Toast.makeText(this, "Database CANNOT OPENED.", Toast.LENGTH_SHORT).show();
        }

    }
}
