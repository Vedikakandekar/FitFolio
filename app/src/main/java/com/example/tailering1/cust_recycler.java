package com.example.tailering1;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class cust_recycler extends AppCompatActivity {
    private ArrayList<Model_User3> user_list;
    private ArrayList<Model_User3> user_list2;


    RecyclerView RV;
    ImageButton backbtn;
    Adapter_user3 adapter_user3;

    private ArrayList<String> sort_arraylist = new ArrayList<>();
    private ArrayAdapter<String> sort_arrayAdapter;
    private ScrollView scroll_view;
    private TextInputEditText sortED;
    private ListView sort_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_recycler);
        RV = findViewById(R.id.recycler3);
        backbtn = findViewById(R.id.backbtn);

        sortED = findViewById(R.id.sortED);
        sort_list = findViewById(R.id.sort_list);
        scroll_view = findViewById(R.id.scroll_view);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(cust_recycler.this, MainActivity.class);
                startActivity(intent);
            }
        });

        sortED.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                scroll_view.setVisibility(View.VISIBLE);
                filter(s.toString());
            }
        });


        loadfromfirebase();
        fetch_data();
    }

    private void fetch_data() {
        try {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("client");
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists()) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            sort_arraylist.add(dataSnapshot.child("customer").getValue().toString());
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(cust_recycler.this, "failed", Toast.LENGTH_SHORT).show();
                }
            });
            sort_arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, sort_arraylist);
            sort_list.setAdapter(sort_arrayAdapter);
            sort_arrayAdapter.notifyDataSetChanged();
            sort_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    sortED.setText(parent.getItemAtPosition(position).toString());
                    scroll_view.setVisibility(View.GONE);

                    user_list2 = new ArrayList<>();

                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = firebaseDatabase.getReference("client");
                    databaseReference.orderByChild("customer").equalTo(sortED.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                if (ds.exists()) {
                                    user_list2.clear();
                                    user_list2.add(new Model_User3(ds.child("customer").getValue().toString(), ds.child("Customer Mob").getValue().toString(), ds.child("totalAmount").getValue().toString(), ((CharSequence) ds.child("date").getValue()), ds.child("timestamp").getValue().toString()));
                                }
                            }
                            adapter_user3 = new Adapter_user3(cust_recycler.this, user_list2);
                            RV.setAdapter(adapter_user3);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }
            });

        } catch (Exception ee) {
            Toast.makeText(cust_recycler.this, ee.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void loadfromfirebase() {

        user_list = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("client");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    user_list.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        user_list.add(new Model_User3(ds.child("customer").getValue().toString(), ds.child("Customer Mob").getValue().toString(), ds.child("totalAmount").getValue().toString(), ((CharSequence) ds.child("date").getValue()), ds.child("timestamp").getValue().toString()));
                    }
                    adapter_user3 = new Adapter_user3(cust_recycler.this, user_list);
                    RV.setAdapter(adapter_user3);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void filter(String s) {
        List<String> templist = new ArrayList<>();
        for (String str : sort_arraylist) {
            if (str.toLowerCase().contains(s.toLowerCase())) {
                templist.add(str);
            }
        }
        sort_arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, templist);
        sort_list.setAdapter(sort_arrayAdapter);
        sort_arrayAdapter.notifyDataSetChanged();
    }


}
