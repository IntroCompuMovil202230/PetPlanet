package com.example.petplanet.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.petplanet.R;
import com.example.petplanet.databinding.ActivityLandingPetOwnerBinding;
import com.example.petplanet.databinding.ActivityLandingPetWalkerBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class LandingPetWalkerActivity extends AppCompatActivity {


    private SensorManager sensorManager;
    private Sensor lightSensor,tempSensor;
    private SensorEventListener lightSensorListener, tempSensorListener;
    private float tempActual;

    private ActivityLandingPetWalkerBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLandingPetWalkerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        tempSensorListener = lecturaTemperatura;
        tempActual = 0;
        sensorManager.registerListener(tempSensorListener,tempSensor,sensorManager.SENSOR_DELAY_NORMAL);

        SensorEventListener lecturaSensor = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                    if(Math.abs(tempActual-sensorEvent.values[0])>10){
                        tempActual = sensorEvent.values[0];
                        if(tempActual<12)
                        {
                            Toast.makeText(LandingPetWalkerActivity.this, "La temperatura es muy baja, abríguese mijo!", Toast.LENGTH_SHORT).show();
                        }else if(tempActual>25) {
                            Toast.makeText(LandingPetWalkerActivity.this, "La temperatura es alta, toma agüita!", Toast.LENGTH_SHORT).show();
                        }
                    }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

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
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(tempSensorListener,tempSensor,sensorManager.SENSOR_DELAY_NORMAL);
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
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(tempSensorListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(tempSensorListener);
    }
}