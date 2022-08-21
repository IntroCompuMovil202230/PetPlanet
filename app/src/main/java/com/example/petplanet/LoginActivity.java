package com.example.petplanet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText password = findViewById(R.id.editTextPassword);
        EditText correo = findViewById(R.id.editTextCorreo);
        Button login = findViewById(R.id.iniciosesionBTN);
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

                password.setError(null);
                correo.setError(null);

                if(isEmail(correo) == false){
                    correo.setError("Introduce un correo electonico valido");
                    correo.requestFocus();
                    return;
                }if(password.getText().toString().isEmpty()){
                    password.setError("Introduce una contraseña");
                    password.requestFocus();
                    return;
                }else{

                    /*
                    se tiene que verificar si un usuario es petowner o petwalker con el correo electronico para definir a que pantalla enviar al usuario

                    por esta primera entrega vamos a mandarlo a la petowner activity
                     */
                    Intent intent = new Intent(LoginActivity.this, LandingPetOwnerActivity.class);
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