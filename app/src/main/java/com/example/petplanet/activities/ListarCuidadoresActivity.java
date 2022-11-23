package com.example.petplanet.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.widget.ArrayAdapter;

import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;


import com.example.petplanet.adapters.CardAdapterUserDog;
import com.example.petplanet.adapters.CardAdapterUsuario;
import com.example.petplanet.R;
import com.example.petplanet.adapters.CardListaPaseo;
import com.example.petplanet.databinding.ActivityListarCuidadoresBinding;
import com.example.petplanet.models.Paseo;
import com.example.petplanet.models.Perro;
import com.example.petplanet.models.Usuario;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ListarCuidadoresActivity extends AppCompatActivity {
    private ActivityListarCuidadoresBinding binding;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    private FirebaseAuth mAuth;
    DatabaseReference myUserRef;

    public static final String PATH_USERS = "users/";
    Paseo walkerx = new Paseo();
    Usuario Ownerx = new Usuario();
    ArrayList<Paseo> cuidadoreslist = new ArrayList<>();

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListarCuidadoresBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        myRef = database.getReference(PATH_USERS);
        mAuth = FirebaseAuth.getInstance();

        cargatlocalidadOwner();
        binding.progressBar2.setVisibility(View.VISIBLE);
        binding.grindCuidadores.setNumColumns(1);
        binding.grindCuidadores.setVerticalSpacing(30);
        binding.grindCuidadores.setHorizontalSpacing(30);
        CargarUsuariosdeLaMismaLocalidad();

        setSupportActionBar(binding.toolbarListarCuidadores);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.toolbarListarCuidadores.setNavigationOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), LandingPetOwnerActivity.class));
            finish();
        });
    }


    public void CargarUsuariosdeLaMismaLocalidad() {
        myUserRef = database.getReference(PATH_USERS + mAuth.getCurrentUser().getUid());
        myUserRef.getDatabase().getReference(PATH_USERS + mAuth.getCurrentUser().getUid()).child("paseosterminados").get().addOnCompleteListener(task1 -> {
            if (task1.isSuccessful()) {
                task1.getResult().getChildren().forEach(walker -> {
                    walkerx = walker.getValue(Paseo.class);
                    cuidadoreslist.add(new Paseo(walkerx.getUidOwner(), walkerx.getFcmtokenowner(), walkerx.getFotodelperro(), walkerx.getNombredelowner(), walkerx.getNombredelperro(), walkerx.getLocalidad(), walkerx.getFecha(), walkerx.getHora(), walkerx.getDirecciondelowner(),walkerx.getHoraderegreso()));
                    ArrayAdapter adapter = new CardListaPaseo(this, R.layout.listapaseocard, cuidadoreslist);
                    if (binding.grindCuidadores != null) {
                        binding.grindCuidadores.setAdapter(adapter);
                        binding.progressBar2.setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    public void cargatlocalidadOwner() {
        myRef = database.getReference(PATH_USERS + mAuth.getCurrentUser().getUid());
        myRef.getDatabase().getReference(PATH_USERS + mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Ownerx = task.getResult().getValue(Usuario.class);

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
        startActivity(new Intent(getApplicationContext(), LandingPetOwnerActivity.class));
        finish();
    }
}