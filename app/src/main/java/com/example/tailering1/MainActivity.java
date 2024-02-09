package com.example.tailering1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
    ImageView imgperson1;
    BottomNavigationView btmview;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgperson1=findViewById(R.id.imgperson);
        btmview=findViewById(R.id.btmview);

        homefrag homefrag=new homefrag();
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.relative2,homefrag);
        fragmentTransaction.commit();

        btmview.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        homefrag homefrag = new  homefrag();
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.relative2,homefrag);
                        fragmentTransaction.commit();
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.search:
                       Intent intent=new Intent(MainActivity.this,customer.class);
                       intent.putExtra("Here_for","ADD");
                       startActivity(intent);
                        return true;


                    case R.id.dash:
                        Intent intent1=new Intent(MainActivity.this,types_recycler.class);
                        startActivity(intent1);
                        return true;



              case R.id.history:
                  Intent intent2=new Intent(MainActivity.this,cust_recycler.class);
                  startActivity(intent2);
                  return true;

                }

                return false;
            }
        });







        imgperson1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MainActivity.this ,registration.class);
                startActivity(intent);
            }
        });

    }


}