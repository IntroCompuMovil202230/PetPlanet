package com.example.petplanet.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.petplanet.R;
import com.example.petplanet.adapters.CardAdapterUsuario;
import com.example.petplanet.databinding.ActivityPerfilUsuarioWalkerBinding;
import com.example.petplanet.models.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class PerfilUsuarioWalkerActivity extends AppCompatActivity {
    private ActivityPerfilUsuarioWalkerBinding binding;
    String nombre;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    public static final String PATH_USERS="users/";

    byte[] decodedString;
    Bitmap decodedByte;
    Usuario walkerx = new Usuario();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPerfilUsuarioWalkerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.progressBar3.setVisibility(View.VISIBLE);
        binding.nombrePetWalker.setVisibility(View.INVISIBLE);
        binding.experienciaview.setVisibility(View.INVISIBLE);
        binding.nombreview.setVisibility(View.INVISIBLE);
        binding.telefonovieww.setVisibility(View.INVISIBLE);
        binding.TelefonoPetwalker.setVisibility(View.INVISIBLE);
        binding.experienciaPetwalker.setVisibility(View.INVISIBLE);
        binding.FotoPetWalker.setVisibility(View.INVISIBLE);
        binding.agregarFaboritos.setVisibility(View.INVISIBLE);


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                nombre= null;

            } else {
                nombre= extras.getString("nombre");

            }
        } else {
            nombre= (String) savedInstanceState.getSerializable("nombre");

        }
        myRef = database.getReference(PATH_USERS);
        myRef.getDatabase().getReference(PATH_USERS).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                binding.progressBar3.setVisibility(View.INVISIBLE);
                binding.nombrePetWalker.setVisibility(View.VISIBLE);
                binding.TelefonoPetwalker.setVisibility(View.VISIBLE);
                binding.experienciaPetwalker.setVisibility(View.VISIBLE);
                binding.experienciaview.setVisibility(View.VISIBLE);
                binding.nombreview.setVisibility(View.VISIBLE);
                binding.telefonovieww.setVisibility(View.VISIBLE);
                binding.FotoPetWalker.setVisibility(View.VISIBLE);
                binding.agregarFaboritos.setVisibility(View.VISIBLE);
                for (DataSnapshot walker : task.getResult().getChildren()) {
                    walkerx = walker.getValue(Usuario.class);
                    if (walkerx.getWalker()) {
                        if(walkerx.getNombre().equals(nombre)){
                            binding.nombrePetWalker.setText(walkerx.getNombre());
                            binding.TelefonoPetwalker.setText(walkerx.getTelefono());
                            binding.experienciaPetwalker.setText(walkerx.getExperiencia());
                            decodedString = Base64.decode(walkerx.getFoto(), Base64.DEFAULT);
                            decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            binding.FotoPetWalker.setImageBitmap(decodedByte);
                        }
                    }
                }
            }
        });
        setSupportActionBar(binding.tollbarPwalker);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.tollbarPwalker.setNavigationOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(),ListarCuidadoresActivity.class));// esto cambia si el usuario es dueño o walker
            finish();
        });

        binding.agregarFaboritos.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), LandingPetOwnerActivity.class);
            startActivity(intent);
            //agregarFavoritos(nombre,telefono,experiencia,foto);
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
        startActivity(new Intent(getApplicationContext(), ListarCuidadoresActivity.class));
        finish();
    }
}