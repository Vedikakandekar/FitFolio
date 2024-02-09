package com.example.tailering1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

public class types_recycler extends AppCompatActivity {
    ImageButton backbtn, addtype;
    private ArrayList<Model_User> user_list = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    Adapter_user adapter_user;
    Model_User model_user;
    RecyclerView RV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_types_recycler);
        backbtn = findViewById(R.id.img_back);
        addtype = findViewById(R.id.img_add_type);
        RV = findViewById(R.id.recycler1);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(types_recycler.this, MainActivity.class);
                startActivity(intent);
            }
        });
        addtype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(types_recycler.this, pagetype.class);
                intent1.putExtra("I_AM_HERE_FOR", "ADD");
                startActivity(intent1);
            }
        });
        loadfrom_firebase();
    }


    private void loadfrom_firebase() {
        try {

            user_list = new ArrayList<>();
            //user_list.add(new Model_User("shirt","50","50000"));

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("type");
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        user_list.clear();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                              //    Toast.makeText(ShowProduct_admin.this, ds.child("productTitle").getValue().toString(), Toast.LENGTH_SHORT).show();
                            model_user = ds.getValue(Model_User.class);
                            user_list.add(model_user);
                        }
                        adapter_user = new Adapter_user(types_recycler.this, user_list);
                        RV.setAdapter(adapter_user);

                    } else {
                        Toast.makeText(types_recycler.this, "No data found", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });


        } catch (Exception ee) {
            Toast.makeText(this, ee.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}