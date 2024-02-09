package com.example.tailering1;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class pagetype extends AppCompatActivity {
    TextInputEditText type, rate, remark;
    Button btn;
    ImageButton imgbtnadd;
    ListView listviewremarks;
    String timestamp_update;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    String here_for;
    TextView addupdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagetype);
        type = findViewById(R.id.EDtype);
        rate = findViewById(R.id.EDrate);
        remark = findViewById(R.id.EDremark);
        btn = findViewById(R.id.btnsubmit);
        imgbtnadd = findViewById(R.id.imgbtnadd);
        listviewremarks = findViewById(R.id.listviewremarks);
        addupdt = findViewById(R.id.addupdt);
        here_for = getIntent().getStringExtra("I_AM_HERE_FOR");
        if (here_for.equals("ADD")) {
            addupdt.setText("Add Type");

        } else if (here_for.equals("UPDATE")) {
            addupdt.setText("Update Type");
            type.setText(getIntent().getStringExtra("TYPE"));
            rate.setText(getIntent().getStringExtra("RATE"));
            timestamp_update = getIntent().getStringExtra("TIMESTAMP");
            ArrayList<String> arrayListtemp = (ArrayList<String>) getIntent().getSerializableExtra("REMARK");
            String[] items2 = arrayListtemp.toString().split("\\s*,\\s*");
            List<String> list_temp = Arrays.asList(items2);
            for (String sss : list_temp) {
                arrayList.add(sss.replace("[", "").replace("]", ""));
            }
        }

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, android.R.id.text1, arrayList);
        listviewremarks.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();

        imgbtnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_to_list();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (here_for.equals("ADD")) {
                    addtofiberbase();
                } else if (here_for.equals("UPDATE")) {
                    updatetofiebase();
                }
            }
        });

    }

    private void updatetofiebase() {

        String typestr, ratestr, remarkstr;
        typestr = type.getText().toString();
        ratestr = rate.getText().toString();


        if (!typestr.isEmpty() && !ratestr.isEmpty()) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("type", typestr);
            hashMap.put("rate", ratestr);
            hashMap.put("remark", arrayList);
            DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("type");
            reference2.child(timestamp_update).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(pagetype.this, "Type Updated", Toast.LENGTH_SHORT).show();
                    type.setText("");
                    rate.setText("");
                    remark.setText("");
                    arrayList.clear();
                    arrayAdapter = new ArrayAdapter<String>(pagetype.this, android.R.layout.simple_spinner_dropdown_item, android.R.id.text1, arrayList);
                    listviewremarks.setAdapter(arrayAdapter);
                    arrayAdapter.notifyDataSetChanged();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(pagetype.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void add_to_list() {
        arrayList.add(remark.getText().toString());
        remark.setText("");
        arrayAdapter.notifyDataSetChanged();
    }


    private void addtofiberbase() {
        String typestr, ratestr,  timestamp;
        typestr = type.getText().toString();
        ratestr = rate.getText().toString();

        timestamp = String.valueOf(System.currentTimeMillis());
        if (!typestr.isEmpty() && !ratestr.isEmpty()) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("type", typestr);
            hashMap.put("rate", ratestr);
            hashMap.put("remark", arrayList);
            hashMap.put("timestamp",timestamp);

            DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("type");
            reference1.orderByChild("type".toLowerCase()).equalTo(type.getText().toString().toLowerCase()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        Toast.makeText(pagetype.this, "Type already Exists", Toast.LENGTH_SHORT).show();
                    } else {
                        reference1.child(timestamp).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(pagetype.this, "Type Created", Toast.LENGTH_SHORT).show();
                                type.setText("");
                                rate.setText("");
                                remark.setText("");


                                arrayList.clear();
                                arrayAdapter = new ArrayAdapter<String>(pagetype.this, android.R.layout.simple_spinner_dropdown_item, android.R.id.text1, arrayList);
                                listviewremarks.setAdapter(arrayAdapter);
                                arrayAdapter.notifyDataSetChanged();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(pagetype.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        } else {
            Toast.makeText(pagetype.this, "type and rate are mandatory", Toast.LENGTH_SHORT).show();
        }
    }
}
