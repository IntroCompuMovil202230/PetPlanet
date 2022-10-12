package com.example.petplanet.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.petplanet.R;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        TextView nombrepersona = findViewById(R.id.TextName);
        ImageView fotopersona = findViewById(R.id.imagePerson);
        ImageView back = findViewById(R.id.imageback);
        ImageView info = findViewById(R.id.imageinfo);
        int foto;
        String nombre;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                nombre= null;

                foto= 0;
            } else {
                nombre= extras.getString("nombre");
                foto= extras.getInt("foto");
            }
        } else {
            nombre= (String) savedInstanceState.getSerializable("nombre");
            foto= (int) savedInstanceState.getSerializable("foto");
        }
        nombrepersona.setText(nombre);
        fotopersona.setImageResource(foto);

        back.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ListaDeChatsActivity.class);
            startActivity(intent);
            finish();
        });// se tiene que implementar la paerte de los mensajes ya los layouts estan hechos

        info.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), PerfilUsuarioWalkerActivity.class);// hay que poner el perfil de owner
            intent.putExtra("nombre", nombre);
            intent.putExtra("imagen", foto);
            startActivity(intent);
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
        startActivity(new Intent(getApplicationContext(), ListaDeChatsActivity.class));
        finish();
    }
}