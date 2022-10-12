package com.example.petplanet.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.petplanet.R;

public class RegistroPetWalkerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_pet_walker);

        Toolbar toolbar = findViewById(R.id.toolbarRegPetW);


        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SelecciondeCuentaActivity.class));
                finish();
            }
        });

        EditText nombre = findViewById(R.id.registroNombreCompleto);
        EditText direccion = findViewById(R.id.registrotDireccion);
        EditText password = findViewById(R.id.registroPassword);
        EditText correo = findViewById(R.id.registroCorreo);
        Spinner localidad = findViewById(R.id.registroLocalidad);
        EditText experiencia = findViewById(R.id.registroExperiencia);
        Button subirdocumentoidentidad = findViewById(R.id.subirDocumentoBTN);
        Button subirhojadevida = findViewById(R.id.subirHojadevidaBTN);
        Button crearcuenta = findViewById(R.id.crearcuentaRBTN);

        crearcuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombreS = nombre.getText().toString();
                String direccionS = direccion.getText().toString();
                String passwordS = password.getText().toString();
                String localidadS = localidad.getSelectedItem().toString();
                String experienciaS = experiencia.getText().toString();
                if(nombreS.isEmpty()) {
                    nombre.setError("Introduce un nombre");
                    nombre.requestFocus();
                    return;
                }if(localidadS.equals("Selecciona una localidad")) {
                    ((TextView)localidad.getSelectedView()).setError("Error message");
                }if(direccionS.isEmpty()) {
                    direccion.setError("Introduce una direccion");
                    direccion.requestFocus();
                    return;
                }if(!isEmail(correo)){
                    correo.setError("Introduce un correo electonico valido");
                    correo.requestFocus();
                    return;
                }if(passwordS.isEmpty()) {
                    password.setError("Introduce una contraseña");
                    password.requestFocus();
                    return;
                }if(experienciaS.isEmpty()) {
                    experiencia.setError("Introduce una experiencia");
                    experiencia.requestFocus();
                    return;
                }
                else{
                    Intent intent = new Intent(getApplicationContext() , LandingPetWalkerActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });


    }
    private boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
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
        startActivity(new Intent(getApplicationContext(), SelecciondeCuentaActivity.class));
        finish();
    }
}