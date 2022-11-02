package com.example.petplanet.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petplanet.databinding.ActivityRegistroPetWalkerBinding;
import com.example.petplanet.models.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistroPetWalkerActivity extends AppCompatActivity {
    private ActivityRegistroPetWalkerBinding binding;

    private FirebaseAuth mAuth;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    public static final String PATH_USERS="users/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistroPetWalkerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        mAuth = FirebaseAuth.getInstance();
        setSupportActionBar(binding.toolbarRegPetW);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.toolbarRegPetW.setNavigationOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(),SelecciondeCuentaActivity.class));
            finish();
        });

        binding.crearcuentaRBTN.setOnClickListener(v -> {
            String nombreS = binding.registroNombreCompleto.getText().toString();
            String direccionS = binding.registrotDireccion.getText().toString();
            String passwordS = binding.registroPassword.getText().toString();
            String localidadS = binding.registroLocalidad.getSelectedItem().toString();
            String experienciaS = binding.registroExperiencia.getText().toString();
            if(nombreS.isEmpty()) {
                binding.registroNombreCompleto.setError("Introduce un nombre");
                binding.registroNombreCompleto.requestFocus();
                return;
            }if(localidadS.equals("Selecciona una localidad")) {
                ((TextView)binding.registroLocalidad.getSelectedView()).setError("Error message");
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
            }if(experienciaS.isEmpty()) {
                binding.registroExperiencia.setError("Introduce una experiencia");
                binding.registroExperiencia.requestFocus();
                return;
            }
            else{
                validateIfUsersAlreadyExists();
            }
        });


    }


    private void validateIfUsersAlreadyExists() {
        mAuth.fetchSignInMethodsForEmail(binding.registroCorreo.getText().toString()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().getSignInMethods().isEmpty()) {
                    // No existe el usuario
                    Intent intent = new Intent(getApplicationContext(), FotoPerfilActivity.class);
                    intent.putExtra("nombre",binding.registroNombreCompleto.getText().toString());
                    intent.putExtra("direccion",binding.registrotDireccion.getText().toString());
                    intent.putExtra("localidad",binding.registroLocalidad.getSelectedItem().toString());
                    intent.putExtra("correo",binding.registroCorreo.getText().toString());
                    intent.putExtra("password",binding.registroPassword.getText().toString());
                    intent.putExtra("experiencia",binding.registroExperiencia.getText().toString());
                    intent.putExtra("tipo","petwalker");
                    startActivity(intent);
                    finish();
                } else {
                    // Existe el usuario
                    Toast.makeText(getApplicationContext(), "El usuario ya existe", Toast.LENGTH_SHORT).show();
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