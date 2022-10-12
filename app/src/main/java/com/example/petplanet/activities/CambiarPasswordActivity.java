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

public class CambiarPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_password);
        EditText etPassword = findViewById(R.id.editTextCorreoCP);
        Button cambioPassword =  findViewById(R.id.enviarLinkBTN2);


        Toolbar tollbarPassword = findViewById(R.id.toolbarCPassword);
        tollbarPassword.setTitle("");
        setSupportActionBar(tollbarPassword);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tollbarPassword.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // luego se pone despues que se revise el rol del usuario a que pantalla ir
                startActivity(new Intent(getApplicationContext(), PerfilUsuarioActivity.class));
                finish();
            }
        });

        cambioPassword.setOnClickListener(v -> {

            if(!isEmail(etPassword)){
                etPassword.setError("Introduce un correo electonico valido");
                etPassword.requestFocus();
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