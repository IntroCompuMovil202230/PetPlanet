package com.example.petplanet.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.petplanet.R;
import com.example.petplanet.databinding.ActivityPerfilUsuarioBinding;
import com.example.petplanet.databinding.ActivityPerfilUsuarioWalkerBinding;

public class PerfilUsuarioWalkerActivity extends AppCompatActivity {
    private ActivityPerfilUsuarioWalkerBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPerfilUsuarioWalkerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        setSupportActionBar(binding.tollbarPwalker);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.tollbarPwalker.setNavigationOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(),ListarCuidadoresActivity.class));// esto cambia si el usuario es dueño o walker
            finish();
        });


        String nombre,telefono,experiencia;
        int foto;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                nombre= null;
                telefono= null;
                experiencia= null;
                foto= 0;
            } else {
                nombre= extras.getString("nombre");
                telefono= extras.getString("telefono");
                experiencia= extras.getString("experiencia");
                foto= extras.getInt("imagen");
            }
        } else {
            nombre= (String) savedInstanceState.getSerializable("nombre");
            telefono= (String) savedInstanceState.getSerializable("telefono");
            experiencia= (String) savedInstanceState.getSerializable("experiencia");
            foto= (int) savedInstanceState.getSerializable("imagen");
        }
        binding.nombrePetWalker.setText(nombre);
        binding.TelefonoPetwalker.setText(telefono);
        binding.experienciaPetwalker.setText(experiencia);
        binding.FotoPetWalker.setImageResource(foto);

        binding.agregarFaboritos.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), LandingPetOwnerActivity.class);
            startActivity(intent);
            //agregarFavoritos(nombre,telefono,experiencia,foto);
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
        startActivity(new Intent(getApplicationContext(), ListarCuidadoresActivity.class));
        finish();
    }
}