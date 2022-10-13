package com.example.petplanet.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.petplanet.R;
import com.example.petplanet.databinding.ActivityIniciarPaseoBinding;
import com.example.petplanet.databinding.ActivityLandingPetOwnerBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class LandingPetOwnerActivity extends AppCompatActivity {
    private ActivityLandingPetOwnerBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLandingPetOwnerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNavigation.setBackground(null);
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            switch (id) {
                case R.id.cuidadores:
                    Intent intent = new Intent(getApplicationContext(), ListarCuidadoresActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.AgendarPaseoBTN:
                    Intent intent2 = new Intent(getApplicationContext(), AgendarPaseosActivity.class);
                    startActivity(intent2);
                    finish();
                    break;
                case R.id.chat:
                    Intent intent3 = new Intent(getApplicationContext(), ListaDeChatsActivity.class);
                    startActivity(intent3);
                    finish();
                    break;
                case R.id.perfilA:
                    Intent intent4 = new Intent(getApplicationContext(), PerfilUsuarioActivity.class);
                    startActivity(intent4);
                    finish();
                    break;
            }
            return true;
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



}