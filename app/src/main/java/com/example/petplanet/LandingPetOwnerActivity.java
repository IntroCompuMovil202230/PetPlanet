package com.example.petplanet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class LandingPetOwnerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_pet_owner);
        FloatingActionButton agendarBTN = findViewById(R.id.AgendarPaseoBTN);

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

        agendarBTN.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), AgendarPaseosActivity.class);
            startActivity(intent);

        });
    }


}