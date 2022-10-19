package com.example.petplanet.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.petplanet.databinding.ActivityAgendarPaseosBinding;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.util.Locale;

public class AgendarPaseosActivity extends AppCompatActivity {
    private ActivityAgendarPaseosBinding binding;
    int hour, minute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAgendarPaseosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        setSupportActionBar(binding.toolbarAgendar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.toolbarAgendar.setNavigationOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), LandingPetOwnerActivity.class));
            finish();
        });


        binding.AgendarHora.setOnClickListener(v -> popTimePicker(v));


        binding.buttonAgendar.setOnClickListener(v -> {
            String fecha = binding.AgendarFecha.getText().toString();
            String hora = binding.AgendarHora.getText().toString();
            String mascota = binding.spinnerMascota.getSelectedItem().toString();
            String cuidador = binding.spinnerPaseador.getSelectedItem().toString();
            if(mascota.equals("Selecciona una mascota")){
                ((TextView)binding.spinnerMascota.getSelectedView()).setError("Error message");
            }if(fecha.isEmpty()){
                binding.AgendarFecha.setError("Selecciona una fecha valida");
                binding.AgendarFecha.requestFocus();
            }if(hora.isEmpty()){
                binding.AgendarHora.setError("Selecciona una hora valida");
                binding.AgendarHora.requestFocus();
            }
            if(cuidador.equals("Seleccione el paseador")) {
                ((TextView) binding.spinnerPaseador.getSelectedView()).setError("Error message");
            }
            else{
            startActivity(new Intent(getApplicationContext(),LandingPetOwnerActivity.class));
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

    public void popTimePicker(View view)
    {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = (timePicker, selectedHour, selectedMinute) -> {
            hour = selectedHour;
            minute = selectedMinute;
            binding.AgendarHora.setText(String.format(Locale.getDefault(), "%02d:%02d",hour, minute));
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