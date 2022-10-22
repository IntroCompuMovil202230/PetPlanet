package com.example.petplanet.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;


import com.example.petplanet.databinding.ActivityPerfilPerroBinding;

public class PerfilPerroActivity extends AppCompatActivity {
    private ActivityPerfilPerroBinding binding;

    String nombreS,fotoS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPerfilPerroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                nombreS = null;
                fotoS = null;
            } else {
                nombreS = extras.getString("nombredelperro");
                fotoS = extras.getString("fotodelperro");

            }
        } else {
            nombreS = (String) savedInstanceState.getSerializable("nombredelperro");
            fotoS = (String) savedInstanceState.getSerializable("fotodelperro");
        }

        binding.fullNamePet.setText(nombreS);
        byte[] decodedString = Base64.decode(fotoS, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        binding.profilePetPicture.setImageBitmap(decodedByte);
        binding.toolbarPperro.setTitle("");
        setSupportActionBar(binding.toolbarPperro);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.toolbarPperro.setNavigationOnClickListener(v -> {
            // luego se pone despues que se revise el rol del usuario a que pantalla ir
            startActivity(new Intent(getApplicationContext(), PerfilUsuarioActivity.class));
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
        startActivity(new Intent(getApplicationContext(), PerfilUsuarioActivity.class));
        finish();
    }
}