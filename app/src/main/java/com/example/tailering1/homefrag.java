package com.example.tailering1;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;


public class homefrag extends Fragment {



    public homefrag() {
        // Required empty public constructor

    }

    CardView card;
    Button btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_homefrag, container, false);
        return view;

    }

     public void goToAttract(View v)
     {
         card= getView().findViewById(R.id.cardhome);
         card.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(getActivity().getApplication(),customer.class);
                 startActivity(intent);

             }
         });


     }


}