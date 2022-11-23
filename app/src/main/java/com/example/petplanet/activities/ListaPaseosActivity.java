package com.example.petplanet.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.example.petplanet.adapters.CardAdapterPerro;
import com.example.petplanet.adapters.CardAdapterUsuario;
import com.example.petplanet.adapters.CardListaPaseo;
import com.example.petplanet.databinding.ActivityListaDeChatsBinding;
import com.example.petplanet.databinding.ActivityListaPaseosBinding;
import com.example.petplanet.models.Paseo;
import com.example.petplanet.models.Perro;
import com.example.petplanet.R;
import com.example.petplanet.models.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ListaPaseosActivity extends AppCompatActivity {
    private ActivityListaPaseosBinding binding;
    ArrayList<Paseo> paseolist = new ArrayList<>();
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
        binding = ActivityListaPaseosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();

        setSupportActionBar(binding.toolbarListar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        myRef = database.getReference(PATH_USERS + mAuth.getCurrentUser().getUid());
        myRef.getDatabase().getReference(PATH_USERS + mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Ownerx = task.getResult().getValue(Usuario.class);
                    }
                }
        );


        binding.toolbarListar.setNavigationOnClickListener(v -> {
            if (Ownerx.getWalker()) {
                startActivity(new Intent(getApplicationContext(), LandingPetWalkerActivity.class));
                finish();
            } else {
                startActivity(new Intent(getApplicationContext(), LandingPetOwnerActivity.class));
                finish();
            }

        });
        CargarUsuariosdeLaMismaLocalidad();
        binding.grind.setNumColumns(1);
        binding.grind.setVerticalSpacing(30);
        binding.grind.setHorizontalSpacing(30);

    }


    public void CargarUsuariosdeLaMismaLocalidad() {
        myUserRef = database.getReference(PATH_USERS + mAuth.getCurrentUser().getUid());
        myUserRef.getDatabase().getReference(PATH_USERS + mAuth.getCurrentUser().getUid()).child("paseosterminados").get().addOnCompleteListener(task1 -> {
            if (task1.isSuccessful()) {
                task1.getResult().getChildren().forEach(walker -> {
                    walkerx = walker.getValue(Paseo.class);
                    cuidadoreslist.add(new Paseo(walkerx.getUidOwner(), walkerx.getFcmtokenowner(), walkerx.getFotodelperro(), walkerx.getNombredelowner(), walkerx.getNombredelperro(), walkerx.getLocalidad(), walkerx.getFecha(), walkerx.getHora(), walkerx.getDirecciondelowner(), walkerx.getHoraderegreso()));
                    ArrayAdapter adapter = new CardListaPaseo(this, R.layout.listapaseocard, cuidadoreslist);
                    if (binding.grind != null) {
                        binding.grind.setAdapter(adapter);
                    }
                });
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
        startActivity(new Intent(getApplicationContext(), LandingPetWalkerActivity.class));
        finish();
    }

}