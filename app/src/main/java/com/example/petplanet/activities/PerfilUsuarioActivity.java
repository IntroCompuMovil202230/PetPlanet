package com.example.petplanet.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.petplanet.databinding.ActivityPerfilUsuarioBinding;


public class PerfilUsuarioActivity extends AppCompatActivity {

    private ActivityPerfilUsuarioBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPerfilUsuarioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        binding.toolbarPusuario.setTitle("");
        setSupportActionBar(binding.toolbarPusuario);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.toolbarPusuario.setNavigationOnClickListener(v -> {
            // luego se pone despues que se revise el rol del usuario a que pantalla ir
            startActivity(new Intent(getApplicationContext(),LandingPetOwnerActivity.class));
            finish();
        });


        binding.changepasswordBTN.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), CambiarPasswordActivity.class);
            startActivity(intent);
            finish();
        });

        binding.petPicture.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), PerfilPerroActivity.class);
            startActivity(intent);
            finish();
        });

        binding.addpet.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), RegistroPerroActivity.class);
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

        //aca toca un if para ver si es dueño o cuidador y mandarlo a la pantalla correspondiente
        startActivity(new Intent(getApplicationContext(), LandingPetOwnerActivity.class));
        finish();
    }
}