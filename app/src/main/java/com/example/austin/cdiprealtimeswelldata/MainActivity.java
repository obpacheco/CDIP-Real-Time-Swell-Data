package com.example.austin.cdiprealtimeswelldata;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button northernCalifornia;
    private Button montereyBay;
    private Button centralCoast;
    private Button southernCalifornia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        northernCalifornia = (Button) findViewById(R.id.northern_california);
        northernCalifornia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNorthernCalifornia();
            }
        });

        montereyBay = (Button) findViewById(R.id.monterey_bay);
        montereyBay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMontereyBay();
            }
        });

        centralCoast = (Button) findViewById(R.id.central_coast);
        centralCoast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCentralCoast();
            }
        });

        southernCalifornia = (Button) findViewById(R.id.southern_california);
        southernCalifornia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSouthernCalifornia();
            }
        });


    }


    public void openNorthernCalifornia(){
        Intent intent = new Intent(this, NorthernCalifornia.class);
        startActivity(intent);
    }

    public void openMontereyBay(){
        Intent intent = new Intent(this, MontereyBay.class);
        startActivity(intent);
    }

    public void openCentralCoast(){
        Intent intent = new Intent(this, CentralCoast.class);
        startActivity(intent);
    }

    public void openSouthernCalifornia(){
        Intent intent = new Intent(this, SouthernCalifornia.class);
        startActivity(intent);
    }


}


