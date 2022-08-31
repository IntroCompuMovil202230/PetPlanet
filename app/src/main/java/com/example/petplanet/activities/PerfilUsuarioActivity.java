package com.example.petplanet.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.widget.Toolbar;

import com.example.petplanet.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PerfilUsuarioActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);
        Toolbar tollbarusuario = findViewById(R.id.toolbarPusuario);
        tollbarusuario.setTitle("");
        setSupportActionBar(tollbarusuario);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tollbarusuario.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // luego se pone despues que se revise el rol del usuario a que pantalla ir
                startActivity(new Intent(getApplicationContext(),LandingPetOwnerActivity.class));
                finish();
            }
        });
        Button changePasswordbtn = findViewById(R.id.changepasswordBTN);
        FloatingActionButton addPet = findViewById(R.id.addpet);
        ImageView pet = findViewById(R.id.petPicture);

        changePasswordbtn.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), CambiarPasswordActivity.class);
            startActivity(intent);
            finish();
        });

        pet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PerfilPerroActivity.class);
                startActivity(intent);
                finish();
            }
        });

        addPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegistroPerroActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}