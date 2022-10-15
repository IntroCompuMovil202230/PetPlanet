package com.example.petplanet.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.petplanet.R;
import com.example.petplanet.databinding.ActivityRegistroPerroBinding;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class RegistroPerroActivity extends AppCompatActivity {
    private ActivityRegistroPerroBinding binding;
    String encoded;
    int SELECT_PICTURE = 200;
    int CAMERA_REQUEST = 100;
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

        binding.fotodelperroBTN.setOnClickListener(v -> {
                    final CharSequence[] options = {"Tomar foto", "Elegir de galeria", "Cancelar"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegistroPerroActivity.this);
                    builder.setTitle("Elige una opcion");
                    builder.setItems(options, (dialog, item) -> {
                        if (options[item].equals("Tomar foto")) {
                            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, CAMERA_REQUEST);
                            binding.fotodelperroBTN.setImageURI(Uri.parse(android.provider.MediaStore.ACTION_IMAGE_CAPTURE));

                        } else if (options[item].equals("Elegir de galeria")) {
                            imageChooser();
                        } else if (options[item].equals("Cancelar")) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
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
                intent.putExtra("foto",encoded);
                intent.putExtra("esterilizado",binding.EsterilizadoCheck.isChecked());
                intent.putExtra("vacunado",binding.carnetVacunacionCheck.isChecked());
                intent.putExtra("fechadenacimiento",fechanacimientoS);
                startActivity(intent);
                finish();
            }
        });
    }


    void imageChooser() {

        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }



    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout


                    try {
                        Bitmap img = (Bitmap) MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        img.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        byte[] byteArray = byteArrayOutputStream .toByteArray();
                        encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                        Log.d("imagen", "onActivityResult: " + encoded);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    binding.fotodelperroBTN.setImageURI(selectedImageUri);
                }
            }
        }

        if (requestCode == CAMERA_REQUEST) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
            Log.d("imagen", "onActivityResult: " + encoded);

            binding.fotodelperroBTN.setImageBitmap(image);
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
        startActivity(new Intent(getApplicationContext(), PerfilUsuarioActivity.class));
        finish();
    }
}
