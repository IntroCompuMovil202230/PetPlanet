package com.example.petplanet.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.petplanet.databinding.ActivityRazasBinding;
import com.example.petplanet.models.Perro;
import com.example.petplanet.models.Usuario;
import com.example.petplanet.utilities.Constants;
import com.google.android.material.chip.Chip;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class RazasActivity extends AppCompatActivity {

    String raza[] = {"Beagle", "Bulldog", "Chihuahua", "Dalmata", "Golden Retriever", "Labrador", "Pitbull", "Poodle", "Rottweiler", "San Bernardo", "Schnauzer", "Shih Tzu", "Terrier", "Yorkshire"};
    private ActivityRazasBinding binding;
    private FirebaseAuth mAuth;
    Usuario Client = new Usuario();
    Perro perrox = new Perro();
    String nombredelperro, nombredueno, fechadenacimeinto, sexoselperro, colordelperro, razadelperro, fotoS;
    Boolean vacunado, esterilizado;
    ArrayList<Perro> perros = new ArrayList<>();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    DatabaseReference myUserRef;
    public static final String PATH_USERS = "users/";
    public static final String PATH_PERROS = "/mascotas/";
    boolean ischecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRazasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        mAuth = FirebaseAuth.getInstance();

        myRef = database.getReference(PATH_USERS + mAuth.getCurrentUser().getUid());
        myRef.getDatabase().getReference(PATH_USERS + mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Client = task.getResult().getValue(Usuario.class);
                nombredueno = Client.getNombre();
            }
        });
        myUserRef = database.getReference(PATH_USERS + mAuth.getCurrentUser().getUid());
        myUserRef.getDatabase().getReference(PATH_USERS + mAuth.getCurrentUser().getUid()).child("perros").get().addOnCompleteListener(task1 -> {
            if (task1.isSuccessful()) {
                task1.getResult().getChildren().forEach(perro -> {
                    perrox = perro.getValue(Perro.class);
                    perros.add(new Perro(perrox.getNombrecompleto(), perrox.getRaza(), perrox.getSexo(), perrox.getColor(), perrox.getFechanacimiento(), perrox.getVacunado(), perrox.getEsterilizado(), perrox.getFoto(), perrox.getRedomendacionesespeciales(), perrox.getRecomendaciones()));
                });

            }
        });
        addChipGroup();

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                nombredelperro = null;
                fechadenacimeinto = null;
                colordelperro = null;
                sexoselperro = null;
                fotoS = null;
                vacunado = null;
                esterilizado = null;
            } else {
                nombredelperro = extras.getString("nombredelamascota");
                fechadenacimeinto = extras.getString("fechadenacimiento");
                colordelperro = extras.getString("colordelperro");
                sexoselperro = extras.getString("sexoselamascota");
                fotoS = extras.getString("foto");
                vacunado = extras.getBoolean("vacunado");
                esterilizado = extras.getBoolean("esterilizado");

            }
        } else {
            nombredelperro = (String) savedInstanceState.getSerializable("nombredelamascota");
            fechadenacimeinto = (String) savedInstanceState.getSerializable("fechadenacimiento");
            colordelperro = (String) savedInstanceState.getSerializable("colordelperro");
            sexoselperro = (String) savedInstanceState.getSerializable("sexoselamascota");
            fotoS = (String) savedInstanceState.getSerializable("foto");
            vacunado = (Boolean) savedInstanceState.getSerializable("vacunado");
            esterilizado = (Boolean) savedInstanceState.getSerializable("esterilizado");
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
                razadelperro = chip.getText().toString();
            }
        });
        Log.d("putoelquelolea", "onCreate: " + binding.switchRecomendaciones.isChecked());
        binding.switchRecomendaciones.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                binding.recomendacionesespecialestxt.setVisibility(View.VISIBLE);
                ischecked = true;
            } else {
                binding.recomendacionesespecialestxt.setVisibility(View.GONE);
                ischecked = false;
            }
        });

        binding.registrarMascotaBT.setOnClickListener(view -> {
            Log.d("putoelquelolea", "onCreate: " + ischecked);
            if (ischecked) {
                binding.recomendacionesespecialestxt.setError("Ingrese las recomendaciones especiales");
            }
            if (razadelperro.isEmpty()) {
                Toast.makeText(this, "Seleccione una raza", Toast.LENGTH_SHORT).show();
            } else {
                registrarperro();
            }
        });

    }

    private Perro createPerroObject() {
        Perro perro = new Perro();
        perro.setNombrecompleto(nombredelperro);
        perro.setFechanacimiento(fechadenacimeinto);
        perro.setSexo(sexoselperro);
        perro.setRaza(razadelperro);
        perro.setEsterilizado(esterilizado);
        perro.setVacunado(vacunado);
        perro.setColor(colordelperro);
        perro.setFoto(fotoS);
        perro.setRedomendacionesespeciales(ischecked);
        perro.setRecomendaciones(binding.recomendacionesespecialestxt.getText().toString());
        return perro;
    }

    private Usuario createUserObject() {
        Usuario usuario = new Usuario();
        perros.add(createPerroObject());
        usuario.setPerros(perros);
        return usuario;
    }

    Usuario nUp = new Usuario();

    private void registrarperro() {
        binding.chipGroup.setVisibility(View.GONE);
        binding.switchRecomendaciones.setVisibility(View.GONE);
        binding.recomendacionesespecialestxt.setVisibility(View.GONE);
        binding.registrarMascotaBT.setVisibility(View.GONE);
        binding.progressBarRazas.setVisibility(View.VISIBLE);
        binding.registrandotxt.setVisibility(View.VISIBLE);
        perros.add(createPerroObject());
        Log.d("putoelquelolea", "registrarperro: " + perros.size());
        if (perros.size() == 0) {
            myRef = database.getReference(Constants.PATH_USERS + mAuth.getCurrentUser().getUid());
            myRef.getDatabase().getReference(Constants.PATH_USERS + mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    nUp = task.getResult().getValue(Usuario.class);
                    nUp.setPerros(perros);
                    myRef.setValue(nUp);
                    startActivity(new Intent(getApplicationContext(), PerfilUsuarioActivity.class));
                    finish();
                } else {
                    Toast.makeText(this, "Error al registrar", Toast.LENGTH_SHORT).show();
                }

            });
        } else {
            myRef = database.getReference(Constants.PATH_USERS + mAuth.getCurrentUser().getUid());
            myRef.getDatabase().getReference(Constants.PATH_USERS + mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Client = task.getResult().getValue(Usuario.class);
                    Client.setPerros(perros);
                    myRef.setValue(Client);
                    startActivity(new Intent(getApplicationContext(), PerfilUsuarioActivity.class));
                    finish();
                } else {
                    Toast.makeText(this, "Error al registrar", Toast.LENGTH_SHORT).show();
                }
            });
        }

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