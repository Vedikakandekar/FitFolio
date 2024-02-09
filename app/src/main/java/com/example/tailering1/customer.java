package com.example.tailering1;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class customer extends AppCompatActivity {
    private static final int CAMERA_REQUEST_CODE = 200;
    private static final int STORAGE_REQUEST_CODE = 300;
    //IMAGE
    private static final int IMAGE_PICK_GALLARY_CODE = 400;
    private static final int IMAGE_PICK_CAMERA_CODE = 500;
    //permission arrays
    private String[] cameraPermissions;
    private String[] StoragePermissions;
    //image path

    private Uri image_uril;
    ImageView pickimg;
    CardView cardcust1;

    private TextInputEditText ETcust;
    private ListView custList;
    private ScrollView scrollView1;

    private TextInputEditText ETtype;
    private ListView typeList;
    private ScrollView scrollView2;

    private ListView remarklist;

    private TextInputEditText ETViewaddr, ETviewmob, ETviewnm, ETqty, ETviewamt;
    ImageButton imgbtnadd;
    CardView cardcust2;
    Button btnsubmit3;

    Button btnsubmit2;

    TextInputEditText ET1, ET2, ET3, ET4, ET5, ET6, ET7, ET8, ET9, ET10, ET11, ET12;

    TextInputEditText ETviewrate;

    private ArrayList<String> cust_ArrayList = new ArrayList<>();
    ArrayAdapter<String> cust_arrayAdapter;

    private ArrayList<String> type_Arraylist = new ArrayList<>();
    ArrayAdapter<String> type_arrayAdapter;

    HashMap<String, String> hashMap1;

    private ArrayList<String> remarks_ArrayList = new ArrayList<>();
    ArrayAdapter<String> remarks_arrayAdapter;

    ListView listDemo;

    ImageButton imgbtnadd2;

    ArrayList<Model_User2> user_list = new ArrayList<Model_User2>();
    HashMap<String, String> rhashMap;
    Adapter_User2 adapter_user2;

    HashMap<String, Object> dbhashmap;

    HashMap<String, String> custinfohashmap;

    RecyclerView RV;

    TextView showremarks;
    ScrollView scrollremarks;
    RecyclerView recycler2;
   static ArrayList<Double> totalamt_arraylist = new ArrayList<>();
   static TextView TVamt;

    ArrayList<String> touser_arraylist = new ArrayList<>();

    private DatePickerDialog datePickerDialog;
    private Button dateButton;

    String here_for;
    TextView addupdt;

    String timestamp_update;
    HashMap<String, String> getdata;

    String finaltimestamp;
    String custMobstr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        ETcust = findViewById(R.id.ETcust);
        custList = findViewById(R.id.custList);
        scrollView1 = findViewById(R.id.view_scroll1);

        ETtype = findViewById(R.id.ETtype);
        typeList = findViewById(R.id.typeList);
        scrollView2 = findViewById(R.id.view_scroll2);

        hashMap1 = new HashMap<>();

        custinfohashmap = new HashMap<>();

        remarklist = findViewById(R.id.remarkList);
        showremarks = findViewById(R.id.showremarks);
        scrollremarks = findViewById(R.id.scrollremarks);

        recycler2 = findViewById(R.id.recycler2);

        ET1 = findViewById(R.id.ET1);
        ET2 = findViewById(R.id.ET2);
        ET3 = findViewById(R.id.ET3);
        ET4 = findViewById(R.id.ET4);
        ET5 = findViewById(R.id.ET5);
        ET6 = findViewById(R.id.ET6);
        ET7 = findViewById(R.id.ET7);
        ET8 = findViewById(R.id.ET8);
        ET9 = findViewById(R.id.ET9);
        ET10 = findViewById(R.id.ET10);
        ET11 = findViewById(R.id.ET11);
        ET12 = findViewById(R.id.ET12);

        btnsubmit2 = findViewById(R.id.btnsubmit2);

        pickimg = findViewById(R.id.pickimg);
        cardcust1 = findViewById(R.id.cardcust1);

        imgbtnadd = findViewById(R.id.imgbtnadd);
        cardcust2 = findViewById(R.id.cardcust2);
        ETViewaddr = findViewById(R.id.ETviewaddr);
        ETviewnm = findViewById(R.id.ETviewnm);
        ETviewmob = findViewById(R.id.ETviewmob);
        btnsubmit3 = findViewById(R.id.btnsubmit3);

        ETqty = findViewById(R.id.ETqty);
        ETviewrate = findViewById(R.id.ETviewrate);
        ETviewamt = findViewById(R.id.ETviewamt);

        imgbtnadd2 = findViewById(R.id.imgbtnadd2);
        dbhashmap = new HashMap<>();
        rhashMap = new HashMap<>();

        RV = findViewById(R.id.recycler2);

        TVamt = findViewById(R.id.TVamt);

        addupdt = findViewById(R.id.addupdt);
        finaltimestamp = String.valueOf(System.currentTimeMillis());

        here_for = getIntent().getStringExtra("Here_for");

        if (here_for.equals("ADD")) {
            addupdt.setText("New Order");

        } else if (here_for.equals("UPDATE")) {
            getdata = new HashMap<>();
            addupdt.setText("Update Type");
            ETcust.setText(getIntent().getStringExtra("custname"));
            TVamt.setText(getIntent().getStringExtra("totalamt"));
            timestamp_update = getIntent().getStringExtra("timestamp");
            custMobstr=getIntent().getStringExtra("custmob");
            //fetch data from db for update
            try {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("client");
                databaseReference.orderByChild("timestamp").equalTo(timestamp_update).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                for (int j = 0; j < ds.child("data").getChildrenCount(); j++){
                                    ArrayList<String> testlist = new ArrayList<>();
                                    HashMap<String, String> testhash = new HashMap<>();
                                    for(int k=0;k<ds.child("data").child(String.valueOf(j)).child("remarks").getChildrenCount();k++) {
                                        testlist.add(ds.child("data").child(String.valueOf(j)).child("remarks").child(String.valueOf(k)).getValue().toString().replace("[", "").replace("]", ""));
                                    }
                                    Toast.makeText(customer.this, String.valueOf(j), Toast.LENGTH_SHORT).show();

                                    testhash.put("M1",ds.child("data").child(String.valueOf(j)).child("measurnments").child("M1").getValue().toString());
                                    testhash.put("M2",ds.child("data").child(String.valueOf(j)).child("measurnments").child("M2").getValue().toString());
                                    testhash.put("M3",ds.child("data").child(String.valueOf(j)).child("measurnments").child("M3").getValue().toString());
                                    testhash.put("M4",ds.child("data").child(String.valueOf(j)).child("measurnments").child("M4").getValue().toString());
                                    testhash.put("M5",ds.child("data").child(String.valueOf(j)).child("measurnments").child("M5").getValue().toString());
                                    testhash.put("M6",ds.child("data").child(String.valueOf(j)).child("measurnments").child("M6").getValue().toString());
                                    testhash.put("M7",ds.child("data").child(String.valueOf(j)).child("measurnments").child("M7").getValue().toString());
                                    testhash.put("M8",ds.child("data").child(String.valueOf(j)).child("measurnments").child("M8").getValue().toString());
                                    testhash.put("M9",ds.child("data").child(String.valueOf(j)).child("measurnments").child("M9").getValue().toString());
                                    testhash.put("M10",ds.child("data").child(String.valueOf(j)).child("measurnments").child("M10").getValue().toString());
                                    testhash.put("M11",ds.child("data").child(String.valueOf(j)).child("measurnments").child("M11").getValue().toString());
                                    testhash.put("M12",ds.child("data").child(String.valueOf(j)).child("measurnments").child("M12").getValue().toString());

                                    user_list.add(new Model_User2(ds.child("data").child(String.valueOf(j)).child("type").getValue().toString(), Double.parseDouble(ds.child("data").child(String.valueOf(j)).child("amt").getValue().toString()), Double.parseDouble(ds.child("data").child(String.valueOf(j)).child("rate").getValue().toString()), Integer.parseInt(ds.child("data").child(String.valueOf(j)).child("qty").getValue().toString()), testlist, testhash, timestamp_update));
                               dbhashmap.put("data",user_list);
                                }
                            }
                           adapter_user2 = new Adapter_User2(customer.this, user_list);
                           RV.setAdapter(adapter_user2);
                        }
                        else
                        {
                            Toast.makeText(customer.this, "No data exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("errrr", error.toString());
                    }
                });
            } catch (Exception ee) {
                Toast.makeText(customer.this, ee.toString(), Toast.LENGTH_SHORT).show();

            }
        }

        initDatePicker();

        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());

        showremarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (scrollremarks.getVisibility() == View.VISIBLE) {
                    scrollremarks.setVisibility(View.GONE);
                } else {
                    scrollremarks.setVisibility(View.VISIBLE);
                }
            }
        });


        remarklist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                touser_arraylist.clear();

                SparseBooleanArray clickedItemPositions = remarklist.getCheckedItemPositions();
                for (int index=0; index<clickedItemPositions.size();index++) {
                    // Get the checked status of the current item
                    boolean checked = clickedItemPositions.valueAt(index);

                    if (checked) {
                        // If the current item is checked
                        int key = clickedItemPositions.keyAt(index);
                        String item = (String) remarklist.getItemAtPosition(key);
                        // add items touser_arraylist
                        touser_arraylist.add(item);
                    }
                }
            }
        });

        imgbtnadd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //user_list.add(type,qty,amt,measurenments , remarks ,rate)
                rhashMap.clear();
                rhashMap.put("M1", ET1.getText().toString());
                rhashMap.put("M2", ET2.getText().toString());
                rhashMap.put("M3", ET3.getText().toString());
                rhashMap.put("M4", ET4.getText().toString());
                rhashMap.put("M5", ET5.getText().toString());
                rhashMap.put("M6", ET6.getText().toString());
                rhashMap.put("M7", ET7.getText().toString());
                rhashMap.put("M8", ET8.getText().toString());
                rhashMap.put("M9", ET9.getText().toString());
                rhashMap.put("M10", ET10.getText().toString());
                rhashMap.put("M11", ET11.getText().toString());
                rhashMap.put("M12", ET12.getText().toString());

                String viewamtstr = ETviewamt.getText().toString();

                totalamt_arraylist.add(Double.parseDouble(viewamtstr));
                double sum = 0.0;
                for (double no : totalamt_arraylist) {
                    sum = sum + no;
                }
                TVamt.setText(String.valueOf(sum));

                dbhashmap.put("data", user_list);
                dbhashmap.put("totalAmount",TVamt.getText().toString());

