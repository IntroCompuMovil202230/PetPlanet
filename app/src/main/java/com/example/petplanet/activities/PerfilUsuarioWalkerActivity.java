package com.example.petplanet.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.petplanet.R;

public class PerfilUsuarioWalkerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario_walker);

        TextView nombrepet = findViewById(R.id.nombrePetWalker);
        TextView Telefonopet = findViewById(R.id.TelefonoPetwalker);
        TextView Experiencia = findViewById(R.id.experienciaPetwalker);
        ImageView fotoPet = findViewById(R.id.FotoPetWalker);
        Button agregarfab = findViewById(R.id.agregarFaboritos);
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
        nombrepet.setText(nombre);
        Telefonopet.setText(telefono);
        Experiencia.setText(experiencia);
        fotoPet.setImageResource(foto);

        agregarfab.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), LandingPetOwnerActivity.class);
            startActivity(intent);
            //agregarFavoritos(nombre,telefono,experiencia,foto);
        });

    }

}