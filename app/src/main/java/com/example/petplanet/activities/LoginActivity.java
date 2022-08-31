package com.example.petplanet.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.petplanet.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText password = findViewById(R.id.registroPassword);
        EditText correo = findViewById(R.id.registroCorreo);
        Button login = findViewById(R.id.crearcuentaRBTN);
        TextView olvido = findViewById(R.id.olvidocontra);
        TextView registro = findViewById(R.id.nuevousuario);

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SelecciondeCuentaActivity.class);
                startActivity(intent);
                finish();
            }
        });

        olvido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, OlvidarpasswordActivity.class);
                startActivity(intent);

                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pass = password.getText().toString();


                if(!isEmail(correo)){
                    correo.setError("Introduce un correo electonico valido");
                    correo.requestFocus();
                    return;
                }if(pass.isEmpty()){
                    password.setError("Introduce una contraseña");
                    password.requestFocus();
                    return;
                }else{
                    Intent intent = new Intent(getApplicationContext(), LandingPetOwnerActivity.class);
                    intent.putExtra("correo", correo.getText().toString());
                    intent.putExtra("password", password.getText().toString());
                    startActivity(intent);

                    finish();
                }
            }




        });


    }

    @Override
    public void onBackPressed() {
        finish();
    }
    public boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

}