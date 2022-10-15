package com.example.petplanet.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;

import com.example.petplanet.R;
import com.example.petplanet.adapters.CardAdapterIniciarpaseo;
import com.example.petplanet.adapters.CardAdapterUsuario;
import com.example.petplanet.databinding.ActivityChatBinding;
import com.example.petplanet.databinding.ActivityIniciarPaseoBinding;
import com.example.petplanet.models.Perro;
import com.example.petplanet.models.Usuario;

import java.util.ArrayList;

public class IniciarPaseoActivity extends AppCompatActivity {
    private ActivityIniciarPaseoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityIniciarPaseoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayList<Perro> perrosList=new ArrayList<>();
        setSupportActionBar(binding.toolbarPerDispo);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.toolbarPerDispo.setNavigationOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(),LandingPetWalkerActivity.class));
            finish();
        });

        binding.grindDispo.setNumColumns(1);
        binding.grindDispo.setVerticalSpacing(30);
        binding.grindDispo.setHorizontalSpacing(30);
        try {

            ArrayAdapter<Perro> adapter = new CardAdapterIniciarpaseo(this,R.layout.cardviewiniciarpaseo,perrosList);
            if (binding.grindDispo != null) {
                binding.grindDispo.setAdapter(adapter);
            }
            binding.grindDispo.setOnItemClickListener((parent, view, position, id) -> {
                Intent intent = new Intent(getApplicationContext() , ChatActivity.class);
                Perro items = perrosList.get(position);
                intent.putExtra("nombre",items.getNombrecompleto());
                intent.putExtra("imagen",items.getFoto());
                startActivity(intent);
                finish();
            });





        }catch (Exception e) {
            e.printStackTrace();
        }



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