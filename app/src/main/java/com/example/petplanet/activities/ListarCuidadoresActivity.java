package com.example.petplanet.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.example.petplanet.adapters.CardAdapterUsuario;
import com.example.petplanet.R;
import com.example.petplanet.databinding.ActivityListaPaseosBinding;
import com.example.petplanet.databinding.ActivityListarCuidadoresBinding;
import com.example.petplanet.models.Perro;
import com.example.petplanet.models.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ListarCuidadoresActivity extends AppCompatActivity {
    private ActivityListarCuidadoresBinding binding;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    public static final String PATH_USERS="users/";
    Usuario walkerx = new Usuario();
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListarCuidadoresBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        myRef = database.getReference(PATH_USERS);

        ArrayList<Usuario> cuidadoreslist=new ArrayList<>();

        binding.progressBar2.setVisibility(View.VISIBLE);
        binding.grindCuidadores.setNumColumns(2);
        binding.grindCuidadores.setVerticalSpacing(30);
        binding.grindCuidadores.setHorizontalSpacing(30);
        myRef.getDatabase().getReference(PATH_USERS).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                binding.progressBar2.setVisibility(View.INVISIBLE);
                for (DataSnapshot walker : task.getResult().getChildren()) {
                    walkerx = walker.getValue(Usuario.class);
                    if (walkerx.getWalker()) {
                        cuidadoreslist.add(new Usuario(walkerx.getNombre(), walkerx.getLocalidad(), walkerx.getCorreo(), walkerx.getDireccion(), walkerx.getFoto(), walkerx.getWalker(), walkerx.getExperiencia()));

                        ArrayAdapter adapter = new CardAdapterUsuario(this, R.layout.cardview, cuidadoreslist);
                        if (binding.grindCuidadores != null) {
                            binding.grindCuidadores.setAdapter(adapter);
                        }
                        binding.grindCuidadores.setOnItemClickListener((parent, view, position, id) -> {
                            Intent intent = new Intent(getApplicationContext() , PerfilUsuarioWalkerActivity.class);
                            Usuario items = cuidadoreslist.get(position);
                            intent.putExtra("nombre",items.getNombre());
                            startActivity(intent);
                            finish();
                        });
                    }
                }
            }
        });




        setSupportActionBar(binding.toolbarListarCuidadores);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.toolbarListarCuidadores.setNavigationOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(),LandingPetOwnerActivity.class));
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