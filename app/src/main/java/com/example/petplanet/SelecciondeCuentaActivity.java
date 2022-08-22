package com.example.petplanet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class SelecciondeCuentaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionde_cuenta);


        CardView petowner =  findViewById(R.id.petowner_cardview);
        CardView petwalker = findViewById(R.id.petwalker_cardview);

        petowner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegistroPetOwnerActivity.class);
                startActivity(intent);
                finish();
            }
        });
        petwalker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegistroPetWalkerActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }









}