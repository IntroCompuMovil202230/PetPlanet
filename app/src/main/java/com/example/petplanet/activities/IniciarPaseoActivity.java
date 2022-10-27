package com.example.petplanet.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;

import com.example.petplanet.R;
import com.example.petplanet.adapters.CardAdapterIniciarpaseo;
import com.example.petplanet.adapters.CardAdapterUsuario;
import com.example.petplanet.databinding.ActivityChatBinding;
import com.example.petplanet.databinding.ActivityIniciarPaseoBinding;
import com.example.petplanet.models.Paseo;
import com.example.petplanet.models.Perro;
import com.example.petplanet.models.Usuario;
import com.example.petplanet.utilities.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class IniciarPaseoActivity extends AppCompatActivity {
    private ActivityIniciarPaseoBinding binding;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    ArrayList<Paseo> paseos = new ArrayList<>();
    private FirebaseAuth mAuth;

    Usuario walkerx = new Usuario();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityIniciarPaseoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();

        ArrayList<Perro> perrosList = new ArrayList<>();
        setSupportActionBar(binding.toolbarPerDispo);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.toolbarPerDispo.setNavigationOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), LandingPetWalkerActivity.class));
            finish();
        });
        binding.grindDispo.setNumColumns(1);
        binding.grindDispo.setVerticalSpacing(30);
        binding.grindDispo.setHorizontalSpacing(30);
        cargarInforcelWalker();


    }

    public void cargarInforcelWalker() {
        myRef = database.getReference(Constants.PATH_USERS + mAuth.getCurrentUser().getUid());
        myRef.getDatabase().getReference(Constants.PATH_USERS + mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                walkerx = task.getResult().getValue(Usuario.class);
                cargarPerros();
            }
        });
    }

    public void cargarPerros() {
        myRef = database.getReference(Constants.PATH_PASEOS);
        myRef.getDatabase().getReference(Constants.PATH_PASEOS).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                task.getResult().getChildren().forEach(paseo -> {
                    Paseo paseox = paseo.getValue(Paseo.class);
                    Log.d("Paseoxxx", paseox.getFecha());
                    if (paseox.getLocalidad().equals(walkerx.getLocalidad())) {
                        Log.d("Paseoxxx", paseox.getFotodelperro());
                        paseos.add(paseox);
                    }
                    if (paseos.size() > 0) {
                        ArrayAdapter<Paseo> adapter = new CardAdapterIniciarpaseo(this, R.layout.cardviewiniciarpaseo, paseos);
                        if (binding.grindDispo != null) {
                            binding.grindDispo.setAdapter(adapter);
                        }
                        binding.grindDispo.setOnItemClickListener((parent, view, position, id) -> {
                            Intent intent = new Intent(getApplicationContext(), LandingPetWalkerActivity.class);
                            Paseo items = paseos.get(position);
                            intent.putExtra("direccion", items.getDirecciondelowner());
                            startActivity(intent);
                            finish();
                        });
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