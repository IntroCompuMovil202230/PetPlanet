package com.example.petplanet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
            }
        });
    }
}