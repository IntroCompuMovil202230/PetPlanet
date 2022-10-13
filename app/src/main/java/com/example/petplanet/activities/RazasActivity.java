package com.example.petplanet.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.example.petplanet.databinding.ActivityRazasBinding;
import com.google.android.material.chip.Chip;
import com.google.firebase.auth.FirebaseAuth;


public class RazasActivity extends AppCompatActivity {

    String raza[]= {"Beagle","Bulldog","Chihuahua","Dalmata","Golden Retriever","Labrador","Pitbull","Poodle","Rottweiler","San Bernardo","Schnauzer","Shih Tzu","Terrier","Yorkshire"};

    private ActivityRazasBinding binding;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRazasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addChipGroup();

        String nombredelperro,fechadenacimeinto,sexoselperro,colordelperro;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                nombredelperro= null;
                fechadenacimeinto = null;
                colordelperro =null;
                sexoselperro=null;

            } else {
                nombredelperro = extras.getString("nombredelamascota");
                fechadenacimeinto = extras.getString("fechadenacimiento");
                colordelperro = extras.getString("colordelperro");
                sexoselperro =extras.getString("sexoselamascota");
            }
        } else {
            nombredelperro = (String) savedInstanceState.getSerializable("nombredelamascota");
            fechadenacimeinto = (String) savedInstanceState.getSerializable("fechadenacimiento");
            colordelperro = (String) savedInstanceState.getSerializable("colordelperro");
            sexoselperro =(String) savedInstanceState.getSerializable("sexoselamascota");
        }


        setSupportActionBar(binding.toolbarEscogeRaza);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.toolbarEscogeRaza.setNavigationOnClickListener(v -> {
            // luego se pone despues que se revise el rol del usuario a que pantalla ir
            startActivity(new Intent(getApplicationContext(), RegistroPerroActivity.class));
            finish();
        });
        binding.chipGroup.setSelectionRequired(true);
        binding.chipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            Chip chip = group.findViewById(checkedId);
            if (chip != null) {
                Toast.makeText(RazasActivity.this, chip.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        binding.registrarMascotaBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }


    private void registrarperro(String nombredelperro, String sexodelperro,String fechadenacimiento,String colordelperro,String Razadelperro) {
    }

    private void addChipGroup() {
        binding.chipGroup.removeAllViews();
        for (int i = 0; i < raza.length; i++) {
            Chip chip = new Chip(this);
            chip.setText(raza[i]);
            chip.setId(i);
            chip.setCheckable(true);
            binding.chipGroup.addView(chip);
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
        startActivity(new Intent(getApplicationContext(), RegistroPerroActivity.class));
        finish();
    }
}