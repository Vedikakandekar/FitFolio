package com.example.tailering1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class registration extends AppCompatActivity {
    TextInputEditText shopname, addr, pass, own, mail, cont;

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        shopname = findViewById(R.id.ET_name);
        addr = findViewById(R.id.ET_addr);
        mail = findViewById(R.id.ET_email);
        pass = findViewById(R.id.ET_pass);
        cont = findViewById(R.id.ET_cont);
        own = findViewById(R.id.ET_own);
        btn = findViewById(R.id.submit);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addtofirebase();
            }
        });


    }

    private void addtofirebase() {
        String namestr, addrstr, passstr, contstr, ownstr, mailstr, timestapmp;
        namestr = shopname.getText().toString();
        addrstr = addr.getText().toString();
        passstr = pass.getText().toString();
        ownstr = own.getText().toString();
        mailstr = mail.getText().toString();
        contstr = cont.getText().toString();

        timestapmp = String.valueOf(System.currentTimeMillis());

        if (!namestr.isEmpty() && !addrstr.isEmpty() && !passstr.isEmpty() && !ownstr.isEmpty() && !contstr.isEmpty() && !mailstr.isEmpty()) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("name", namestr);
            hashMap.put("addr", addrstr);
            hashMap.put("own", ownstr);
            hashMap.put("cont", contstr);
            hashMap.put("mail", mailstr);
            hashMap.put("pass", passstr);

            DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("users");
            ref1.orderByChild("cont").equalTo(contstr).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists()) {
                        Toast.makeText(registration.this, "Mobile Number Already Exists!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        ref1.child(timestapmp).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(registration.this, "User Created", Toast.LENGTH_SHORT).show();
                                shopname.setText("");
                                cont.setText("");
                                addr.setText("");
                                own.setText("");
                                mail.setText("");
                                pass.setText("");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(registration.this, e.toString(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        } else {
            Toast.makeText(registration.this, "Every Field is Mandatory", Toast.LENGTH_SHORT).show();
        }

    }
}