package com.example.petplanet.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.petplanet.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class LandingPetWalkerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_pet_walker);


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
                    case R.id.IniciarPaseoBTN:
                        Intent intent2 = new Intent(getApplicationContext(), IniciarPaseoActivity.class);
                        startActivity(intent2);
                    case R.id.chat:
                        Intent intent3 = new Intent(getApplicationContext(), ChatActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.perfilA:
                        Intent intent4 = new Intent(getApplicationContext(), PerfilUsuarioActivity.class);
                        startActivity(intent4);
                        break;
                }
                return true;
            }
        });


    }
}