package com.example.petplanet.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;


import com.example.petplanet.R;
import com.example.petplanet.databinding.ActivityMainBinding;
import com.example.petplanet.models.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FirebaseAuth mAuth;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        checkAndRequestPermissions();
        mAuth = FirebaseAuth.getInstance();

        binding.creacruentaBTN.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), SelecciondeCuentaActivity.class);
            startActivity(intent);
            finish();
        });
        binding.iniciarsesionBTN.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
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
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            updateUI(currentUser);
        }
    }


    private  boolean checkAndRequestPermissions() {
        int permissionWritestorage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionCamera = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA);
        int locationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (permissionWritestorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (permissionCamera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            myRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());

            myRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()){

                    if(task.getResult().exists()){
                        Usuario usuario = task.getResult().getValue(Usuario.class);

                        assert usuario != null;
                        if(usuario.getWalker())
                            startActivity(new Intent(getApplicationContext(),LandingPetWalkerActivity.class));
                        else
                            startActivity(new Intent(getApplicationContext(),LandingPetOwnerActivity.class));
                    }
                }
            });

        }
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
        finish();
    }
}