package com.example.petplanet.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;

import com.example.petplanet.R;
import com.example.petplanet.adapters.CardAdapterIniciarpaseo;
import com.example.petplanet.adapters.CardAdapterUsuario;
import com.example.petplanet.models.Perro;
import com.example.petplanet.models.Usuario;

import java.util.ArrayList;

public class IniciarPaseoActivity extends AppCompatActivity {
    GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_paseo);
        gridView = findViewById(R.id.grindDispo);
        ArrayList<Perro> perrosList=new ArrayList<>();
        Toolbar toolbarDispo = findViewById(R.id.toolbarPerDispo);
        setSupportActionBar(toolbarDispo);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbarDispo.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LandingPetWalkerActivity.class));
                finish();
            }
        });

        gridView.setNumColumns(1);
        gridView.setVerticalSpacing(30);
        gridView.setHorizontalSpacing(30);
        try {
            perrosList.add(new Perro("juan pablo","fluffy",R.drawable.perro1));
            perrosList.add(new Perro("carlos","donald",R.drawable.perro2));
            perrosList.add(new Perro("Pedro","BB",R.drawable.perro3));
            ArrayAdapter<Perro> adapter = new CardAdapterIniciarpaseo(this,R.layout.cardviewiniciarpaseo,perrosList);
            if (gridView != null) {
                gridView.setAdapter(adapter);
            }
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getApplicationContext() , ChatActivity.class);
                    Perro items = perrosList.get(position);
                    intent.putExtra("nombre",items.getNombredueno());
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
        startActivity(new Intent(getApplicationContext(), LandingPetWalkerActivity.class));
        finish();
    }
}