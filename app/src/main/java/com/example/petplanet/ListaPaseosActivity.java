package com.example.petplanet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.gridlayout.widget.GridLayout;

import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListaPaseosActivity extends AppCompatActivity {

    GridView gridLayout;
    ArrayList<Perro> perroslist=new ArrayList<>();
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_paseos);

        Toolbar toolbar = findViewById(R.id.toolbarListar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LandingPetWalkerActivity.class));
            }
        });

        gridLayout = findViewById(R.id.grind);
        gridLayout.setNumColumns(2);
        gridLayout.setVerticalSpacing(30);
        gridLayout.setHorizontalSpacing(30);
        perroslist.add(new Perro("Perro 1","Horario 4pm-6pm",R.drawable.perro1));
        perroslist.add(new Perro("Perro 2","Horario 5pm-6pm",R.drawable.perro2));
        perroslist.add(new Perro("Perro 3","Horario 3pm-6pm",R.drawable.perro3));
        perroslist.add(new Perro("Perro 4","Horario 2pm-4pm",R.drawable.perro1));
        perroslist.add(new Perro("Perro 4","Horario 2pm-4pm",R.drawable.perro1));
        perroslist.add(new Perro("Perro 4","Horario 2pm-4pm",R.drawable.perro1));
        perroslist.add(new Perro("Perro 4","Horario 2pm-4pm",R.drawable.perro1));
        perroslist.add(new Perro("Perro 4","Horario 2pm-4pm",R.drawable.perro1));
        ArrayAdapter adapter = new CardAdapter(this,R.layout.cardview,perroslist);
        gridLayout.setAdapter(adapter);




    }
}