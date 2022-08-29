package com.example.petplanet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class LandingPetWalkerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_pet_walker);

        FloatingActionButton iniciarBTN = findViewById(R.id.IniciarPaseoBTN);

        BottomNavigationView bottomtool = findViewById(R.id.bottom_navigationWalker);
        bottomtool.setBackground(null);
        bottomtool.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.mascotas:
                        Intent intent = new Intent(getApplicationContext(), ListaPaseosActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.chat:
                        Intent intent2 = new Intent(getApplicationContext(), ChatActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.perfilA:
                        Intent intent3 = new Intent(getApplicationContext(), PerfilUsuarioActivity.class);
                        startActivity(intent3);
                        break;
                }
                return true;
            }
        });

        iniciarBTN.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), IniciarPaseoActivity.class);
            startActivity(intent);

        });
    }
}