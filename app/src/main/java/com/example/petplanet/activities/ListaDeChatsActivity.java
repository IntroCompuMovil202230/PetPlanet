package com.example.petplanet.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.petplanet.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ListaDeChatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_chats);

        FloatingActionButton addChat = findViewById(R.id.addChatBTN);
        addChat.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), UsersActivity.class);
            startActivity(intent);
            finish();
        });
    }
}