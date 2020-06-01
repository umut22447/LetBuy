package com.example.myapplication5;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class ItemManager {

    Context context;

    public ItemManager(Context context){
        this.context = context;
    }

    public Item getItemById(int itemId){
        LetBuyDatabaseHelper helper = new LetBuyDatabaseHelper(context);
        Item item = null;
        try{
            SQLiteDatabase database = helper.getReadableDatabase();
            Cursor cursor = database.query("ITEM_DB",new String[]{"_id","SELLER","NAME","TYPE","DESCRIPTION","PRICE","IMAGEID"},"_id = ?",new String[]{String.valueOf(itemId)},null,null,null);
            if(cursor.moveToNext()){
                int id = cursor.getInt(0);
                String seller = cursor.getString(1);
                String itemName = cursor.getString(2);
                String type = cursor.getString(3);
                String description = cursor.getString(4);
                double price = cursor.getDouble(5);
                int imageId = cursor.getInt(6);

                item = new Item(id,seller,itemName,type,description,price,imageId);
            }

            cursor.close();
            database.close();
        }
        catch (SQLException e){
            Toast.makeText(context,"Database cannot open.",Toast.LENGTH_SHORT).show();
        }

        return item;
    }

    public Item getItem(int position){
        LetBuyDatabaseHelper helper = new LetBuyDatabaseHelper(context);
        Item item = null;
        try{
            SQLiteDatabase database = helper.getReadableDatabase();
            Cursor cursor = database.query("ITEM_DB",new String[]{"_id","SELLER","NAME","TYPE","DESCRIPTION","PRICE","IMAGEID"},null,null,null,null,null);
            for (int i = 0; i < position; i++) {
                cursor.moveToNext();
            }
            if(cursor.moveToNext()){
                int id = cursor.getInt(0);
                String seller = cursor.getString(1);
                String itemName = cursor.getString(2);
                String type = cursor.getString(3);
                String description = cursor.getString(4);
                double price = cursor.getDouble(5);
                int imageId = cursor.getInt(6);

                item = new Item(id,seller,itemName,type,description,price,imageId);
            }

            cursor.close();
            database.close();
        }
        catch (SQLException e){
            Toast.makeText(context,"Database cannot open.",Toast.LENGTH_SHORT).show();
        }

        return item;
    }

    public void addItem(Item item){
        LetBuyDatabaseHelper helper = new LetBuyDatabaseHelper(context);
        try{
            SQLiteDatabase database = helper.getWritableDatabase();
            helper.insertItem(database,item);
            database.close();
        }
        catch(SQLException e){
            Toast.makeText(context,"Database cannot open.",Toast.LENGTH_SHORT).show();
        }
    }

    public void buyItem(Item purchasingItem, User buyer){
        LetBuyDatabaseHelper helper = new LetBuyDatabaseHelper(context);
        try{
            SQLiteDatabase database = helper.getWritableDatabase();
            int itemId = purchasingItem.getItemId();
            String itemName = purchasingItem.getName();
            helper.insertUserInventory(database,itemName,purchasingItem.getSeller(),buyer.getUsername());
            database.delete("ITEM_DB","_id = ?", new String[]{String.valueOf(itemId)});
            database.close();
        }
        catch(SQLException e){
            Toast.makeText(context,"Database cannot open.",Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<Item> getItemListByType(String itemType){
        ArrayList<Item> list = new ArrayList<>();
        LetBuyDatabaseHelper helper = new LetBuyDatabaseHelper(context);
        try{
            SQLiteDatabase database = helper.getReadableDatabase();
            Cursor cursor = database.query("ITEM_DB",new String[]{"_id","SELLER","NAME","TYPE","DESCRIPTION","PRICE","IMAGEID"},"TYPE = ?",new String[]{itemType},null,null,null);
            while(cursor.moveToNext()){
                Item item = null;
                int id = cursor.getInt(0);
                String seller = cursor.getString(1);
                String itemName = cursor.getString(2);
                String type = cursor.getString(3);
                String description = cursor.getString(4);
                double price = cursor.getDouble(5);
                int imageId = cursor.getInt(6);

                item = new Item(id,seller,itemName,type,description,price,imageId);
                list.add(item);
            }

            cursor.close();
            database.close();
        }
        catch (SQLException e){
            Toast.makeText(context,"Database cannot open.",Toast.LENGTH_SHORT).show();
        }

        return list;
    }


}
