package com.example.myapplication5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class UserOffersActivity extends AppCompatActivity {

    public static final String USERNAME_EXTRA = "username";
    String username;

    TextView noOfferText;
    ListView offerListView;
    FrameLayout frameLayout;
    ArrayList<Offer> offerList;

    SQLiteDatabase database;
    Cursor cursor;
    //CursorAdapter cursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_offers);
        username = getIntent().getStringExtra(USERNAME_EXTRA);
        offerList = new ArrayList<>();
        noOfferText = findViewById(R.id.no_offer_text);
        offerListView = findViewById(R.id.user_offer_list);
        frameLayout = findViewById(R.id.offer_frame_layout);

        Toolbar toolbar = findViewById(R.id.user_offers_toolbar);
        toolbar.setTitle(R.string.offer);
        setSupportActionBar(toolbar);
        LetBuyDatabaseHelper helper = new LetBuyDatabaseHelper(this);
        try{
            database = helper.getReadableDatabase();
            cursor = database.query("OFFER_DB",new String[]{"SELLER","BUYER","SELLERITEMID","BUYERITEMID","AMOUNT"},"SELLER = ?",new String[]{username},null,null,null);
            while(cursor.moveToNext()){
                Offer offer = new Offer(cursor.getString(0),cursor.getString(1),cursor.getInt(2),cursor.getInt(3),cursor.getDouble(4));
                offerList.add(offer);
            }
            ArrayAdapter<Offer> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,offerList);
            //cursorAdapter = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_1,cursor,new String[]{"BUYER"},new int[]{android.R.id.text1},0); //En kötü arrayadapter kullan.
            offerListView.setAdapter(arrayAdapter);
            if(offerList.isEmpty()){
                noOfferText.setVisibility(View.VISIBLE);
            }
            else{
                noOfferText.setVisibility(View.GONE);

            }

            AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    offerItemClicked(position);
                }
            };

            offerListView.setOnItemClickListener(itemClickListener);


        }
        catch(SQLException e){
        }
    }

    @Override
    public void onRestart() {
        super.onRestart();
    }

    public void offerItemClicked(int position){
        Intent intent = new Intent(this,OfferDetailActivity.class);
        Offer ofr = offerList.get(position);
        intent.putExtra(OfferDetailActivity.EXTRA_SELLER,ofr.getSeller());
        intent.putExtra(OfferDetailActivity.EXTRA_BUYER,ofr.getBuyer());
        intent.putExtra(OfferDetailActivity.EXTRA_SELLER_ITEM,ofr.getSellerItemId());
        intent.putExtra(OfferDetailActivity.EXTRA_BUYER_ITEM,ofr.getBuyerItemId());
        intent.putExtra(OfferDetailActivity.EXTRA_AMOUNT,ofr.getAmount());
        startActivity(intent);
    }
}
