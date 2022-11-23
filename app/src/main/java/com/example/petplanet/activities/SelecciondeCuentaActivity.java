package com.example.petplanet.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.petplanet.databinding.ActivitySelecciondeCuentaBinding;

public class SelecciondeCuentaActivity extends AppCompatActivity {
    private ActivitySelecciondeCuentaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelecciondeCuentaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        setSupportActionBar(binding.toolbarSele);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.toolbarSele.setNavigationOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        });


        binding.petownerCardview.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), RegistroPetOwnerActivity.class);
            startActivity(intent);
            finish();
        });
        binding.petwalkerCardview.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), RegistroPetWalkerActivity.class);
            startActivity(intent);
            finish();
        });
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onRestart() {
        super.onRestart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }


}