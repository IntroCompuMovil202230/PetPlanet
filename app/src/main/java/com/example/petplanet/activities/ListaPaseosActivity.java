package com.example.petplanet.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.example.petplanet.adapters.CardAdapterPerro;
import com.example.petplanet.databinding.ActivityListaDeChatsBinding;
import com.example.petplanet.databinding.ActivityListaPaseosBinding;
import com.example.petplanet.models.Perro;
import com.example.petplanet.R;

import java.util.ArrayList;

public class ListaPaseosActivity extends AppCompatActivity {
    private ActivityListaPaseosBinding binding;
    ArrayList<Perro> perroslist=new ArrayList<>();
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListaPaseosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        setSupportActionBar(binding.toolbarListar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.toolbarListar.setNavigationOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(),LandingPetWalkerActivity.class));
            finish();
        });

        binding.grind.setNumColumns(2);
        binding.grind.setVerticalSpacing(30);
        binding.grind.setHorizontalSpacing(30);

        ArrayAdapter adapter = new CardAdapterPerro(this,R.layout.cardview,perroslist);
        binding.grind.setAdapter(adapter);
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
        startActivity(new Intent(getApplicationContext(), LandingPetWalkerActivity.class));
        finish();
    }

}