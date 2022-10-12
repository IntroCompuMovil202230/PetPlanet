package com.example.petplanet.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.petplanet.R;
import com.example.petplanet.activities.PerfilUsuarioActivity;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;


public class RegistroPerroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_perro);
        Toolbar toolbarRegPet = findViewById(R.id.toolbarRegPetW);


        setSupportActionBar(toolbarRegPet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbarRegPet.setNavigationOnClickListener(v -> {
            // luego se pone despues que se revise el rol del usuario a que pantalla ir
            startActivity(new Intent(getApplicationContext(), PerfilUsuarioActivity.class));
            finish();
        });


        EditText fechanacimiento = findViewById(R.id.registrofechanacimientoperro);
        EditText nombredelperro = findViewById(R.id.registroNombrePet);
        ImageView fotoperro = findViewById(R.id.fotodelperroBTN);
        Button registrarperro = findViewById(R.id.registrarMascotaBT);
        Spinner sexo = findViewById(R.id.spinnersexo);
        Spinner color = findViewById(R.id.spinnercolor);
        MaterialDatePicker.Builder<Long> materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("Selecciona la fecha de nacimiento");
        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();
        fechanacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
            }
        });
        materialDatePicker.addOnPositiveButtonClickListener(
                selection -> {

                    // if the user clicks on the positive
                    // button that is ok button update the
                    // selected date
                    fechanacimiento.setText(materialDatePicker.getHeaderText());
                    // in the above statement, getHeaderText
                    // will return selected date preview from the
                    // dialog
                });

        registrarperro.setOnClickListener(v -> {
            String nombrep =nombredelperro.getText().toString();
            String colorS = color.getSelectedItem().toString();
            String sexoS = sexo.getSelectedItem().toString();
            String fechanacimientoS = fechanacimiento.getText().toString();
            if(nombrep.isEmpty()){
                nombredelperro.setError("Ingrese el nombre de la mascota");
                nombredelperro.requestFocus();
                return;
            }if(sexoS.equals("Seleccione el sexo")){
                ((TextView)sexo.getSelectedView()).setError("Error message");
            }if(colorS.equals("Selecciona un color")){
                ((TextView)color.getSelectedView()).setError("Error message");
            }if(fechanacimientoS.isEmpty()){
                fechanacimiento.setError("Ingrese una fecha de nacimiento valida");
                fechanacimiento.requestFocus();
                return;
            }else{
                Intent intent = new Intent(getApplicationContext(), RazasActivity.class);
                startActivity(intent);
                finish();
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
