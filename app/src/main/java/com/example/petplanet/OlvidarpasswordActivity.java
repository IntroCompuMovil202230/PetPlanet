package com.example.petplanet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class OlvidarpasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvidarpassword);

        EditText CorreoOlvido = findViewById(R.id.editTextCorreoCambiopsw);
        Button enviar = findViewById(R.id.enviarLinkBTN);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CorreoOlvido.setError(null);
                if(CorreoOlvido.getText().toString().isEmpty()){
                    CorreoOlvido.setError("Introduce un correo electronico");
                    CorreoOlvido.requestFocus();
                    return;
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}