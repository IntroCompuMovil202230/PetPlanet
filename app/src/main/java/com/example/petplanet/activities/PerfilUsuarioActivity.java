package com.example.petplanet.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import androidx.biometric.BiometricPrompt;
import android.net.Uri;
import android.os.Bundle;
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
import com.example.petplanet.models.Perro;
import com.example.petplanet.models.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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
    public static final String PATH_USERS="users/";
    public static final String PATH_PERROS="/mascotas/";
    String fotoS;
    int SELECT_PICTURE = 200;
    int CAMERA_REQUEST = 100;
    ArrayList<Perro> perroslist =new ArrayList<>();
    Boolean iswalker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPerfilUsuarioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        binding.guardatBTN.setVisibility(View.INVISIBLE);


        myRef=database.getReference(PATH_USERS+mAuth.getCurrentUser().getUid());
        myRef.getDatabase().getReference(PATH_USERS+mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Client = task.getResult().getValue(Usuario.class);
                byte[] decodedString = Base64.decode(Client.getFoto(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                binding.profilePetUPicture.setImageBitmap(decodedByte);
                binding.fullNamePet.setText(Client.getNombre());
                binding.direccionUsuario.setText(Client.getDireccion());
                binding.localidadPetOwner.setText(Client.getLocalidad());
                binding.emailtxt.setText(Client.getCorreo());
                if(Client.getWalker()) {
                    binding.addpet.setVisibility(View.INVISIBLE);
                }
                else{
                    binding.addpet.setVisibility(View.VISIBLE);
                }
            }
        });
        ArrayList<Perro> prueba = new ArrayList<>();

        binding.grindPerrosdueno.setNumColumns(3);
        binding.grindPerrosdueno.setVerticalSpacing(30);
        binding.grindPerrosdueno.setHorizontalSpacing(30);

        myUserRef=database.getReference(PATH_USERS+mAuth.getCurrentUser().getUid()+PATH_PERROS);
        myUserRef.getDatabase().getReference(PATH_USERS+mAuth.getCurrentUser().getUid()+PATH_PERROS).child("perros").get().addOnCompleteListener(task1 -> {
            if (task1.isSuccessful()) {
                Log.d("malditasea", "onComplete: "+task1.getResult().getValue());
                task1.getResult().getChildren().forEach(perro -> {
                    perrox = perro.getValue(Perro.class);
                    Log.d("malditasea", "onComplete: "+perrox.getNombrecompleto());
                    prueba.add(new Perro(perrox.getNombrecompleto(),perrox.getRaza(),perrox.getSexo(),perrox.getColor(),perrox.getFechanacimiento(),perrox.getVacunado(),perrox.getEsterilizado(),perrox.getFoto()));
                    Log.d("malditasea", "onComplete: "+prueba.size());

                    ArrayAdapter adapter = new CardAdapterUserDog(this,R.layout.perfilperroview,prueba);
                    binding.grindPerrosdueno.setAdapter(adapter);
                    binding.grindPerrosdueno.setOnItemClickListener((parent, view, position, id) -> {
                        Intent intent = new Intent(getApplicationContext() , PerfilPerroActivity.class);
                        Perro items = prueba.get(position);
                        intent.putExtra("nombredelperro",items.getNombrecompleto());
                        intent.putExtra("imagen",items.getFoto());
                        startActivity(intent);
                        finish();
                    });
                });

            }
        });


        binding.toolbarPusuario.setTitle("");
        setSupportActionBar(binding.toolbarPusuario);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.profilePetUPicture.setOnClickListener(v -> {
            final CharSequence[] options = {"Tomar foto", "Elegir de galeria", "Cancelar"};
            AlertDialog.Builder builder = new AlertDialog.Builder(PerfilUsuarioActivity.this);
            builder.setTitle("Elige una opcion");
            builder.setItems(options, (dialog, item) -> {
                if (options[item].equals("Tomar foto")) {
                    Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CAMERA_REQUEST);
                    binding.profilePetUPicture.setImageURI(Uri.parse(android.provider.MediaStore.ACTION_IMAGE_CAPTURE));
                } else if (options[item].equals("Elegir de galeria")) {
                    imageChooser();
                } else if (options[item].equals("Cancelar")) {
                    dialog.dismiss();
                }
            });
            builder.show();
        });


        binding.toolbarPusuario.setNavigationOnClickListener(v -> {
            // luego se pone despues que se revise el rol del usuario a que pantalla ir
            startActivity(new Intent(getApplicationContext(),LandingPetOwnerActivity.class));
            finish();
        });


        binding.changepasswordBTN.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), CambiarPasswordActivity.class);
            intent.putExtra("correo",Client.getCorreo());
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
                    public void onAuthenticationError(int errorCode, @NonNull  CharSequence errString) {
                        super.onAuthenticationError(errorCode, errString);
                    }

                    @Override
                    public void onAuthenticationSucceeded(@NonNull androidx.biometric.BiometricPrompt.AuthenticationResult result) {
                        super.onAuthenticationSucceeded(result);
                        //Cambiar de pantalla
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
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            fotoS = Base64.encodeToString(byteArray, Base64.DEFAULT);
            Log.d("imagen", "onActivityResult: " + fotoS);

            binding.guardatBTN.setVisibility(View.VISIBLE);
            binding.profilePetUPicture.setImageBitmap(image);
        }
        binding.guardatBTN.setOnClickListener(view -> {
            Client.setFoto(fotoS);
            myRef=database.getReference(PATH_USERS+mAuth.getCurrentUser().getUid());
            myRef.setValue(Client).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Foto de perfil actualizada", Toast.LENGTH_SHORT).show();
                    binding.guardatBTN.setVisibility(View.INVISIBLE);
                }else{
                    Toast.makeText(getApplicationContext(), "Error al actualizar foto de perfil", Toast.LENGTH_SHORT).show();
                }
            });
        });
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

            mAuth.signOut();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
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

        //aca toca un if para ver si es dueño o cuidador y mandarlo a la pantalla correspondiente
        startActivity(new Intent(getApplicationContext(), LandingPetOwnerActivity.class));
        finish();
    }
}