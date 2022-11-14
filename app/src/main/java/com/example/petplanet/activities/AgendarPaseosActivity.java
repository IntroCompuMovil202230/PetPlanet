package com.example.petplanet.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.petplanet.databinding.ActivityAgendarPaseosBinding;
import com.example.petplanet.utilities.Constants;
import com.google.android.material.datepicker.MaterialDatePicker;

import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.petplanet.models.Paseo;
import com.example.petplanet.models.Perro;
import com.example.petplanet.models.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class AgendarPaseosActivity extends AppCompatActivity {
    private ActivityAgendarPaseosBinding binding;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    DatabaseReference myUserRef;
    private FirebaseAuth mAuth;

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
        myUserRef = database.getReference(Constants.PATH_USERS + mAuth.getCurrentUser().getUid());
        myUserRef.getDatabase().getReference(Constants.PATH_USERS + mAuth.getCurrentUser().getUid()).child("perros").get().addOnCompleteListener(task1 -> {
            if (task1.isSuccessful()) {

                Log.d("malditasea", "onComplete: " + task1.getResult().getValue());
                if (task1.getResult().getValue() != null) {
                    task1.getResult().getChildren().forEach(perro -> {
                        perrox = perro.getValue(Perro.class);


                        binding.progressBarAgendar.setVisibility(View.INVISIBLE);
                        binding.spinnerMascota.setVisibility(View.VISIBLE);
                        binding.agendarfechaview.setVisibility(View.VISIBLE);
                        binding.agendarhoraview.setVisibility(View.VISIBLE);
                        binding.duracionminimadelpaseoview.setVisibility(View.VISIBLE);
                        binding.buttonAgendar.setVisibility(View.VISIBLE);
                        Log.d("malditasea", "onComplete: " + perrox.getNombrecompleto());
                        prueba.add(new Perro(perrox.getNombrecompleto(), perrox.getRaza(), perrox.getSexo(), perrox.getColor(), perrox.getFechanacimiento(), perrox.getVacunado(), perrox.getEsterilizado(), perrox.getFoto(), perrox.getRedomendacionesespeciales(), perrox.getRecomendaciones()));
                        menuperros.add(perrox.getNombrecompleto());

                        Log.d("malditasea", "onComplete: " + prueba.size());
                        ArrayAdapter adapter = new ArrayAdapter(AgendarPaseosActivity.this, android.R.layout.simple_spinner_dropdown_item, menuperros);
                        binding.spinnerMascotatxt.setAdapter(adapter);

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

        myRef = database.getReference(Constants.PATH_USERS + mAuth.getCurrentUser().getUid());
        myRef.getDatabase().getReference(Constants.PATH_USERS + mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Client = task.getResult().getValue(Usuario.class);
            }
        });


        binding.AgendarHora.setOnClickListener(this::popTimePicker);
        binding.duracionminimadelpaseo.setOnClickListener(this::popDiracionview);

        binding.buttonAgendar.setOnClickListener(v -> {
            String fecha = binding.AgendarFecha.getText().toString();
            String hora = binding.AgendarHora.getText().toString();
            String duracion = binding.duracionminimadelpaseo.getText().toString();
            String mascota = binding.spinnerMascotatxt.getText().toString();
            if (mascota.isEmpty()) {
                binding.spinnerMascota.setError("Seleccione un perro");
                binding.spinnerMascota.requestFocus();
            }
            if (fecha.isEmpty()) {
                binding.AgendarFecha.setError("Selecciona una fecha valida");
                binding.AgendarFecha.requestFocus();
            }
            if (hora.isEmpty()) {
                binding.AgendarHora.setError("Selecciona una hora valida");
                binding.AgendarHora.requestFocus();
            }
            if (duracion.isEmpty()) {
                binding.duracionminimadelpaseo.setError("Ingresa la duracion minima del paseo");
                binding.duracionminimadelpaseo.requestFocus();
            } else {
                agendarpaseo(fecha, hora, mascota);
            }
        });

        MaterialDatePicker.Builder<Long> materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("Selecciona la fecha del paseo");
        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();
        binding.AgendarFecha.setOnClickListener(v -> materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER"));
        materialDatePicker.addOnPositiveButtonClickListener(
                selection -> {

                    // if the user clicks on the positive
                    // button that is ok button update the
                    // selected date
                    TimeZone timeZoneUTC = TimeZone.getDefault();
                    // It will be negative, so that's the -1
                    int offsetFromUTC = timeZoneUTC.getOffset(new Date().getTime()) * -1;
                    // Create a date format, then a date object with our offset
                    SimpleDateFormat simpleFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
                    Date date = new Date(materialDatePicker.getHeaderText());

                    binding.AgendarFecha.setText(simpleFormat.format(date));
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

        Paseo paseo = new Paseo(fotodelperro, Client.getNombre(), mascota, Client.getLocalidad(), fecha, hora, Client.getDireccion(), binding.duracionminimadelpaseo.getText().toString());
        myRef = database.getReference(Constants.PATH_PASEOS);
        myRef.getDatabase().getReference().child(Constants.PATH_PASEOS).child(myRef.getDatabase().getReference().push().getKey()).setValue(paseo).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Paseo agendado", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), LandingPetOwnerActivity.class));
                finish();
            }
        });
    }


    public void popTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = (timePicker, selectedHour, selectedMinute) -> {
            hour = selectedHour;
            minute = selectedMinute;
            binding.AgendarHora.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
        };

        // int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, /*style,*/ onTimeSetListener, hour, minute, false);
        timePickerDialog.setTitle("Selecciona la hora del paseo");

        timePickerDialog.show();
    }


    public void popDiracionview(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = (timePicker, selectedHour, selectedMinute) -> {
            hour = selectedHour;
            minute = selectedMinute;
            binding.duracionminimadelpaseo.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
        };

        // int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, /*style,*/ onTimeSetListener, hour, minute, false);
        timePickerDialog.setTitle("Selecciona la hora de finalizacion del paseo");

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