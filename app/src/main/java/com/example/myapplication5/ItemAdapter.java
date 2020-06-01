package com.example.myapplication5;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private String[] productNames;
    private String[] sellers;
    private double[] prices;
    private int[] imageIds;
    private Listener listener;

    public static interface Listener{
        void onItemClicked(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;


        public ViewHolder(CardView itemView) {
            super(itemView);
            cardView = itemView;
        }
    }

    public ItemAdapter(String[] productNames, String[] sellers, double[] prices, int[] imageIds){
        this.productNames = productNames;
        this.sellers = sellers;
        this.prices = prices;
        this.imageIds = imageIds;
    }

    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_template,parent,false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position){
        CardView cardView = holder.cardView;
        ImageView imageView = cardView.findViewById(R.id.item_list_picture);
        Drawable drawable = ContextCompat.getDrawable(cardView.getContext(),imageIds[position]);
        imageView.setImageDrawable(drawable);
        imageView.setContentDescription(productNames[position]);
        TextView productText = cardView.findViewById(R.id.item_list_productname);
        TextView sellerText = cardView.findViewById(R.id.item_list_seller);
        TextView priceText = cardView.findViewById(R.id.item_list_price);
        productText.setText(productNames[position]);
        sellerText.setText(sellers[position]);
        priceText.setText(String.valueOf(prices[position]));
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onItemClicked(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return productNames.length;
    }

    public void setListener(Listener listener){
        this.listener = listener;
    }


}