//                if (!typestr.isEmpty()) {
//                    ETtype.setError(null);
//                    if (!qtystr.isEmpty()) {
//                        ETqty.setError(null);
//                        if (!m1str.isEmpty()) {
//                            ET1.setError(null);
//                            if (!m2str.isEmpty()) {
//                                ET2.setError(null);
//                                if (!m3str.isEmpty()) {
//                                    ET3.setError(null);
//                                    if (!m4str.isEmpty()) {
//                                        ET4.setError(null);
//                                        if (!m5str.isEmpty()) {
//                                            ET5.setError(null);
//                                            if (!m6str.isEmpty()) {
//                                                ET6.setError(null);
//                                                if (!m7str.isEmpty()) {
//                                                    ET7.setError(null);
//                                                    if (!m8str.isEmpty()) {
//                                                        ET8.setError(null);
//                                                        if (!m9str.isEmpty()) {
//                                                            ET9.setError(null);
//                                                            if (!m10str.isEmpty()) {
//                                                                ET10.setError(null);
//                                                                if (!m11str.isEmpty()) {
//                                                                    ET11.setError(null);
//                                                                    if (!m12str.isEmpty()) {
//                                                                        ET12.setError(null);
//                                                                        if (!m1str.isEmpty()) {
//                                                                            ET1.setError(null);
//                                                                            if (!viewratestr.isEmpty()) {
//                                                                                ETviewrate.setError(null);
//                                                                                if (!viewamtstr.isEmpty()) {
//                                                                                    ETviewamt.setError(null);
//
//                                                                                } else {
//                                                                                    ETviewamt.setText("Please Enter The Amount");
//                                                                                }
//                                                                            } else {
//                                                                                ETviewrate.setText("Please Enter The Amount");
//                                                                            }
//                                                                        }
//                                                                    }else {
//                                                                            ETviewamt.setText("Please Enter The Amount");
//                                                                        }
//                                                                    } else {
//                                                                        ETviewamt.setText("Please Enter The Amount");
//                                                                    }
//                                                                } else {
//                                                                    ETviewamt.setText("Please Enter The Amount");
//                                                                }
//                                                            } else {
//                                                                ETviewamt.setText("Please Enter The Amount");
//                                                            }
//                                                        } else {
//                                                            ETviewamt.setText("Please Enter The Amount");
//                                                        }
//                                                    } else {
//                                                        ETviewamt.setText("Please Enter The Amount");
//                                                    }
//                                                } else {
//                                                    ETviewamt.setText("Please Enter The Amount");
//                                                }
//                                            } else {
//                                                ETviewamt.setText("Please Enter The Amount");
//                                            }
//                                        } else {
//                                            ETviewamt.setText("Please Enter The Amount");
//                                        }
//                                    } else {
//                                        ETviewamt.setText("Please Enter The Amount");
//                                    }
//                                } else {
//                                    ETviewamt.setText("Please Enter The Amount");
//                                }
//                            } else {
//                                ETviewamt.setText("Please Enter The Amount");
//                            }
//                        } else {
//                            ETviewamt.setText("Please Enter The Amount");
//                        }
//                    }
//                } else {
//                    ETviewamt.setText("Please Enter The Amount");
//                }
//            }
//                                                                        }

                user_list.add(new Model_User2(ETtype.getText().toString(), Double.parseDouble(ETviewamt.getText().toString()), Double.parseDouble(ETviewrate.getText().toString()), Integer.parseInt(ETqty.getText().toString()), touser_arraylist, rhashMap, finaltimestamp));
                adapter_user2 = new Adapter_User2(customer.this, user_list);
                RV.setAdapter(adapter_user2);
                adapter_user2.notifyDataSetChanged();

                ETviewrate.setText("");
                ETviewamt.setText("");
                ETqty.setText("");
                ETtype.setText("");
                ET1.setText("");
                ET2.setText("");
                ET3.setText("");
                ET4.setText("");
                ET5.setText("");
                ET6.setText("");
                ET7.setText("");
                ET8.setText("");
                ET9.setText("");
                ET10.setText("");
                ET11.setText("");
                ET12.setText("");
            }
        });

        //image permission
        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        StoragePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        cardcust1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(customer.this,"card clicked",Toast.LENGTH_SHORT).show();
                showImagePickDialog();

            }
        });


        btnsubmit3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addtofirebase();
            }
        });

