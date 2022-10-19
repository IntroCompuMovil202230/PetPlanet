package com.example.petplanet.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.petplanet.R;
import com.example.petplanet.databinding.ActivityLandingPetOwnerBinding;
import com.example.petplanet.databinding.ActivityLandingPetWalkerBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class LandingPetWalkerActivity extends AppCompatActivity {

    private ActivityLandingPetWalkerBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLandingPetWalkerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        float presionActual;
        SensorManager sensorManager;
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        presionActual = 0;
        presSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);

        SensorManager.registerListener(presSensorListener,presSensor,sensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(presSensorListener,tempSensor,sensorManager.SENSOR_DELAY_NORMAL);
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        SensorEventListener event;

        private SensorEventListener lecturaSensor = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {

                if (Math.abs(presionActual - sensorEvent.values[0]) > 10) {
                    presionActual = sensorEvent.values[0];
                    if (presionActual < 12) {
                        Toast.makeText(LandingPetWalkerActivity.this, "Esta haciendo sol! Relajao", Toast.LENGTH_SHORT).show();
                    } else if (presionActual > 25) {
                        Toast.makeText(LandingPetWalkerActivity.this, "Cuidado, puede llover! busca un paraguas", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }


        binding.bottomNavigationWalker.setBackground(null);
        binding.bottomNavigationWalker.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            switch (id) {
                case R.id.mascotas:
                    Intent intent = new Intent(getApplicationContext(), ListaPaseosActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.IniciarPaseoBTN:
                    Intent intent2 = new Intent(getApplicationContext(), IniciarPaseoActivity.class);
                    startActivity(intent2);
                    finish();
                    break;
                case R.id.chat:
                    Intent intent3 = new Intent(getApplicationContext(), ListaDeChatsActivity.class);
                    startActivity(intent3);
                    finish();
                    break;
                case R.id.perfilA:
                    Intent intent4 = new Intent(getApplicationContext(), PerfilUsuarioActivity.class);
                    startActivity(intent4);
                    finish();
                    break;
            }
            return true;
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
}