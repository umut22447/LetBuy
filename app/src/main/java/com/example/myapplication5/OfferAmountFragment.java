package com.example.myapplication5;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class OfferAmountFragment extends Fragment {

    public EditText amountEdit;
    public String username;

    public static interface OfferListener{
        void makeOffer(double amount);
    }

    OfferListener offerListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offer_amount, container, false);
        amountEdit = view.findViewById(R.id.offer_amount);

        username = getActivity().getIntent().getStringExtra(PrepareOfferActivity.BUYER_EXTRA);
        UserManager userManager = new UserManager(getActivity());
        final User user = userManager.getUser(username);
        Button button = view.findViewById(R.id.make_offer_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(amountEdit.getText().toString().equals("")){
                    offerListener.makeOffer(0);
                }
                else{
                    double amount = Double.valueOf(amountEdit.getText().toString());
                    if(user.getBalance() >= amount){
                        offerListener.makeOffer(amount);
                    }
                    else{
                        amountEdit.setText("");
                        amountEdit.setHint("Your balance is not enough.");
                        amountEdit.setHintTextColor(getResources().getColor(R.color.design_default_color_error));
                    }
                }

            }
        });
        return view;
    }

    public void setListener(OfferListener listener){
        this.offerListener = listener;
    }


}
