package com.example.petplanet.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import android.widget.TextView;

import com.example.petplanet.databinding.ActivityAgendarPaseosBinding;
import com.example.petplanet.utilities.Constants;
import com.google.android.material.datepicker.MaterialDatePicker;

import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.petplanet.R;
import com.example.petplanet.adapters.CardAdapterUserDog;
import com.example.petplanet.adapters.CardAdapterUsuario;
import com.example.petplanet.databinding.ActivityAgendarPaseosBinding;
import com.example.petplanet.databinding.ActivityRazasBinding;
import com.example.petplanet.models.Paseo;
import com.example.petplanet.models.Perro;
import com.example.petplanet.models.Usuario;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;
import java.util.Locale;

public class AgendarPaseosActivity extends AppCompatActivity {
    private ActivityAgendarPaseosBinding binding;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    DatabaseReference myUserRef;
    private FirebaseAuth mAuth;
    public static final String PATH_USERS = "users/";
    public static final String PATH_PERROS = "/mascotas/";
    public static final String PATH_PASEOS = "paseos/";

    ArrayList<Perro> prueba = new ArrayList<>();


    Perro perrox = new Perro();
    Usuario walkerx = new Usuario();
    Usuario Client = new Usuario();
    int hour, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAgendarPaseosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        binding.progressBarAgendar.setVisibility(View.VISIBLE);
        setSupportActionBar(binding.toolbarAgendar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.toolbarAgendar.setNavigationOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), LandingPetOwnerActivity.class));
            finish();
        });

        ArrayList<String> menuperros = new ArrayList<>();
        menuperros.add("Seleccione un perro");
        myUserRef = database.getReference(PATH_USERS + mAuth.getCurrentUser().getUid());
        myUserRef.getDatabase().getReference(PATH_USERS + mAuth.getCurrentUser().getUid()).child("perros").get().addOnCompleteListener(task1 -> {
            if (task1.isSuccessful()) {

                Log.d("malditasea", "onComplete: " + task1.getResult().getValue());
                if (task1.getResult().getValue() != null) {
                    task1.getResult().getChildren().forEach(perro -> {
                        perrox = perro.getValue(Perro.class);


                        binding.progressBarAgendar.setVisibility(View.INVISIBLE);
                        binding.spinnerMascota.setVisibility(View.VISIBLE);


                        binding.AgendarHora.setVisibility(View.VISIBLE);
                        binding.AgendarFecha.setVisibility(View.VISIBLE);
                        binding.buttonAgendar.setVisibility(View.VISIBLE);
                        Log.d("malditasea", "onComplete: " + perrox.getNombrecompleto());
                        prueba.add(new Perro(perrox.getNombrecompleto(), perrox.getRaza(), perrox.getSexo(), perrox.getColor(), perrox.getFechanacimiento(), perrox.getVacunado(), perrox.getEsterilizado(), perrox.getFoto()));
                        menuperros.add(perrox.getNombrecompleto());

                        Log.d("malditasea", "onComplete: " + prueba.size());
                        ArrayAdapter adapter = new ArrayAdapter(AgendarPaseosActivity.this, android.R.layout.simple_spinner_dropdown_item, menuperros);
                        binding.spinnerMascota.setAdapter(adapter);

                    });
                } else {
                    binding.progressBarAgendar.setVisibility(View.INVISIBLE);
                    binding.moverregistrarperroBTN.setVisibility(View.VISIBLE);
                    binding.nohayperrostxt.setVisibility(View.VISIBLE);
                }

            }
        });
        binding.moverregistrarperroBTN.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), RegistroPerroActivity.class));
            finish();
        });

        myRef = database.getReference(PATH_USERS + mAuth.getCurrentUser().getUid());
        myRef.getDatabase().getReference(PATH_USERS + mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Client = task.getResult().getValue(Usuario.class);
            }
        });


        binding.AgendarHora.setOnClickListener(v -> popTimePicker(v));


        binding.buttonAgendar.setOnClickListener(v -> {
            String fecha = binding.AgendarFecha.getText().toString();
            String hora = binding.AgendarHora.getText().toString();
            String mascota = binding.spinnerMascota.getSelectedItem().toString();
            if (mascota.equals("Selecciona una mascota")) {
                ((TextView) binding.spinnerMascota.getSelectedView()).setError("Error message");
            }
            if (fecha.isEmpty()) {
                binding.AgendarFecha.setError("Selecciona una fecha valida");
                binding.AgendarFecha.requestFocus();
            }
            if (hora.isEmpty()) {
                binding.AgendarHora.setError("Selecciona una hora valida");
                binding.AgendarHora.requestFocus();
            } else {
                agendarpaseo(fecha, hora, mascota);
            }
        });

        MaterialDatePicker.Builder<Long> materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("Selecciona la fecha de nacimiento");
        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();
        binding.AgendarFecha.setOnClickListener(v -> materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER"));
        materialDatePicker.addOnPositiveButtonClickListener(
                selection -> {

                    // if the user clicks on the positive
                    // button that is ok button update the
                    // selected date
                    binding.AgendarFecha.setText(materialDatePicker.getHeaderText());
                    // in the above statement, getHeaderText
                    // will return selected date preview from the
                    // dialog
                });


    }

    String fotodelperro;

    public void agendarpaseo(String fecha, String hora, String mascota) {
        prueba.forEach(perro -> {
            if (perro.getNombrecompleto().equals(mascota)) {
                perrox = perro;
                fotodelperro = perro.getFoto();
            }
        });

        Paseo paseo = new Paseo(fotodelperro, Client.getNombre(), mascota, Client.getLocalidad(), fecha, hora, Client.getDireccion(), "Pendiente");
        myRef = database.getReference(Constants.PATH_PASEOS);
        Log.d("malditasea", "agendarpaseo: " + myRef.getDatabase().getReference().push().getKey());
        myRef.push().setValue(paseo);
        Log.d("malditasea", "agendarpaseo: " + myRef.getKey());
        Toast.makeText(this, "Paseo agendado", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), LandingPetOwnerActivity.class));
        finish();


    }


    public void popTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = (timePicker, selectedHour, selectedMinute) -> {
            hour = selectedHour;
            minute = selectedMinute;
            binding.AgendarHora.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
        };

        // int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, /*style,*/ onTimeSetListener, hour, minute, true);

        timePickerDialog.setTitle("Selecciona la hora del paseo");
        timePickerDialog.show();
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