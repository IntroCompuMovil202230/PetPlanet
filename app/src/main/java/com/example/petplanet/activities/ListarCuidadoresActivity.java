package com.example.petplanet.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.example.petplanet.adapters.CardAdapterUsuario;
import com.example.petplanet.R;
import com.example.petplanet.databinding.ActivityListarCuidadoresBinding;
import com.example.petplanet.models.Usuario;

import java.util.ArrayList;

public class ListarCuidadoresActivity extends AppCompatActivity {
    private ActivityListarCuidadoresBinding binding;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListarCuidadoresBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayList<Usuario> cuidadoreslist=new ArrayList<>();
        setSupportActionBar(binding.toolbarListarCuidadores);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.toolbarListarCuidadores.setNavigationOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(),LandingPetOwnerActivity.class));
            finish();
        });


        binding.grindCuidadores.setNumColumns(2);
        binding.grindCuidadores.setVerticalSpacing(30);
        binding.grindCuidadores.setHorizontalSpacing(30);


        try {

            ArrayAdapter<Usuario> adapter = new CardAdapterUsuario(this,R.layout.cardview,cuidadoreslist);
            if (binding.grindCuidadores != null) {
                binding.grindCuidadores.setAdapter(adapter);
            }
            binding.grindCuidadores.setOnItemClickListener((parent, view, position, id) -> {
                Intent intent = new Intent(getApplicationContext() , PerfilUsuarioWalkerActivity.class);
                Usuario items = cuidadoreslist.get(position);

                intent.putExtra("nombre",items.getNombre());
                intent.putExtra("telefono",items.getTelefono());
                intent.putExtra("experiencia",items.getExperiencia());
                intent.putExtra("imagen",items.getFoto());

                startActivity(intent);
                finish();
            });
        }catch (Exception e) {
            e.printStackTrace();
        }
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
        startActivity(new Intent(getApplicationContext(), LandingPetOwnerActivity.class));
        finish();
    }
}