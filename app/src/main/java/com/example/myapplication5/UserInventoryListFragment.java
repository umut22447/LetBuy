package com.example.myapplication5;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class UserInventoryListFragment extends ListFragment {

    String username;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        username = getActivity().getIntent().getStringExtra(UserInventoryActivity.USERNAME_EXTRA);
        ArrayList<UserInventoryItem> inventory = new ArrayList<>();

        LetBuyDatabaseHelper helper = new LetBuyDatabaseHelper(getContext());
        try{
            SQLiteDatabase database = helper.getReadableDatabase();
            Cursor cursor = database.query("USER_INVENTORY",new String[]{"ITEMNAME","OLDOWNER","NEWOWNER"},"NEWOWNER = ?", new String[]{username},null,null,null);
            while(cursor.moveToNext()){
                UserInventoryItem item = new UserInventoryItem(cursor.getString(0),cursor.getString(1),cursor.getString(2));
                inventory.add(item);
            }
            ArrayAdapter adapter = new ArrayAdapter(inflater.getContext(),android.R.layout.simple_list_item_1,inventory);
            setListAdapter(adapter);
            cursor.close();
            database.close();
        }
        catch (SQLException e){
            Toast.makeText(inflater.getContext(), "DATABASE CANNOT OPENED.", Toast.LENGTH_SHORT).show();
        }

        return super.onCreateView(inflater,container,savedInstanceState);
    }
}
