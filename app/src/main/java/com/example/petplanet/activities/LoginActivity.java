package com.example.petplanet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.petplanet.databinding.ActivityLoginBinding;
import com.example.petplanet.models.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();

        binding.nuevousuario.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SelecciondeCuentaActivity.class);
            startActivity(intent);
            finish();
        });

        binding.olvidocontra.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, OlvidarpasswordActivity.class);
            startActivity(intent);

            finish();
        });

        binding.loginBTN.setOnClickListener(v -> {

            String pass = binding.loginPassword.getText().toString();


            if(!isEmail(binding.loginCorreo)){
                binding.loginCorreo.setError("Introduce un correo electonico valido");
                binding.loginCorreo.requestFocus();
                return;
            }if(pass.isEmpty()){
                binding.loginPassword.setError("Introduce una contraseña");
                binding.loginPassword.requestFocus();
                return;
            }else{

                login(String.valueOf(binding.loginCorreo.getText()), String.valueOf(binding.loginPassword.getText()));
            }
        });


    }

    private void login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                updateUI(mAuth.getCurrentUser());
            }
            else {
                showMessage(task.getException().getMessage());
            }
        });

    }

    private void showMessage(String text) {
        Toast.makeText(getApplicationContext(),text, Toast.LENGTH_LONG).show();
    }
    private void updateUI(FirebaseUser user) {
        if (user != null) {
            myRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());

            myRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()){

                    if(task.getResult().exists()){
                        Usuario usuario = task.getResult().getValue(Usuario.class);

                        assert usuario != null;
                        if(usuario.getWalker())
                            startActivity(new Intent(getApplicationContext(),LandingPetWalkerActivity.class));
                        else
                            startActivity(new Intent(getApplicationContext(),LandingPetOwnerActivity.class));
                    }
                }
            });

        }
    }


    public boolean isEmail(EditText text) {
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
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            updateUI(currentUser);
        }
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
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}