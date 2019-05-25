package com.example.poorva.messorganizer2;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button additems,createMenu,createOrderbtn,addVegitable,addSweet,addBase,addRice;
    TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textview = findViewById(R.id.textView);
        createMenu = findViewById(R.id.btnCreateMenu);
        additems = findViewById(R.id.btnadditems);
        createOrderbtn=findViewById(R.id.btnCreateOrder);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected())
        {
            textview.setVisibility(View.INVISIBLE);
        }

        else
        {
          createMenu.setEnabled(false);
          additems.setEnabled(false);
        }


        additems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,AddItem.class);
                i.putExtra("selector",additems.getId());
                startActivity(i);
            }
        });


        createMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,CreateMenu.class);
                startActivity(i);
            }
        });

        createOrderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,CreateOrder.class);
                startActivity(i);
            }
        });

    }
}
