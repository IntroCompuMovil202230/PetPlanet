package com.example.petplanet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.example.petplanet.databinding.ActivityLoginBinding;
import com.example.petplanet.models.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.Executor;


public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

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

        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(LoginActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Autenticación Biometrica")
                        .setSubtitle("Inicia Sesión con tu huella")
                                .setNegativeButtonText("Use Password")
                                        .build();

        binding.huella.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                biometricPrompt.authenticate(promptInfo);
                onStart();
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