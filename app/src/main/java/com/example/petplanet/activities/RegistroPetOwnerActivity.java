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
import com.example.petplanet.databinding.ActivityRegistroPerroBinding;
import com.example.petplanet.databinding.ActivityRegistroPetOwnerBinding;

public class RegistroPetOwnerActivity extends AppCompatActivity {
    private ActivityRegistroPetOwnerBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistroPetOwnerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());





        setSupportActionBar(binding.toolbarRegPetW);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.toolbarRegPetW.setNavigationOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(),SelecciondeCuentaActivity.class));
            finish();
        });

        binding.crearcuentaRBTN.setOnClickListener(v -> {

            String nombreS = binding.registroNombrePet.getText().toString();
            String direccionS = binding.registrotDireccion.getText().toString();
            String passwordS = binding.registroPassword.getText().toString();
            String localidadS = binding.spinnerLocalidad.getSelectedItem().toString();
            if(nombreS.isEmpty()) {
                binding.registroNombrePet.setError("Introduce un nombre");
                binding.registroNombrePet.requestFocus();
                return;
            }if(localidadS.equals("Selecciona una localidad")) {
                ((TextView)binding.spinnerLocalidad.getSelectedView()).setError("Error message");
            }if(direccionS.isEmpty()) {
                binding.registrotDireccion.setError("Introduce una direccion");
                binding.registrotDireccion.requestFocus();
                return;
            }if(!isEmail(binding.registroCorreo)){
                binding.registroCorreo.setError("Introduce un correo electonico valido");
                binding.registroCorreo.requestFocus();
                return;
            }if(passwordS.isEmpty()) {
                binding.registroPassword.setError("Introduce una contraseña");
                binding.registroPassword.requestFocus();
                return;
            }
            else{
                Intent intent = new Intent(getApplicationContext() , RegistroPerroActivity.class);
                startActivity(intent);
                finish();
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


