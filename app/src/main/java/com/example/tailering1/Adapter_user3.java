package com.example.tailering1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Adapter_user3 extends RecyclerView.Adapter<Adapter_user3.Holder_user> {

    private Context context;
    public ArrayList<Model_User3> user_list;

    public Adapter_user3(Context context, ArrayList<Model_User3> user_list) {
        this.context = context;
        this.user_list = user_list;
    }


    @NonNull
    @Override
    public Holder_user onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_layout3, parent, false);
        return new Adapter_user3.Holder_user(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder_user holder, int position) {
        Model_User3 model_user = user_list.get(position);
        holder.TVcustnm.setText(model_user.custname);
        holder.TVcustmob.setText(model_user.custmob);
        holder.TVtotal.setText(String.valueOf(model_user.totalamt));
        holder.TVdate.setText((CharSequence) model_user.date);

        holder.TVcustnm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  fetch remarks and measurenments from DB

                Intent intent = new Intent(context, customer.class);
                intent.putExtra("Here_for", "UPDATE");
                intent.putExtra("timestamp", model_user.timestamp);
                intent.putExtra("custname", model_user.custname);
                intent.putExtra("custmob", model_user.custmob);
                intent.putExtra("totalamt", model_user.totalamt);
                intent.putExtra("date", model_user.date);
                context.startActivity(intent);
            }
        });

        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("DELETE");
                builder.setMessage("Are You Sure ?");
                builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("client");
                        reference.child(model_user.timestamp).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
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
        return user_list.size();
    }

    class Holder_user extends RecyclerView.ViewHolder {
        TextView TVcustnm, TVcustmob, TVtotal, TVdate;
        CardView cardhistory;
        ImageButton del;

        public Holder_user(@NonNull View itemView) {
            super(itemView);
            TVcustmob = itemView.findViewById(R.id.TVcustmob);
            TVcustnm = itemView.findViewById(R.id.TVcustnm);
            TVtotal = itemView.findViewById(R.id.TVtotal);
            TVdate = itemView.findViewById(R.id.date);
            cardhistory = itemView.findViewById(R.id.cardhistory);
            del = itemView.findViewById(R.id.IMGdel);
        }
    }
}
