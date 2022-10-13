package com.example.petplanet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.petplanet.databinding.ActivityLoginBinding;



public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


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
                Intent intent = new Intent(getApplicationContext(), LandingPetOwnerActivity.class);
                intent.putExtra("correo", binding.loginCorreo.getText().toString());
                intent.putExtra("password", binding.loginPassword.getText().toString());
                startActivity(intent);

                finish();
            }
        });


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