//        RV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                double sum = 0.0;
//                for (double no : totalamt_arraylist) {
//                    sum = sum + no;
//                }
//                TVamt.setText(String.valueOf(sum));
//              customer.this.notifyAll();
//                Toast.makeText(customer.this, ""+totalamt_arraylist, Toast.LENGTH_SHORT).show();
//            }
//        });


        ETcust.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                scrollView1.setVisibility(View.VISIBLE);
                filter(s.toString());
            }
        });

        ETtype.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
//                if(s.equals(null))
//                {
//                  //  scrollView2.setVisibility(View.GONE);
//
//                }
//                else {
                scrollView2.setVisibility(View.VISIBLE);
                filter2(s.toString());


                //  }
            }
        });


        //fetch types from database "Type"

        FirebaseDatabase firebaseDatabase2 = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference2 = firebaseDatabase2.getReference("type");
        ValueEventListener valueEventListener2 = databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childsnapshot : snapshot.getChildren()) {
                    type_Arraylist.add((String) childsnapshot.child("type").getValue());
                    hashMap1.put(childsnapshot.child("type").getValue().toString(), childsnapshot.child("rate").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(customer.this, "failed", Toast.LENGTH_SHORT).show();

            }
        });

        type_arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, type_Arraylist);
        typeList.setAdapter(type_arrayAdapter);
        type_arrayAdapter.notifyDataSetChanged();

        typeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                ETtype.setText(parent.getItemAtPosition(position).toString());

                for (String str : hashMap1.keySet()) {
                    if (hashMap1.containsKey(ETtype.getText().toString())) {
                        ETviewrate.setText(hashMap1.get(ETtype.getText().toString()));
                    }
                }

                ArrayList<String> temp_ArrayList = new ArrayList<>();

                //fetch Remarks from type DB in arraylist
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference("type");
                databaseReference.orderByChild("type").equalTo(ETtype.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {

                            for (DataSnapshot childsnapshot : snapshot.getChildren()) {
                                temp_ArrayList.add(childsnapshot.child("remark").getValue().toString());
                            }
                            String[] items2 = temp_ArrayList.toString().split("\\s*,\\s*");
                            List<String> list_temp = Arrays.asList(items2);
                            remarks_ArrayList.clear();
                            for (String sss : list_temp) {

                                remarks_ArrayList.add(sss.replace("[", "").replace("]", ""));
                            }
                            remarks_arrayAdapter = new ArrayAdapter<String>(customer.this, android.R.layout.simple_list_item_multiple_choice, remarks_ArrayList);
                            remarklist.setChoiceMode(remarklist.CHOICE_MODE_MULTIPLE);
                            remarklist.setTextFilterEnabled(true);
                            remarklist.setAdapter(remarks_arrayAdapter);

                        }

                        temp_ArrayList.clear();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                scrollView2.setVisibility(View.GONE);


            }


        });

        ETqty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String ETratestr, ETqtystr;
                ETratestr = ETviewrate.getText().toString();
                ETqtystr = ETqty.getText().toString();
                if (!ETratestr.isEmpty() && !ETqtystr.isEmpty()) {
                    int prod = Integer.parseInt(ETratestr) * Integer.parseInt(ETqtystr);
                    ETviewamt.setText(String.valueOf(prod));
                }


            }
        });


        //fetch customer names from DB
        FirebaseDatabase firebaseDatabase1 = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference1 = firebaseDatabase1.getReference("customer");
        ValueEventListener valueEventListener1 = databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childsnapshot : snapshot.getChildren()) {
                    cust_ArrayList.add((String) childsnapshot.child("name").getValue());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(customer.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });

        cust_arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, cust_ArrayList);
        custList.setAdapter(cust_arrayAdapter);
        cust_arrayAdapter.notifyDataSetChanged();
        custList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ETcust.setText(parent.getItemAtPosition(position).toString());
                scrollView1.setVisibility(View.GONE);

                //fetch mob and add of customer
                FirebaseDatabase firebaseDatabase1 = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference1 = firebaseDatabase1.getReference("customer");
                databaseReference1.orderByChild("name").equalTo(ETcust.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot childsnapshot : snapshot.getChildren()) {
                            if (childsnapshot.exists()) {
                                custinfohashmap.clear();
                                custinfohashmap.put("mob", childsnapshot.child("mob").getValue().toString());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(customer.this, "failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        btnsubmit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (here_for.equals("ADD")) {
                    addcusttofirebase();
                } else if (here_for.equals("UPDATE")) {
                    updatetofirebase();
                }

            }
        });

        imgbtnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardcust2.setVisibility(View.VISIBLE);

            }
        });

    }

    private void updatetofirebase() {

        String customerstr, typestr;
        customerstr = ETcust.getText().toString();

        dbhashmap.put("customer",customerstr);
        dbhashmap.put("date",dateButton.getText().toString());
        dbhashmap.put("Customer Mob",custMobstr);

        FirebaseDatabase firebaseDatabase1 = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference1 = firebaseDatabase1.getReference("client");
        databaseReference1.child(timestamp_update).updateChildren(dbhashmap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(customer.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(customer.this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void addcusttofirebase() {

        String customerstr, typestr;
        customerstr = ETcust.getText().toString();
        typestr = ETtype.getText().toString();
        finaltimestamp = String.valueOf(System.currentTimeMillis());

        dbhashmap.put("customer", customerstr);
        HashMap<String, Object> hashmap1 = new HashMap<>();
        dbhashmap.put("timestamp", finaltimestamp);
        dbhashmap.put("date", dateButton.getText().toString());
        dbhashmap.put("Customer Mob",custinfohashmap.get("mob"));

        FirebaseDatabase firebaseDatabase1 = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference1 = firebaseDatabase1.getReference("client");
        databaseReference1.child(finaltimestamp).setValue(dbhashmap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(customer.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                ETcust.setText("");
                ETtype.setText("");
                ET1.setText("");
                ET2.setText("");
                ET3.setText("");
                ET4.setText("");
                ET5.setText("");
                ET6.setText("");
                ET7.setText("");
                ET8.setText("");
                ET9.setText("");
                ET10.setText("");
                ET11.setText("");
                ET12.setText("");
                TVamt.setText("");
                totalamt_arraylist.clear();
                user_list.clear();
                adapter_user2 = new Adapter_User2(customer.this, user_list);
                RV.setAdapter(adapter_user2);
                adapter_user2.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(customer.this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = makeDateString(dayOfMonth, month, year);
                dateButton.setText(date);

            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = android.app.AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }

    private String makeDateString(int day, int month, int year) {
        return day + " " + getMonthFormat(month) + " " + year;

    }

    private String getMonthFormat(int month) {
        if (month == 1)
            return "JAN";
        if (month == 2)
            return "FEB";
        if (month == 3)
            return "MAR";
        if (month == 4)
            return "APR";
        if (month == 5)
            return "MAY";
        if (month == 6)
            return "JUN";
        if (month == 7)
            return "JUL";
        if (month == 8)
            return "AUG";
        if (month == 9)
            return "SEP";
        if (month == 10)
            return "OCT";
        if (month == 11)
            return "NOV";
        if (month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }

    private void addtofirebase() {

        String nmstr, mobstr, addrstr, timestamp, datestr;
        nmstr = ETviewnm.getText().toString();
        addrstr = ETViewaddr.getText().toString();
        mobstr = ETviewmob.getText().toString();

        timestamp = String.valueOf(System.currentTimeMillis());

        if (!nmstr.isEmpty() && !addrstr.isEmpty() && !mobstr.isEmpty()) {
            HashMap<String, Object> hashMap = new HashMap<>();

            hashMap.put("name", nmstr);
            hashMap.put("add", addrstr);
            hashMap.put("mob", mobstr);

            DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("customer");
            reference1.orderByChild("mob").equalTo(mobstr).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists()) {
                        Toast.makeText(customer.this, "Mobile Number already exists!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        reference1.child(timestamp).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(customer.this, "Customer Added Successfully!!!", Toast.LENGTH_SHORT).show();
                                ETviewnm.setText("");
                                ETViewaddr.setText("");
                                ETviewmob.setText("");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else {
            Toast.makeText(customer.this, "Please Enter Every Field", Toast.LENGTH_SHORT).show();

        }

    }


    private void filter2(String toString) {
        List<String> templist2 = new ArrayList<>();
        for (String str2 : type_Arraylist) {
            if (str2.contains(toString.toLowerCase())) {
                templist2.add(str2);
            }
        }
        type_arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, templist2);
        typeList.setAdapter(type_arrayAdapter);
        type_arrayAdapter.notifyDataSetChanged();
    }

    private void filter(String s) {
        List<String> templist1 = new ArrayList<>();
        for (String str1 : cust_ArrayList) {
            if (str1.toLowerCase().contains(s.toLowerCase())) {
                templist1.add(str1);
            }
        }
        cust_arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, android.R.id.text1, templist1);
        custList.setAdapter(cust_arrayAdapter);
        cust_arrayAdapter.notifyDataSetChanged();
    }

    private void showImagePickDialog() {
        String[] options = {"Camera", "Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick Image")
                .setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {
                            if (checkCameraPermission()) {
                                //permission granted
                                pickFromCamera();
                            } else {
                                //permission not granted ,request
                                requestCameraPermission();
                            }
                        } else {
                            //Toast.makeText(singup_page1.this, "gallery clicked", Toast.LENGTH_SHORT).show();
                            //gallery clicked
                            if (checkStoragePermission()) {
                                //permission granted
                                PickFromGallery();
                            } else {
                                //permission not granted , request
                                requestStoragePermission();
                            }
                        }
                    }
                }).show();
    }

    private void PickFromGallery() {
        //intent to pick image from gallery
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_GALLARY_CODE);
    }

    private void pickFromCamera() {
        //using media to pick
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE, "Temp_Image_Title");
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, "Temp_Image_Description");
        image_uril = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, image_uril);
        startActivityForResult(intent, IMAGE_PICK_CAMERA_CODE);
    }

    private boolean checkStoragePermission
            () {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                (PackageManager.PERMISSION_GRANTED);
        return result;

    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this, StoragePermissions, STORAGE_REQUEST_CODE);

    }

    private boolean checkCameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;

    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, cameraPermissions, CAMERA_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && storageAccepted) {
                        //both granted
                        pickFromCamera();
                    } else {
                        Toast.makeText(customer.this, "Camera and storage permission are requird11", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            case STORAGE_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (storageAccepted) {
                        //permisson granted
                        PickFromGallery();
                    } else {
                        //permisson denied
                        Toast.makeText(this, "Storage permission is requried..11", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(
            int requestCode, int resultCode,
            @Nullable Intent data) {
        try {
            if (resultCode == RESULT_OK) {
                Toast.makeText(customer.this, "result ok" + String.valueOf(resultCode), Toast.LENGTH_SHORT).show();
                if (requestCode == IMAGE_PICK_GALLARY_CODE) {
                    //image picked from gallery
                    //save picked img uri
                    image_uril = data.getData();
                    pickimg.setImageURI(image_uril);
                } else if (requestCode == IMAGE_PICK_CAMERA_CODE) {
                    //image picked from camera
                    //image_uril = data.getData();
                    pickimg.setImageURI(image_uril);
                }
            }
        } catch (Exception ee) {
            Toast.makeText(customer.this, ee.toString(), Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}