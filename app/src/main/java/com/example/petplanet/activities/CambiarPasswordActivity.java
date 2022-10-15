package com.example.petplanet.activities;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.petplanet.R;
import com.example.petplanet.databinding.ActivityCambiarPasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CambiarPasswordActivity extends AppCompatActivity {
    private ActivityCambiarPasswordBinding binding;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String correo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCambiarPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                correo= null;

            } else {
                correo = extras.getString("correo");


            }
        } else {
            correo = (String) savedInstanceState.getSerializable("correo");
        }



        binding.toolbarCPassword.setTitle("");
        setSupportActionBar(binding.toolbarCPassword);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.toolbarCPassword.setNavigationOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), PerfilUsuarioActivity.class));
            finish();
        });
        binding.cambiarpasswordBTN.setOnClickListener(v -> {
            AuthCredential credential = EmailAuthProvider
                    .getCredential(correo, binding.oldpass.getText().toString());
            user.reauthenticate(credential)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            user.updatePassword(binding.newpass.getText().toString()).addOnCompleteListener(task1 -> {
                                if (task1.isSuccessful()) {
                                    Toast.makeText(CambiarPasswordActivity.this, "Contraseña cambiada", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "Password updated");
                                    startActivity(new Intent(getApplicationContext(), PerfilUsuarioActivity.class));
                                    finish();
                                } else {
                                    Log.d(TAG, "Error password not updated");
                                }
                            });
                        } else {
                            binding.oldpass.setError("Contraseña incorrecta");
                            Log.d(TAG, "Error auth failed");
                        }
                    });
        });
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