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
import com.example.petplanet.databinding.ActivityMainBinding;
import com.example.petplanet.databinding.ActivityOlvidarpasswordBinding;

public class OlvidarpasswordActivity extends AppCompatActivity {
    private ActivityOlvidarpasswordBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOlvidarpasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.toolbarolv.setTitle("");
        setSupportActionBar(binding.toolbarolv);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.toolbarolv.setNavigationOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            finish();
        });


        binding.enviarLinkBTN.setOnClickListener(v -> {

            if(!isEmail(binding.editTextCorreoCambiopsw)){
                binding.editTextCorreoCambiopsw.setError("Introduce un correo electronico");
                binding.editTextCorreoCambiopsw.requestFocus();
                return;
            }
            else {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
}