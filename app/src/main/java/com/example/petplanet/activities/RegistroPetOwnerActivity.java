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

public class RegistroPetOwnerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_pet_owner);

        Toolbar toolbar = findViewById(R.id.toolbarRegPetW);
        EditText correo =  findViewById(R.id.registroCorreo);
        EditText nombre = findViewById(R.id.registroNombrePet);
        EditText direccion =  findViewById(R.id.registrotDireccion);
        EditText password =  findViewById(R.id.registroPassword);
        Spinner localidad = findViewById(R.id.spinnerraza);
        Button creaccuenta = findViewById(R.id.crearcuentaRBTN);


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

        creaccuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombreS = nombre.getText().toString();
                String direccionS = direccion.getText().toString();
                String passwordS = password.getText().toString();
                String localidadS = localidad.getSelectedItem().toString();
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
                }
                else{
                    Intent intent = new Intent(getApplicationContext() , RegistroPerroActivity.class);
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

}


