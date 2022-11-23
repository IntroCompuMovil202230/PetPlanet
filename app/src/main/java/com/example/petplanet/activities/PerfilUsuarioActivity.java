package com.example.petplanet.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import androidx.biometric.BiometricPrompt;

import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import android.widget.Button;
import android.widget.Toast;


import com.example.petplanet.R;
import com.example.petplanet.adapters.CardAdapterUserDog;
import com.example.petplanet.databinding.ActivityPerfilUsuarioBinding;
import com.example.petplanet.models.Paseo;
import com.example.petplanet.models.Perro;
import com.example.petplanet.models.Usuario;
import com.example.petplanet.utilities.Constants;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;


public class PerfilUsuarioActivity extends AppCompatActivity {
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    private ActivityPerfilUsuarioBinding binding;
    private FirebaseAuth mAuth;

    Usuario Client = new Usuario();
    Perro perrox = new Perro();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    DatabaseReference myUserRef;
    DatabaseReference mydogdel;
    public static final String PATH_USERS = "users/";
    public static final String PATH_PERROS = "/mascotas/";
    String fotoS;
    int SELECT_PICTURE = 200;
    private LocationCallback mLocationCallback;
    private FusedLocationProviderClient fusedLocationClient;
    int CAMERA_REQUEST = 100;
    ArrayList<Perro> perroslist = new ArrayList<>();
    Boolean iswalker;
    ArrayList<Perro> prueba = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPerfilUsuarioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();

        binding.progressBarPerfilUsuario.setVisibility(View.VISIBLE);


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        cargardatos();
        cargandodatosperros();


        binding.grindPerrosdueno.setNumColumns(3);
        binding.grindPerrosdueno.setVerticalSpacing(30);
        binding.grindPerrosdueno.setHorizontalSpacing(30);
        binding.grindPerrosdueno.setLongClickable(true);

