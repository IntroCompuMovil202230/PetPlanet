package com.example.petplanet.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.petplanet.R;

public class PerfilPerroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_perro);

        Toolbar tollbarperro = findViewById(R.id.toolbarPperro);
        tollbarperro.setTitle("");
        setSupportActionBar(tollbarperro);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tollbarperro.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // luego se pone despues que se revise el rol del usuario a que pantalla ir
                startActivity(new Intent(getApplicationContext(),PerfilUsuarioActivity.class));
                finish();
            }
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