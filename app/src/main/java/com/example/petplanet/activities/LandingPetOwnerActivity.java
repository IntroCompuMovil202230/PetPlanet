package com.example.petplanet.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.petplanet.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class LandingPetOwnerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_pet_owner);

        BottomNavigationView bottomtool = findViewById(R.id.bottom_navigation);
        bottomtool.setBackground(null);
        bottomtool.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
            }
        });


    }


}