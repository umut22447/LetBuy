package com.example.myapplication5;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class ClothesListFragment extends Fragment implements ItemAdapter.Listener{
    private ItemFragmentInterface listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_clothes_list,container,false);

        LetBuyDatabaseHelper helper = new LetBuyDatabaseHelper(getActivity());

        try{
            SQLiteDatabase database = helper.getReadableDatabase();
            Cursor cursor = database.query("ITEM_DB",new String[]{"_id","SELLER","NAME","TYPE","DESCRIPTION","PRICE","IMAGEID"},"TYPE = ?",new String[]{"Clothes"},null,null,null);
            String[] names = new String[cursor.getCount()];
            String[] seller = new String[cursor.getCount()];
            double[] prices = new double[cursor.getCount()];
            int[] images = new int[cursor.getCount()];

            int index = 0;
            while(cursor.moveToNext()){
                seller[index] = cursor.getString(1);
                names[index] = cursor.getString(2);
                prices[index] = cursor.getDouble(5);
                images[index] = cursor.getInt(6);
                index++;
            }

            ItemAdapter itemAdapter = new ItemAdapter(names,seller,prices,images);
            recyclerView.setAdapter(itemAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            itemAdapter.setListener(this);

            cursor.close();
            database.close();

        }
        catch (SQLException e){
            Toast.makeText(getActivity(),"Database cannot open.",Toast.LENGTH_SHORT).show();
        }


        return recyclerView;
    }

    @Override
    public void onItemClicked(int position) {
        listener.spesificItemClicked(position,"Clothes");
    }

    public void setListener(ItemFragmentInterface listener){
        this.listener = listener;
    }

}
