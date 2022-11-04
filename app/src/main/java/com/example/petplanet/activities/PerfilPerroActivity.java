package com.example.petplanet.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;


import com.example.petplanet.R;
import com.example.petplanet.adapters.CardAdapterUserDog;
import com.example.petplanet.databinding.ActivityPerfilPerroBinding;
import com.example.petplanet.models.Perro;
import com.example.petplanet.models.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PerfilPerroActivity extends AppCompatActivity {
    private ActivityPerfilPerroBinding binding;

    String nombreS, fotoS;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    DatabaseReference myUserRef;

    private FirebaseAuth mAuth;

    Usuario Client = new Usuario();
    Perro perrox = new Perro();

    public static final String PATH_USERS = "users/";
    public static final String PATH_PERROS = "/mascotas/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPerfilPerroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                nombreS = null;
                fotoS = null;
            } else {
                nombreS = extras.getString("nombredelperro");
                fotoS = extras.getString("fotodelperro");

            }
        } else {
            nombreS = (String) savedInstanceState.getSerializable("nombredelperro");
            fotoS = (String) savedInstanceState.getSerializable("fotodelperro");
        }

        binding.toolbarPperro.setTitle("");
        setSupportActionBar(binding.toolbarPperro);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.toolbarPperro.setNavigationOnClickListener(v -> {
            // luego se pone despues que se revise el rol del usuario a que pantalla ir
            startActivity(new Intent(getApplicationContext(), PerfilUsuarioActivity.class));
            finish();
        });
        mAuth = FirebaseAuth.getInstance();


        myUserRef = database.getReference(PATH_USERS + mAuth.getCurrentUser().getUid());
        myUserRef.getDatabase().getReference(PATH_USERS + mAuth.getCurrentUser().getUid()).child("perros").get().addOnCompleteListener(task1 -> {
            if (task1.isSuccessful()) {
                task1.getResult().getChildren().forEach(perro -> {
                    perrox = perro.getValue(Perro.class);
                    if (perrox.getNombrecompleto().equals(nombreS)) {





                        binding.fullNamePet.setText(perrox.getNombrecompleto());
                        byte[] decodedString = Base64.decode(perrox.getFoto(), Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        binding.profilePetPicture.setImageBitmap(decodedByte);
                        binding.Fechadenacimientodelperro.setText(perrox.getFechanacimiento());
                        binding.sexodelperro.setText(perrox.getSexo());
                        binding.raza.setText(perrox.getRaza());
                        if (perrox.getVacunado()) {
                            binding.vacunadoperroTxt.setText("Si");
                        } else {
                            binding.vacunadoperroTxt.setText("No");
                        }
                        if (perrox.getEsterilizado()) {
                            binding.esterilazoTXT.setText("Si");
                        } else {
                            binding.esterilazoTXT.setText("No");
                        }
                        if(perrox.getRedomendacionesespeciales()){
                            binding.textView16.setVisibility(View.VISIBLE);
                            binding.recomendacionesTXT.setVisibility(View.VISIBLE);
                            binding.recomendacionesTXT.setText(perrox.getRecomendaciones());
                        }

                        SystemClock.sleep(1000);
                        binding.fullNamePet.setVisibility(View.VISIBLE);
                        binding.profilePetPicture.setVisibility(View.VISIBLE);
                        binding.esterilazoTXT.setVisibility(View.VISIBLE);
                        binding.Fechadenacimientodelperro.setVisibility(View.VISIBLE);

                        binding.textView6.setVisibility(View.VISIBLE);
                        binding.textView7.setVisibility(View.VISIBLE);
                        binding.textView8.setVisibility(View.VISIBLE);
                        binding.textView11.setVisibility(View.VISIBLE);
                        binding.textView12.setVisibility(View.VISIBLE);
                        binding.textView14.setVisibility(View.VISIBLE);
                        binding.sexodelperro.setVisibility(View.VISIBLE);
                        binding.vacunadoperroTxt.setVisibility(View.VISIBLE);
                        binding.raza.setVisibility(View.VISIBLE);
                        binding.progressBarPerfilPerro.setVisibility(View.GONE);
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
        startActivity(new Intent(getApplicationContext(), PerfilUsuarioActivity.class));
        finish();
    }
}