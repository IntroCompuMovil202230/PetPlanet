package com.example.petplanet.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petplanet.R;
import com.example.petplanet.databinding.ActivityRegistroPerroBinding;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


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
            new MaterialAlertDialogBuilder(this)
                    .setTitle("Elige una opcion para subir tu foto")
                    .setPositiveButton("Tomar foto", (dialogInterface, i) -> {
                        if (ContextCompat.checkSelfPermission(RegistroPerroActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(RegistroPerroActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);
                            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, CAMERA_REQUEST);
                            binding.fotodelperroBTN.setImageURI(Uri.parse(android.provider.MediaStore.ACTION_IMAGE_CAPTURE));
                        } else {
                            if (checkAndRequestPermissions()) {
                                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(intent, CAMERA_REQUEST);
                                binding.fotodelperroBTN.setImageURI(Uri.parse(android.provider.MediaStore.ACTION_IMAGE_CAPTURE));
                            }
                        }
                    })
                    .setNeutralButton("Cancelar", (dialogInterface, i) -> {
                    })
                    .setNegativeButton("Elegir de galeria", (dialogInterface, i) -> {
                        if (checkAndRequestPermissionsStorage()) {
                            imageChooser();
                        } else {
                            Toast.makeText(this, "No se puede acceder a la galeria", Toast.LENGTH_SHORT).show();
                        }
                    })

                    .show();
        });


        binding.seguirconelregistroBTN.setOnClickListener(v -> {
            String nombrep = binding.registroNombrePet.getText().toString();
            String colorS = binding.spinnercolortxt.getText().toString();
            String sexoS = binding.spinnersexotxt.getText().toString();
            String fechanacimientoS = binding.registrofechanacimientoperro.getText().toString();
            if (nombrep.isEmpty()) {
                binding.registroNombrePet.setError("Ingrese el nombre de la mascota");
                binding.registroNombrePet.requestFocus();
                return;
            }
            if (sexoS.isEmpty()) {
                binding.spinnersexo.setError("Seleccione el sexo de la mascota");
            }
            if (colorS.isEmpty()) {
                binding.spinnercolor.setError("Seleccione el color de la mascota");
            }
            if (fechanacimientoS.isEmpty()) {
                binding.registrofechanacimientoperro.setError("Ingrese una fecha de nacimiento valida");
                binding.registrofechanacimientoperro.requestFocus();
                return;
            } else {
                Intent intent = new Intent(getApplicationContext(), RazasActivity.class);
                intent.putExtra("nombredelamascota", nombrep);
                intent.putExtra("colordelperro", colorS);
                intent.putExtra("sexoselamascota", sexoS);
                intent.putExtra("foto", encoded);
                intent.putExtra("esterilizado", binding.EsterilizadoCheck.isChecked());
                intent.putExtra("vacunado", binding.carnetVacunacionCheck.isChecked());
                intent.putExtra("fechadenacimiento", fechanacimientoS);
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


    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    private boolean checkAndRequestPermissions() {
        int permissionCamera = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionCamera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }

        return true;
    }

    private boolean checkAndRequestPermissionsStorage() {
        int permissionWritestorage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionWritestorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
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
                        img.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
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
            byte[] byteArray = byteArrayOutputStream.toByteArray();
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
