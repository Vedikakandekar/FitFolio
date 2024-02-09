package com.example.tailering1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class Adapter_User2 extends RecyclerView.Adapter<Adapter_User2.Holder_user> {
    private Context context;
    public ArrayList<Model_User2> usersList;

    //Context aarya list from userList
    public Adapter_User2(Context context, ArrayList<Model_User2> usersList) {
        this.context = context;
        this.usersList = usersList;
    }

    @NonNull
    @Override

    //View type from users layout
    public Holder_user onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_layout1, parent, false);
        return new Adapter_User2.Holder_user(view);

    }

    // Holder Position
    @Override
    public void onBindViewHolder(@NonNull Holder_user holder, int position) {
        Model_User2 model_user2 = usersList.get(position);
        holder.type.setText(model_user2.type);
        holder.rate.setText(String.valueOf(model_user2.rate));
        holder.amt.setText(String.valueOf(model_user2.amt));
        holder.qty.setText(String.valueOf(model_user2.qty));

        holder.TVrM1.setText(model_user2.measurnments.get("M1").toString());
        holder.TVrM2.setText(model_user2.measurnments.get("M2").toString());
        holder.TVrM3.setText(model_user2.measurnments.get("M3").toString());
        holder.TVrM4.setText(model_user2.measurnments.get("M4").toString());
        holder.TVrM5.setText(model_user2.measurnments.get("M5").toString());
        holder.TVrM6.setText(model_user2.measurnments.get("M6").toString());
        holder.TVrM7.setText(model_user2.measurnments.get("M7").toString());
        holder.TVrM8.setText(model_user2.measurnments.get("M8").toString());
        holder.TVrM9.setText(model_user2.measurnments.get("M9").toString());
        holder.TVrM10.setText(model_user2.measurnments.get("M10").toString());
        holder.TVrM11.setText(model_user2.measurnments.get("M11").toString());
        holder.TVrM12.setText(model_user2.measurnments.get("M12").toString());

        holder.arrayAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,model_user2.remarks);
        holder.rRemarkList.setAdapter(holder.arrayAdapter);

        holder.TVrmeasurenments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(holder.cardmeasure.getVisibility()==View.VISIBLE)
            {
                holder.cardmeasure.setVisibility(View.GONE);
            }
            else {
                holder.cardmeasure.setVisibility(View.VISIBLE);
            }
            }
        });

        holder.TVrremarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.cardremarks.getVisibility()==View.VISIBLE)
                {
                    holder.cardremarks.setVisibility(View.GONE);
                }
                else {
                    holder.cardremarks.setVisibility(View.VISIBLE);
                }

            }
        });

        holder.imgdel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("DELETE");
                builder.setMessage("Are You Sure ?");
                builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        usersList.remove(holder.getPosition());
                        notifyDataSetChanged();
                        customer.totalamt_arraylist.remove(Double.parseDouble(holder.amt.getText().toString()));
                        double sum = 0.0;
                        for (double no : customer.totalamt_arraylist) {
                            sum = sum + no;
                        }
                        customer.TVamt.setText(String.valueOf(sum));
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();


            }
        });

    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    //RecyclerView.Adapter<Adapter_list_student.HolderUser>
    class Holder_user extends RecyclerView.ViewHolder {
        TextView type, qty, amt, rate, TVrmeasurenments, TVrremarks;
        CardView cardremarks, cardmeasure;
        TextView TVrM1, TVrM2, TVrM3, TVrM4, TVrM5, TVrM6, TVrM7, TVrM8, TVrM9, TVrM10, TVrM11, TVrM12;
        ArrayAdapter<String> arrayAdapter;
        ListView rRemarkList;
        ImageButton imgdel;

        public Holder_user(@NonNull View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.TVrtype);
            rate = itemView.findViewById(R.id.TVrrate);
            qty = itemView.findViewById(R.id.TVrqty);
            amt = itemView.findViewById(R.id.TVramt);

            TVrmeasurenments = itemView.findViewById(R.id.TVrmeasurenments);
            TVrremarks = itemView.findViewById(R.id.TVrremarks);

            cardmeasure = itemView.findViewById(R.id.cardmeasure);
            cardremarks = itemView.findViewById(R.id.cardremarks);

            TVrM1 = itemView.findViewById(R.id.TVrM1);
            TVrM2 = itemView.findViewById(R.id.TVrM2);
            TVrM3 = itemView.findViewById(R.id.TVrM3);
            TVrM4 = itemView.findViewById(R.id.TVrM4);
            TVrM5 = itemView.findViewById(R.id.TVrM5);
            TVrM6 = itemView.findViewById(R.id.TVrM6);
            TVrM7 = itemView.findViewById(R.id.TVrM7);
            TVrM8 = itemView.findViewById(R.id.TVrM8);
            TVrM9 = itemView.findViewById(R.id.TVrM9);
            TVrM10 = itemView.findViewById(R.id.TVrM10);
            TVrM11 = itemView.findViewById(R.id.TVrM11);
            TVrM12 = itemView.findViewById(R.id.TVrM12);

            rRemarkList = itemView.findViewById(R.id.rRemarkList);

            imgdel=itemView.findViewById(R.id.imgbtndel);

        }
    }
}


