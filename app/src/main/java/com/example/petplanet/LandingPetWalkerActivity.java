package com.example.petplanet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class LandingPetWalkerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_pet_walker);

        ImageButton lista = findViewById(R.id.listaPaseos);
        ImageButton iniciar = findViewById(R.id.iniciarPaseoA);
        ImageButton perfil = findViewById(R.id.perfilA);

        lista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListaPaseosActivity.class);
                startActivity(intent);
            }
        });
        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), IniciarPaseoActivity.class);
                startActivity(intent);
            }
        });
        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PerfilUsuarioActivity.class);
                startActivity(intent);
            }
        });
    }
}