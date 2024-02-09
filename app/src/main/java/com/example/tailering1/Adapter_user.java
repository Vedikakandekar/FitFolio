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
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

// extending RecyclerView to adapter
public class Adapter_user extends RecyclerView.Adapter<Adapter_user.Holder_user> {
    private Context context;
    public ArrayList<Model_User> usersList;

    //Context aarya list from userList
    public Adapter_user(Context context,ArrayList<Model_User> usersList) {
        this.context = context;
        this.usersList = usersList;
    }

    @NonNull
    @Override

    //View type from users layout
    public Holder_user onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_layout, parent, false);
        return new Adapter_user.Holder_user(view);

    }

    // Holder Position
    @Override
    public void onBindViewHolder(@NonNull Holder_user holder, int position) {
        Model_User model_user = usersList.get(position);
        holder.type.setText(model_user.type);
        holder.rate.setText(model_user.getRate());


        holder.type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //geting remarks manually for time bieng.................temp solution
                ArrayList<String> remark = new ArrayList<>();
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference("type");
                databaseReference.orderByChild("type").equalTo(model_user.type).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot childsnapshot : snapshot.getChildren()) {
                            remark.add(childsnapshot.child("remark").getValue().toString().replace("[", "").replace("]", ""));
                        }
                        Intent intent = new Intent(context, pagetype.class);
                        intent.putExtra("I_AM_HERE_FOR", "UPDATE");
                        intent.putExtra("TIMESTAMP", model_user.timestamp);
                        intent.putExtra("TYPE", model_user.type);
                        intent.putExtra("RATE", model_user.rate);
                        intent.putExtra("REMARK", remark);
                        context.startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    //RecyclerView.Adapter<Adapter_list_student.HolderUser>
    class Holder_user extends RecyclerView.ViewHolder {
        TextView type, rate;
        ImageButton imgdel;

        public Holder_user(@NonNull View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.TVtype);
            rate = itemView.findViewById(R.id.TVrate);
            imgdel = itemView.findViewById(R.id.imgbtndel);
        }
    }
}

