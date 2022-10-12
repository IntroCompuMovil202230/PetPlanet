package com.example.petplanet.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.example.petplanet.adapters.CardAdapterUsuario;
import com.example.petplanet.R;
import com.example.petplanet.databinding.ActivityListaPaseosBinding;
import com.example.petplanet.databinding.ActivityListarCuidadoresBinding;
import com.example.petplanet.models.Usuario;

import java.util.ArrayList;

public class ListarCuidadoresActivity extends AppCompatActivity {
    private ActivityListarCuidadoresBinding binding;
    GridView gridLayout;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListarCuidadoresBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayList<Usuario> cuidadoreslist=new ArrayList<>();
        setSupportActionBar(binding.toolbarListarCuidadores);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.toolbarListarCuidadores.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LandingPetOwnerActivity.class));
                finish();
            }
        });


        binding.grindCuidadores.setNumColumns(2);
        binding.grindCuidadores.setVerticalSpacing(30);
        binding.grindCuidadores.setHorizontalSpacing(30);


        try {
            cuidadoreslist.add(new Usuario("juan pablo","3109988433","me gustan los perros de todo tipo, soy mu cariñoso con todos, trato de no asustarlos ni dejarlos que se peleen entre ellos",R.drawable.perro1));
            cuidadoreslist.add(new Usuario("carlos","3109988453","me encantan los perros pequeños",R.mipmap.ic_launcher_foreground));
            cuidadoreslist.add(new Usuario("Pedro","3019988433","Soy un amante de los labradores",R.mipmap.ic_launcher_foreground));
            cuidadoreslist.add(new Usuario("David","3109978433","Me encantan los perros medianos",R.mipmap.ic_launcher_foreground));
            cuidadoreslist.add(new Usuario("Alejandro","3109588433","Siempre llevo treats para los perros",R.mipmap.ic_launcher_foreground));
            cuidadoreslist.add(new Usuario("David otero","3149988433","Siempre camino mucho con los perros",R.drawable.perro3));
            ArrayAdapter<Usuario> adapter = new CardAdapterUsuario(this,R.layout.cardview,cuidadoreslist);
            if (binding.grindCuidadores != null) {
                binding.grindCuidadores.setAdapter(adapter);
            }
            binding.grindCuidadores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getApplicationContext() , PerfilUsuarioWalkerActivity.class);
                    Usuario items = cuidadoreslist.get(position);

                    intent.putExtra("nombre",items.getNombre());
                    intent.putExtra("telefono",items.getTelefono());
                    intent.putExtra("experiencia",items.getExperiencia());
                    intent.putExtra("imagen",items.getFoto());

                    startActivity(intent);
                    finish();
                }
            });
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