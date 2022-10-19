package com.example.petplanet.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.petplanet.databinding.ActivityListaDeChatsBinding;

public class ListaDeChatsActivity extends AppCompatActivity {
    private ActivityListaDeChatsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListaDeChatsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbarlistchat.setTitle("");
        setSupportActionBar(binding.toolbarlistchat);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.toolbarlistchat.setNavigationOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(),LandingPetOwnerActivity.class));// esto cambia si el usuario es dueño o walker
            finish();
        });

        binding.addChatBTN.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), UsersActivity.class);
            startActivity(intent);
            finish();
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
        startActivity(new Intent(getApplicationContext(), LandingPetOwnerActivity.class));
        finish();
    }
}