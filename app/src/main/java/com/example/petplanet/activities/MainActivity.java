package com.example.petplanet.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;


import com.example.petplanet.R;
import com.example.petplanet.databinding.ActivityMainBinding;
import com.example.petplanet.models.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FirebaseAuth mAuth;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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