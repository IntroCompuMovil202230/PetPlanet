package com.example.petplanet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;

public class CambiarPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_password);
        EditText etPassword = findViewById(R.id.editTextCorreoCambiopsw);
        Button cambioPassword =  findViewById(R.id.enviarLinkBTN);

        cambioPassword.setOnClickListener(v -> {
            if(isEmail(etPassword)){
                etPassword.setError("Introduce un correo electonico valido");
                etPassword.requestFocus();
                return;
            }
        });
    }
    public boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

}