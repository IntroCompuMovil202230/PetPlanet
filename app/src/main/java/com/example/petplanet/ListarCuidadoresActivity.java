package com.example.petplanet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import org.json.JSONException;

import java.util.ArrayList;

public class ListarCuidadoresActivity extends AppCompatActivity {
    GridView gridLayout;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_cuidadores);
        ArrayList<Usuario> cuidadoreslist=new ArrayList<>();
        Toolbar toolbarCuida = findViewById(R.id.toolbarListarCuidadores);
        setSupportActionBar(toolbarCuida);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbarCuida.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LandingPetOwnerActivity.class));
            }
        });

        gridLayout = findViewById(R.id.grindCuidadores);
        gridLayout.setNumColumns(2);
        gridLayout.setVerticalSpacing(30);
        gridLayout.setHorizontalSpacing(30);


        try {
            cuidadoreslist.add(new Usuario("juan pablo","3109988433","me gustan los perros de todo tipo, soy mu cariñoso con todos, trato de no asustarlos ni dejarlos que se peleen entre ellos",R.drawable.perro1));
            cuidadoreslist.add(new Usuario("carlos","3109988453","me encantan los perros pequeños",R.mipmap.ic_launcher_foreground));
            cuidadoreslist.add(new Usuario("Pedro","3019988433","Soy un amante de los labradores",R.mipmap.ic_launcher_foreground));
            cuidadoreslist.add(new Usuario("David","3109978433","Me encantan los perros medianos",R.mipmap.ic_launcher_foreground));
            cuidadoreslist.add(new Usuario("Alejandro","3109588433","Siempre llevo treats para los perros",R.mipmap.ic_launcher_foreground));
            cuidadoreslist.add(new Usuario("David otero","3149988433","Siempre camino mucho con los perros",R.drawable.perro3));
            ArrayAdapter<Usuario> adapter = new CardAdapterUsuario(this,R.layout.cardview,cuidadoreslist);
            if (gridLayout != null) {
                gridLayout.setAdapter(adapter);
            }
            gridLayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getApplicationContext() , PerfilUsuarioWalkerActivity.class);
                    Usuario items = cuidadoreslist.get(position);

                    intent.putExtra("nombre",items.getNombre());
                    intent.putExtra("telefono",items.getTelefono());
                    intent.putExtra("experiencia",items.getExperiencia());
                    intent.putExtra("imagen",items.getFoto());

                    startActivity(intent);
                }
            });
        }catch (Exception e) {
            e.printStackTrace();
        }





    }
}