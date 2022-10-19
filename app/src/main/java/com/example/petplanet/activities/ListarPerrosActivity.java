package com.example.petplanet.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.example.petplanet.R;
import com.example.petplanet.adapters.CardAdapterPerro;
import com.example.petplanet.databinding.ActivityListarPerrosBinding;
import com.example.petplanet.models.Perro;

import java.util.ArrayList;

public class ListarPerrosActivity extends AppCompatActivity {

    private ActivityListarPerrosBinding binding;

    ArrayList<Perro> perroslist=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListarPerrosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbarListarPerros);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.toolbarListarPerros.setNavigationOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), PerfilUsuarioActivity.class));
            finish();
        });

        binding.grindPerrosdueno.setNumColumns(2);
        binding.grindPerrosdueno.setVerticalSpacing(30);
        binding.grindPerrosdueno.setHorizontalSpacing(30);

        ArrayAdapter adapter = new CardAdapterPerro(this,R.layout.cardview,perroslist);
        if (binding.grindPerrosdueno != null) {
            binding.grindPerrosdueno.setAdapter(adapter);
        }
        binding.grindPerrosdueno.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(getApplicationContext() , PerfilPerroActivity.class);
            Perro items = perroslist.get(position);
            intent.putExtra("nombredelperro",items.getNombrecompleto());
            intent.putExtra("imagen",items.getFoto());
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
        startActivity(new Intent(getApplicationContext(), LandingPetOwnerActivity.class));
        finish();
    }
}