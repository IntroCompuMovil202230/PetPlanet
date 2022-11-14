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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class IniciarPaseoActivity extends AppCompatActivity {
    private ActivityIniciarPaseoBinding binding;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    ArrayList<Paseo> paseos = new ArrayList<>();
    private FirebaseAuth mAuth;
    Paseo paseox = new Paseo();
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
                if (!walkerx.getPaseoencurso()) {
                    cargarPerros();
                } else {
                    binding.adTXT.setText("Ya tienes un paseo en curso");
                }
            }
        });
    }

    public void cargarPerros() {

        myRef = database.getReference(Constants.PATH_PASEOS);
        myRef.getDatabase();
        myRef.getDatabase().getReference(Constants.PATH_PASEOS).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DataSnapshot paseo : task.getResult().getChildren()) {
                    paseox = paseo.getValue(Paseo.class);
                    boolean iniciarpaseo = false;
                    if (paseox.getLocalidad().equals(walkerx.getLocalidad())) {
                        binding.progressBarIniciar.setVisibility(View.GONE);
                        Date fechafirebase = new Date();
                        Date date2 = new Date();

                        Date simplfirebase = new Date();
                        Date simpl2 = new Date();
                        DateFormat formatfecha = new SimpleDateFormat("dd MMMM yyyy");
                        try {
                            fechafirebase = formatfecha.parse(paseox.getFecha().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String fechaactual = formatfecha.format(date2);
                        String fechafirebase2 = formatfecha.format(fechafirebase);

                        Log.d("Paseoxxx", fechaactual.equals(fechafirebase2) + " " + formatfecha.format(date2) + " " + formatfecha.format(fechafirebase));
                        if (fechaactual.equals(fechafirebase2)) {
                            Log.d("Paseoxxx", paseox.getHora());
                            DateFormat dateFormat = new SimpleDateFormat("hh:mm");
                            Date date = new Date();
                            try {
                                date = dateFormat.parse(paseox.getHora().toString());

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            int horaActual, minutosActual;
                            horaActual = date2.getHours();
                            minutosActual = date2.getMinutes();

                            String horaActual2 = horaActual + ":" + minutosActual;
                            Log.d("Paseoxxx", horaActual2);
                            Date comparar1, comparar2, comparar3;
                            int duracion = 0;
// se comprueba la hora del paseo con la hora actual
                            try {
                                comparar1 = dateFormat.parse(paseox.getHora().toString()); //Esta es la hora que me viene de la BD
                                comparar3 = dateFormat.parse(paseox.getHoraderegreso().toString()); //Esta es la hora actual
                                comparar2 = dateFormat.parse(horaActual2);
                                duracion = comparar1.getHours() - comparar3.getHours() + comparar1.getMinutes() - comparar3.getMinutes();

                                Log.d("Paseoxxx", duracion + "");
                                if (comparar1.compareTo(comparar2) < 0) {
                                    iniciarpaseo = false;
                                } else {
                                    iniciarpaseo = true;
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            if (iniciarpaseo) {
                                if (paseox.getNombredelwalker() != null) {
                                    if (paseox.getNombredelwalker().equals("pendiente")) {
                                        paseox.setId(paseo.getKey());
                                        paseox.setDuracion(String.valueOf(duracion));
                                        paseos.add(paseox);
                                    }
                                }
                                if (paseox.getNombredelwalker() == null) {
                                    paseox.setId(paseo.getKey());
                                    paseox.setDuracion(String.valueOf(duracion));
                                    paseos.add(paseox);
                                }
                            }
                        }
                    }
                    if (paseos.size() > 0) {
                        binding.grindDispo.setVisibility(View.VISIBLE);
                        ArrayAdapter<Paseo> adapter = new CardAdapterIniciarpaseo(this, R.layout.cardviewiniciarpaseo, paseos);
                        if (binding.grindDispo != null) {
                            binding.grindDispo.setAdapter(adapter);
                        }
                        binding.grindDispo.setOnItemClickListener((parent, view, position, id) -> {
                            Intent intent = new Intent(getApplicationContext(), LandingPetWalkerActivity.class);
                            Paseo items = paseos.get(position);
                            intent.putExtra("nombredelowner", items.getNombredelperro());
                            intent.putExtra("id", items.getId());
                            intent.putExtra("duracion", items.getDuracion());
                            intent.putExtra("direccion", items.getDirecciondelowner());
                            startActivity(intent);
                            finish();
                        });
                    }
                    if (iniciarpaseo && paseos.size() == 0) {
                        binding.adTXT.setText("No hay paseos disponibles en tu localidad");
                    }
                    if (!iniciarpaseo || paseos.size() == 0) {
                        binding.adTXT.setText("No hay paseos disponibles");
                    }
                }
            }
            if (!task.isSuccessful()) {
                binding.adTXT.setText("No hay paseos disponibles");
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