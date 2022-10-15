package com.example.petplanet.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;



import com.example.petplanet.databinding.ActivityPerfilPerroBinding;

public class PerfilPerroActivity extends AppCompatActivity {
    private ActivityPerfilPerroBinding binding;

    String nombreS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPerfilPerroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                nombreS= null;

            } else {
                nombreS = extras.getString("nombredelperro");

            }
        } else {
            nombreS =        (String) savedInstanceState.getSerializable("nombredelperro");
        }

        binding.fullNamePet.setText(nombreS);
        binding.toolbarPperro.setTitle("");
        setSupportActionBar(binding.toolbarPperro);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.toolbarPperro.setNavigationOnClickListener(v -> {
            // luego se pone despues que se revise el rol del usuario a que pantalla ir
            startActivity(new Intent(getApplicationContext(),PerfilUsuarioActivity.class));
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