        binding.toolbarPusuario.setTitle("");
        setSupportActionBar(binding.toolbarPusuario);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.profilePetUPicture.setOnClickListener(v -> {


            new MaterialAlertDialogBuilder(this)
                    .setTitle("Elige una opcion para subir tu foto")
                    .setPositiveButton("Tomar foto", (dialogInterface, i) -> {
                        if (ContextCompat.checkSelfPermission(PerfilUsuarioActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(PerfilUsuarioActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);
                        } else {
                            if (checkAndRequestPermissions()) {
                                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(intent, CAMERA_REQUEST);
                                binding.profilePetUPicture.setImageURI(Uri.parse(android.provider.MediaStore.ACTION_IMAGE_CAPTURE));
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


        binding.toolbarPusuario.setNavigationOnClickListener(v -> {
            if (Client.getWalker()) {
                startActivity(new Intent(getApplicationContext(), LandingPetWalkerActivity.class));
                finish();
            } else {
                startActivity(new Intent(getApplicationContext(), LandingPetOwnerActivity.class));
                finish();
            }

        });


        binding.changepasswordBTN.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), CambiarPasswordActivity.class);
            intent.putExtra("correo", Client.getCorreo());
            startActivity(intent);
            finish();
        });

        binding.addpet.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), RegistroPerroActivity.class);
            startActivity(intent);
            finish();
        });


        binding.fingerBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Executor executor = ContextCompat.getMainExecutor(PerfilUsuarioActivity.this);

                biometricPrompt = new BiometricPrompt(PerfilUsuarioActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
                    @Override
                    public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                        super.onAuthenticationError(errorCode, errString);
                    }

                    @Override
                    public void onAuthenticationSucceeded(@NonNull androidx.biometric.BiometricPrompt.AuthenticationResult result) {
                        super.onAuthenticationSucceeded(result);
                        //Cambiar de pantalla
                        Log.d("malditasea", String.valueOf(result.toString()));
                    }

                    @Override
                    public void onAuthenticationFailed() {
                        super.onAuthenticationFailed();
                    }
                });

                promptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle("Project").setDescription("Usa tu huella").setDeviceCredentialAllowed(true).build();


                biometricPrompt.authenticate(promptInfo);
            }
        });
    }

    public void cargandodatosperros() {
        myUserRef = database.getReference(PATH_USERS + mAuth.getCurrentUser().getUid());
        myUserRef.getDatabase().getReference(PATH_USERS + mAuth.getCurrentUser().getUid()).child("perros").get().addOnCompleteListener(task1 -> {
            if (task1.isSuccessful()) {
                task1.getResult().getChildren().forEach(perro -> {
                    perrox = perro.getValue(Perro.class);
                    prueba.add(new Perro(perrox.getNombrecompleto(), perrox.getRaza(), perrox.getSexo(), perrox.getColor(), perrox.getFechanacimiento(), perrox.getVacunado(), perrox.getEsterilizado(), perrox.getFoto(), perrox.getRedomendacionesespeciales(), perrox.getRecomendaciones()));
                    ArrayAdapter adapter = new CardAdapterUserDog(this, R.layout.perfilperroview, prueba);
                    binding.grindPerrosdueno.setAdapter(adapter);
                    binding.grindPerrosdueno.setOnItemClickListener((parent, view, position, id) -> {
                        Intent intent = new Intent(getApplicationContext(), PerfilPerroActivity.class);
                        Perro items = prueba.get(position);
                        intent.putExtra("nombredelperro", items.getNombrecompleto());
                        intent.putExtra("fotodelperro", items.getFoto());
                        startActivity(intent);
                        finish();
                    });

                    binding.grindPerrosdueno.setOnItemLongClickListener((parent, view, position, id) -> {
                        new MaterialAlertDialogBuilder(this)
                                .setTitle("Deseas eliminar este perro?")
                                .setMessage("Si lo eliminas no podras recuperarlo")
                                .setPositiveButton("Si eliminalo", (dialogInterface, i) -> {
                                    Perro items = prueba.get(position);


                                    for (int j = 0; j < prueba.size(); j++) {
                                        if (prueba.get(j).getNombrecompleto().equals(items.getNombrecompleto())) {
                                            prueba.remove(j);
                                        }
                                    }
                                    myRef = database.getReference(Constants.PATH_USERS + mAuth.getCurrentUser().getUid());
                                    myRef.getDatabase().getReference(Constants.PATH_USERS + mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            Client = task.getResult().getValue(Usuario.class);
                                            Client.setPerros(prueba);
                                            myRef.setValue(Client);
                                            startActivity(new Intent(getApplicationContext(), PerfilUsuarioActivity.class));
                                            finish();
                                        } else {
                                            Toast.makeText(this, "Error al registrar", Toast.LENGTH_SHORT).show();
                                        }
                                    });


                                    Toast.makeText(this, "se elimino", Toast.LENGTH_SHORT).show();
                                })
                                .setNeutralButton("Cancelar", (dialogInterface, i) -> {

                                })
                                .show();
                        return true;
                    });

                });
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
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        fotoS = Base64.encodeToString(byteArray, Base64.DEFAULT);

                        Log.d("imagen", "onActivityResult: " + fotoS);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    binding.guardatBTN.setVisibility(View.VISIBLE);
                    binding.profilePetUPicture.setImageURI(selectedImageUri);
                }
            }
        }

        if (requestCode == CAMERA_REQUEST) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            fotoS = Base64.encodeToString(byteArray, Base64.DEFAULT);
            Log.d("imagen", "onActivityResult: " + fotoS);

            binding.guardatBTN.setVisibility(View.VISIBLE);
            binding.profilePetUPicture.setImageBitmap(image);
        }


        binding.guardatBTN.setOnClickListener(view -> {
            cargardatos();
            myRef = database.getReference(Constants.PATH_USERS + mAuth.getCurrentUser().getUid());
            myRef.getDatabase().getReference(Constants.PATH_USERS + mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Client = task.getResult().getValue(Usuario.class);
                    Client.setFoto(fotoS);
                    if (prueba.size() > 0) {
                        Client.setPerros(prueba);
                    }
                    myRef.setValue(Client);
                    Toast.makeText(getApplicationContext(), "Foto de perfil actualizada", Toast.LENGTH_SHORT).show();
                    binding.guardatBTN.setVisibility(View.INVISIBLE);
                } else {
                    Toast.makeText(getApplicationContext(), "Error al actualizar foto de perfil", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    public void cargardatos() {
        myRef = database.getReference(PATH_USERS + mAuth.getCurrentUser().getUid());
        myRef.getDatabase().getReference(PATH_USERS + mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Client = task.getResult().getValue(Usuario.class);
                byte[] decodedString = Base64.decode(Client.getFoto(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                binding.profilePetUPicture.setImageBitmap(decodedByte);
                binding.fullNamePet.setText(Client.getNombre());
                binding.direccionUsuario.setText(Client.getDireccion());
                binding.localidadPetOwner.setText(Client.getLocalidad());
                binding.emailtxt.setText(Client.getCorreo());
                if (Client.getWalker()) {
                    binding.addpet.setVisibility(View.GONE);
                    binding.tusmascotastxt.setVisibility(View.GONE);
                    binding.grindPerrosdueno.setVisibility(View.GONE);
                } else {
                    binding.tusmascotastxt.setVisibility(View.VISIBLE);
                    binding.addpet.setVisibility(View.VISIBLE);
                    binding.grindPerrosdueno.setVisibility(View.VISIBLE);
                }
                SystemClock.sleep(100);
                binding.progressBarPerfilUsuario.setVisibility(View.GONE);
                binding.profilePetUPicture.setVisibility(View.VISIBLE);
                binding.fullNamePet.setVisibility(View.VISIBLE);
                binding.direccionUsuario.setVisibility(View.VISIBLE);
                binding.localidadPetOwner.setVisibility(View.VISIBLE);
                binding.emailtxt.setVisibility(View.VISIBLE);
                binding.textViewcorreo.setVisibility(View.VISIBLE);
                binding.textViewdireccion.setVisibility(View.VISIBLE);
                binding.textViewLocalidad.setVisibility(View.VISIBLE);
                binding.textViewnombre.setVisibility(View.VISIBLE);
                binding.fingerBTN.setVisibility(View.VISIBLE);
                binding.changepasswordBTN.setVisibility(View.VISIBLE);

            }
        });
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.perfil_nav_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
// Handle action bar item clicks here. The action bar will
// automatically handle clicks on the Home/Up button, so long
// as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

//noinspection SimplifiableIfStatement
// Display menu item's title by using a Toast.
        if (id == R.id.logoutBtn) {
            stopLocationUpdates();
            mAuth.signOut();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);

            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(mLocationCallback);
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
        if (Client.getWalker()) {
            startActivity(new Intent(getApplicationContext(), LandingPetWalkerActivity.class));
            finish();
        } else {
            startActivity(new Intent(getApplicationContext(), LandingPetOwnerActivity.class));
            finish();
        }
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d("asdasdasd", "On New Intent called");
    }


}