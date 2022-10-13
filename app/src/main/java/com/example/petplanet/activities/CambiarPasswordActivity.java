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

import com.example.petplanet.R;
import com.example.petplanet.databinding.ActivityCambiarPasswordBinding;

public class CambiarPasswordActivity extends AppCompatActivity {
    private ActivityCambiarPasswordBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCambiarPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbarCPassword.setTitle("");
        setSupportActionBar(binding.toolbarCPassword);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.toolbarCPassword.setNavigationOnClickListener(v -> {
            // luego se pone despues que se revise el rol del usuario a que pantalla ir
            startActivity(new Intent(getApplicationContext(), PerfilUsuarioActivity.class));
            finish();
        });

        binding.enviarLinkBTN2.setOnClickListener(v -> {

            if(!isEmail(binding.editTextCorreoCP)){
                binding.editTextCorreoCP.setError("Introduce un correo electonico valido");
                binding.editTextCorreoCP.requestFocus();
                return;
            }else{
                Intent intent = new Intent(getApplicationContext(), PerfilUsuarioActivity.class);
                startActivity(intent);
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
        startActivity(new Intent(getApplicationContext(), PerfilUsuarioActivity.class));
        finish();
    }

}