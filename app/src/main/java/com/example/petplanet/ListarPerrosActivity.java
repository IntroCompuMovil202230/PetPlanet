package com.example.petplanet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class ListarPerrosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_perros);

        TextView perro1 = findViewById(R.id.nomperro1);
        perro1.setText("Nombre del perro: fluffy");
    }
}