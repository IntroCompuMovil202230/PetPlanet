package com.example.petplanet.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.example.petplanet.databinding.ActivityFotoPerfilBinding;
import com.example.petplanet.models.Perro;
import com.example.petplanet.models.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class FotoPerfilActivity extends AppCompatActivity {

    private ActivityFotoPerfilBinding binding;
    private  String nombreS, direccionS, emailS, passwordS, localidadS,experiensiaS, tipoS,fotoS;
    private FirebaseAuth mAuth;
    public static final String PATH_USERS="users/";
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    public static final String PATH_PERROS="mascotas/";
    String tokenS;
    ArrayList<String> tokens = new ArrayList<>();
    ArrayList<Perro> perros = new ArrayList<>();
    int SELECT_PICTURE = 200;
    int CAMERA_REQUEST = 100;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFotoPerfilBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                nombreS= null;
                direccionS = null;
                emailS =null;
                passwordS=null;
                localidadS=null;
                tipoS=null;
                experiensiaS=null;

            } else {
                nombreS = extras.getString("nombre");
                direccionS = extras.getString("direccion");
                emailS = extras.getString("correo");
                passwordS =extras.getString("password");
                localidadS = extras.getString("localidad");
                experiensiaS = extras.getString("experiencia");
                tipoS = extras.getString("tipo");
            }
        } else {

            nombreS =        (String) savedInstanceState.getSerializable("nombre");
            direccionS =     (String) savedInstanceState.getSerializable("direccion");
            emailS =         (String) savedInstanceState.getSerializable("correo");
            passwordS =      (String) savedInstanceState.getSerializable("password");
            localidadS =     (String) savedInstanceState.getSerializable("localidad");
            experiensiaS =   (String) savedInstanceState.getSerializable("experiencia");
            tipoS =          (String) savedInstanceState.getSerializable("tipo");
        }


        binding.fotodelusuarioBTN.setOnClickListener(v -> {
            final CharSequence[] options = {"Tomar foto", "Elegir de galeria", "Cancelar"};
            AlertDialog.Builder builder = new AlertDialog.Builder(FotoPerfilActivity.this);
            builder.setTitle("Elige una opcion");
            builder.setItems(options, (dialog, item) -> {
                if (options[item].equals("Tomar foto")) {
                    Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CAMERA_REQUEST);
                    binding.fotodelusuarioBTN.setImageURI(Uri.parse(android.provider.MediaStore.ACTION_IMAGE_CAPTURE));

                } else if (options[item].equals("Elegir de galeria")) {
                    imageChooser();
                } else if (options[item].equals("Cancelar")) {
                    dialog.dismiss();
                }
            });
            builder.show();
        });

        binding.registrarUBTN.setOnClickListener(v -> {

            if(fotoS.isEmpty()) {
                binding.mensajedeerror.setError("Introduce una foto");
                binding.fotodelusuarioBTN.requestFocus();
                return;
            }else{
                createFirebaseAuthUser(emailS,passwordS);
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
                        img.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                        byte[] byteArray = byteArrayOutputStream .toByteArray();
                        fotoS = Base64.encodeToString(byteArray, Base64.DEFAULT);

                        Log.d("imagen", "onActivityResult: " + fotoS);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    binding.fotodelusuarioBTN.setImageURI(selectedImageUri);
                }
            }
        }

        if (requestCode == CAMERA_REQUEST) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            fotoS = Base64.encodeToString(byteArray, Base64.DEFAULT);
            Log.d("imagen", "onActivityResult: " + fotoS);

            binding.fotodelusuarioBTN.setImageBitmap(image);
        }
    }

    private void validateIfUsersAlreadyExists() {
        mAuth.fetchSignInMethodsForEmail(emailS).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().getSignInMethods().isEmpty()) {
                    // No existe el usuario

                } else {
                    // Existe el usuario
                    Toast.makeText(getApplicationContext(), "El usuario ya existe", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void createFirebaseAuthUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                saveUser();
            }

        });
    }

    private Usuario createUserObject() {
        Usuario usu = new Usuario();
        if(tipoS.equals("petowner")) {
            return new Usuario(mAuth.getCurrentUser().getUid(),nombreS,localidadS, emailS, direccionS, fotoS, false, "");
        }
        if(tipoS.equals("petwalker")) {
            return new Usuario(mAuth.getCurrentUser().getUid(),nombreS,localidadS, emailS, direccionS, fotoS, true, experiensiaS);
        }
        return null;
    }

    private void saveUser() {
        Usuario Client = createUserObject();
        myRef=database.getReference(PATH_USERS+mAuth.getCurrentUser().getUid());
        myRef.setValue(Client).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Toast.makeText(getApplicationContext(), "Usuario registrado", Toast.LENGTH_SHORT).show();
                if(tipoS.equals("petowner")) {

                    startActivity(new Intent(getApplicationContext(),LandingPetOwnerActivity.class));
                    finish();

                }
                if(tipoS.equals("petwalker")) {
                    startActivity(new Intent(getApplicationContext(), LandingPetWalkerActivity.class));
                    finish();
                }
            }else{
                Toast.makeText(getApplicationContext(), "Error al registrar usuario", Toast.LENGTH_SHORT).show();
            }
        });
    }


}