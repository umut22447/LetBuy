package com.example.myapplication5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PrepareOfferActivity extends AppCompatActivity implements OfferAmountFragment.OfferListener{

    public static final String BUYER_EXTRA = "buyer";
    public static final String SELLER_EXTRA = "seller";
    public static final String EXTRA_ITEM_ID = "item_id";

    public String seller;
    public String buyer;
    public int itemId;
    ArrayList<Item> sellerItems;
    Item offeredItem;
    ListView listView;
    LetBuyDatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepare_offer);

        buyer = getIntent().getStringExtra(BUYER_EXTRA);
        seller = getIntent().getStringExtra(SELLER_EXTRA);
        itemId = getIntent().getIntExtra(EXTRA_ITEM_ID,0);

        sellerItems = new ArrayList<>();
        helper = new LetBuyDatabaseHelper(this);
        try{
            SQLiteDatabase database = helper.getReadableDatabase();
            Cursor cursor = database.query("ITEM_DB",new String[]{"_id","SELLER","NAME","TYPE","DESCRIPTION","PRICE","IMAGEID"},"SELLER = ?",new String[]{buyer},null,null,null);   //Satıcısı kendi olan itemleri listele.
            while(cursor.moveToNext()){
                Item item = new Item(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getDouble(5),cursor.getInt(6));
                sellerItems.add(item);
            }
            listView = findViewById(R.id.prepare_list_view);
            ArrayAdapter<Item> listAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,sellerItems);
            listView.setAdapter(listAdapter);
            cursor.close();
            database.close();
        }
        catch (SQLException e){
            Toast.makeText(this, "Database cannot be opened.", Toast.LENGTH_SHORT).show();
        }


        OfferAmountFragment fragment = new OfferAmountFragment();
        fragment.setListener(this);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.prepare_amount_frame,fragment);
        ft.commit();

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                offeredItem = sellerItems.get(position);
                TextView selectedItemText = findViewById(R.id.selected_item_name);
                TextView selectedItemPriceText = findViewById(R.id.selected_item_price);

                selectedItemText.setText(offeredItem.getName());
                selectedItemPriceText.setText(String.valueOf(offeredItem.getPrice()));
            }
        };

        listView.setOnItemClickListener(itemClickListener);
    }

    @Override
    public void makeOffer(double amount) {
        try{
            if(offeredItem != null){
                Offer offer = new Offer(seller,buyer,itemId,offeredItem.getItemId(),amount);
                SQLiteDatabase writableDatabase =  helper.getWritableDatabase();
                helper.insertOffer(writableDatabase,offer);
                writableDatabase.close();
                Intent intent = new Intent(this,MainMenuActivity.class);
                intent.putExtra(MainMenuActivity.USERNAME_EXTRA,buyer);
                Toast.makeText(this, "You successfully send offer.", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
            else{
                Toast.makeText(this, "Please select one item.", Toast.LENGTH_SHORT).show();
            }
        }
        catch (SQLException e){

        }
    }
}
