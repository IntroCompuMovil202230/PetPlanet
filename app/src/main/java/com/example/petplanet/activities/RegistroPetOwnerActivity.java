package com.example.petplanet.activities;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petplanet.databinding.ActivityRegistroPetOwnerBinding;
import com.example.petplanet.models.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegistroPetOwnerActivity extends AppCompatActivity {
    private ActivityRegistroPetOwnerBinding binding;
    private FirebaseAuth mAuth;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    public static final String PATH_USERS="users/";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistroPetOwnerBinding.inflate(getLayoutInflater());
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

            String nombreS = binding.registroNombrePetOwner.getText().toString();
            String direccionS = binding.registrotDireccion.getText().toString();
            String passwordS = binding.registroPassword.getText().toString();
            String localidadS = binding.spinnerLocalidad.getSelectedItem().toString();
            if(nombreS.isEmpty()) {
                binding.registroNombrePetOwner.setError("Introduce un nombre");
                binding.registroNombrePetOwner.requestFocus();
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
                validateIfUsersAlreadyExists();
            }
        });






    }





    private void validateIfUsersAlreadyExists() {
        mAuth.fetchSignInMethodsForEmail(binding.registroCorreo.getText().toString()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().getSignInMethods().isEmpty()) {
                    // No existe el usuario
                    createFirebaseAuthUser(String.valueOf(binding.registroCorreo.getText()), String.valueOf(binding.registroPassword.getText()));
                } else {
                    // Existe el usuario
                    Toast.makeText(RegistroPetOwnerActivity.this, "El usuario ya existe", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void createFirebaseAuthUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()){

                saveUser();
            }

        });
    }

    private Usuario createUserObject() {
        return new Usuario(binding.registroNombrePetOwner.getText().toString(),
                binding.spinnerLocalidad.getSelectedItem().toString(),
                binding.registroCorreo.getText().toString(),
                binding.registrotDireccion.getText().toString(),
                false,"");
    }



    private void saveUser() {
        Usuario Client = createUserObject();
        myRef=database.getReference(PATH_USERS+mAuth.getCurrentUser().getUid());
        myRef.setValue(Client).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Toast.makeText(RegistroPetOwnerActivity.this, "Usuario registrado", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),LandingPetOwnerActivity.class));
                finish();
            }else{
                Toast.makeText(RegistroPetOwnerActivity.this, "Error al registrar usuario", Toast.LENGTH_SHORT).show();
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


