package com.example.petplanet.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import androidx.appcompat.widget.Toolbar;

import com.example.petplanet.R;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.util.Locale;

public class AgendarPaseosActivity extends AppCompatActivity {
    EditText editTexthora;
    int hour, minute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendar_paseos);


        Spinner spinnermascota = findViewById(R.id.spinnerMascota);
        Spinner spinnercuidador = findViewById(R.id.spinnerPaseador);
        EditText editTextfecha = findViewById(R.id.AgendarFecha);
        editTexthora = findViewById(R.id.AgendarHora);
        Spinner cuidador = findViewById(R.id.spinnerPaseador);
        Toolbar agengarTool = findViewById(R.id.toolbarAgendar);
        Button agendarBTN = findViewById(R.id.buttonAgendar);
        setSupportActionBar(agengarTool);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        agengarTool.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LandingPetOwnerActivity.class));
                finish();
            }
        });


        editTexthora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popTimePicker(v);
            }
        });


        agendarBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fecha = editTextfecha.getText().toString();
                String hora = editTexthora.getText().toString();
                String mascota = spinnermascota.getSelectedItem().toString();
                String cuidador = spinnercuidador.getSelectedItem().toString();
                if(mascota.equals("Selecciona una mascota")){
                    ((TextView)spinnermascota.getSelectedView()).setError("Error message");
                }if(fecha.isEmpty()){
                    editTextfecha.setError("Selecciona una fecha valida");
                    editTextfecha.requestFocus();
                }if(hora.isEmpty()){
                    editTexthora.setError("Selecciona una hora valida");
                    editTexthora.requestFocus();
                }
                if(cuidador.equals("Seleccione el paseador")) {
                    ((TextView) spinnercuidador.getSelectedView()).setError("Error message");
                }
                else{
                startActivity(new Intent(getApplicationContext(),LandingPetOwnerActivity.class));
                }
            }
        });

        MaterialDatePicker.Builder<Long> materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("Selecciona la fecha de nacimiento");
        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();
        editTextfecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
            }
        });
        materialDatePicker.addOnPositiveButtonClickListener(
                new MaterialPickerOnPositiveButtonClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onPositiveButtonClick(Object selection) {

                        // if the user clicks on the positive
                        // button that is ok button update the
                        // selected date
                        editTextfecha.setText(materialDatePicker.getHeaderText());
                        // in the above statement, getHeaderText
                        // will return selected date preview from the
                        // dialog
                    }
                });



    }

    public void popTimePicker(View view)
    {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
            {
                hour = selectedHour;
                minute = selectedMinute;
                editTexthora.setText(String.format(Locale.getDefault(), "%02d:%02d",hour, minute));
            }
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