package com.example.petplanet.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.petplanet.R;
import com.example.petplanet.adapters.UsersAdapter;
import com.example.petplanet.databinding.ActivityUsersBinding;
import com.example.petplanet.models.Usuario;
import java.util.ArrayList;
import java.util.List;


public class UsersActivity extends AppCompatActivity {

    private ActivityUsersBinding binding;



    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        List<Usuario> cuidadoreslist=new ArrayList<>();
        binding.usersList.setHasFixedSize(true);
        binding.usersList.setVisibility(View.VISIBLE);

        binding.progressBar.setVisibility(View.GONE);
        UsersAdapter adapter;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.usersList.setLayoutManager(linearLayoutManager);
        binding.imageback.setOnClickListener(v -> {
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
                intent.putExtra("nombre",cuidadoreslist.get(binding.usersList.getChildAdapterPosition(view)).getNombre());
                intent.putExtra("foto",cuidadoreslist.get(binding.usersList.getChildAdapterPosition(view)).getFoto());
                startActivity(intent);
                finish();
            });
            binding.usersList.setAdapter(adapter);

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