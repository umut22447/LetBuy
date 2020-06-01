package com.example.myapplication5;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LetBuyDatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "LETBUY_DB";
    public static final int VERSION = 1;

    public LetBuyDatabaseHelper(Context context){
        super(context,DB_NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE USER_DB (USERNAME TEXT PRIMARY KEY, PASSWORD TEXT, EMAIL TEXT, FULLNAME TEXT, ADDRESS TEXT, BALANCE DOUBLE);");
        db.execSQL("CREATE TABLE ITEM_DB (_id INTEGER PRIMARY KEY AUTOINCREMENT, SELLER TEXT, NAME TEXT, TYPE TEXT, DESCRIPTION TEXT, PRICE DOUBLE, IMAGEID INTEGER);");
        db.execSQL("CREATE TABLE OFFER_DB (SELLER TEXT,BUYER TEXT,SELLERITEMID INTEGER,BUYERITEMID INTEGER,AMOUNT DOUBLE);");
        db.execSQL("CREATE TABLE USER_INVENTORY (ITEMNAME TEXT, OLDOWNER TEXT, NEWOWNER TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void insertUser(SQLiteDatabase db, User user){
        ContentValues values = new ContentValues();
        values.put("USERNAME",user.getUsername());
        values.put("PASSWORD",user.getPassword());
        values.put("EMAIL",user.getEmail());
        values.put("FULLNAME",user.getFullName());
        values.put("ADDRESS",user.getAddress());
        values.put("BALANCE",user.getBalance());
        db.insert("USER_DB",null,values);
    }

    public void insertItem(SQLiteDatabase db, Item item){
        ContentValues values = new ContentValues();
        values.put("SELLER",item.getSeller());
        values.put("NAME",item.getName());
        values.put("TYPE",item.getType());
        values.put("DESCRIPTION",item.getDescription());
        values.put("PRICE",item.getPrice());
        values.put("IMAGEID",item.getImageResourceId());
        db.insert("ITEM_DB",null,values);
    }

    public void insertOffer(SQLiteDatabase db, Offer offer){
        ContentValues values = new ContentValues();
        values.put("SELLER",offer.getSeller());
        values.put("BUYER",offer.getBuyer());
        values.put("SELLERITEMID",offer.getSellerItemId());
        values.put("BUYERITEMID",offer.getBuyerItemId());
        values.put("AMOUNT",offer.getAmount());
        db.insert("OFFER_DB",null,values);
    }

    public void insertUserInventory(SQLiteDatabase db,String itemName, String oldOwner, String newOwner){
        ContentValues values = new ContentValues();
        values.put("ITEMNAME", itemName);
        values.put("OLDOWNER",oldOwner);
        values.put("NEWOWNER",newOwner);
        db.insert("USER_INVENTORY",null, values);
    }
}
