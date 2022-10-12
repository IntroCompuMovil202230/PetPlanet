package com.example.petplanet.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.petplanet.R;
import com.example.petplanet.adapters.CardAdapterUsuario;
import com.example.petplanet.adapters.UsersAdapter;
import com.example.petplanet.models.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsersActivity extends AppCompatActivity {

    RecyclerView recyclerView;



    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        List<Usuario> cuidadoreslist=new ArrayList<>();
        ImageView back = findViewById(R.id.imageback);
        recyclerView = findViewById(R.id.usersList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setVisibility(View.VISIBLE);

        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        UsersAdapter adapter;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        back.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), LandingPetOwnerActivity.class));
            finish();
        });

        try {
            cuidadoreslist.add(new Usuario("carlos","3109988453",R.mipmap.ic_launcher_foreground));
            cuidadoreslist.add(new Usuario("Pedro","3019988433",R.mipmap.ic_launcher_foreground));
            cuidadoreslist.add(new Usuario("Alejandro","3109588433",R.mipmap.ic_launcher_foreground));
            cuidadoreslist.add(new Usuario("David otero","3149988433",R.drawable.perro3));

            adapter = new UsersAdapter(cuidadoreslist,this);
            adapter.setOnClickListener(view -> {
                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                intent.putExtra("nombre",cuidadoreslist.get(recyclerView.getChildAdapterPosition(view)).getNombre());
                intent.putExtra("foto",cuidadoreslist.get(recyclerView.getChildAdapterPosition(view)).getFoto());
                startActivity(intent);
                finish();
            });
            recyclerView.setAdapter(adapter);

        }catch (Exception e) {
            e.printStackTrace();
        }
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