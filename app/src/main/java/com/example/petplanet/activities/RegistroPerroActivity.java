package com.example.petplanet.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import com.example.petplanet.databinding.ActivityRegistroPerroBinding;
import com.google.android.material.datepicker.MaterialDatePicker;



public class RegistroPerroActivity extends AppCompatActivity {
    private ActivityRegistroPerroBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistroPerroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        setSupportActionBar(binding.toolbarRegPetW);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.toolbarRegPetW.setNavigationOnClickListener(v -> {
            // luego se pone despues que se revise el rol del usuario a que pantalla ir
            startActivity(new Intent(getApplicationContext(), PerfilUsuarioActivity.class));
            finish();
        });


        MaterialDatePicker.Builder<Long> materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("Selecciona la fecha de nacimiento");
        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();
        binding.registrofechanacimientoperro.setOnClickListener(v -> materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER"));
        materialDatePicker.addOnPositiveButtonClickListener(
                selection -> {

                    // if the user clicks on the positive
                    // button that is ok button update the
                    // selected date
                    binding.registrofechanacimientoperro.setText(materialDatePicker.getHeaderText());
                    // in the above statement, getHeaderText
                    // will return selected date preview from the
                    // dialog
                });

        binding.seguirconelregistroBTN.setOnClickListener(v -> {
            String nombrep =binding.registroNombrePet.getText().toString();
            String colorS = binding.spinnercolor.getSelectedItem().toString();
            String sexoS = binding.spinnersexo.getSelectedItem().toString();
            String fechanacimientoS = binding.registrofechanacimientoperro.getText().toString();
            if(nombrep.isEmpty()){
                binding.registroNombrePet.setError("Ingrese el nombre de la mascota");
                binding.registroNombrePet.requestFocus();
                return;
            }if(sexoS.equals("Seleccione el sexo")){
                ((TextView)binding.spinnersexo.getSelectedView()).setError("Error message");
            }if(colorS.equals("Selecciona un color")){
                ((TextView)binding.spinnercolor.getSelectedView()).setError("Error message");
            }if(fechanacimientoS.isEmpty()){
                binding.registrofechanacimientoperro.setError("Ingrese una fecha de nacimiento valida");
                binding.registrofechanacimientoperro.requestFocus();
                return;
            }else{
                Intent intent = new Intent(getApplicationContext(), RazasActivity.class);
                intent.putExtra("nombredelamascota",nombrep);
                intent.putExtra("colordelperro",colorS);
                intent.putExtra("sexoselamascota",sexoS);
                intent.putExtra("fechadenacimiento",fechanacimientoS);
                startActivity(intent);
                finish();
            }
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
