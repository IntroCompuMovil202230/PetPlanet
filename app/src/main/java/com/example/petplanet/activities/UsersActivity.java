package com.example.petplanet.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.View;

import android.widget.ArrayAdapter;

import com.example.petplanet.R;
import com.example.petplanet.adapters.CardAdapterUsuario;

import com.example.petplanet.adapters.UsersAdapter;
import com.example.petplanet.databinding.ActivityUsersBinding;
import com.example.petplanet.listeners.UserListener;
import com.example.petplanet.models.Usuario;
import com.example.petplanet.utilities.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;


public class UsersActivity extends AppCompatActivity implements UserListener {

    private ActivityUsersBinding binding;
    private PreferenceManager preferenceManager;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    private FirebaseAuth mAuth;


    public static final String PATH_USERS="users/";
    Usuario walkerx = new Usuario();


    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //preferenceManager = new PreferenceManager(getApplicationContext());
        mAuth = FirebaseAuth.getInstance();
        myRef = database.getReference(PATH_USERS);
        setListeners();
        getUsers();
    }


    private void setListeners() {
        binding.imageback.setOnClickListener(v -> onBackPressed());
    }
    boolean currentwalkerstatus = false;
    private void getUsers(){
        List<Usuario> users = new ArrayList<>();

        myRef.getDatabase().getReference(PATH_USERS).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                //binding.progressBar2.setVisibility(View.INVISIBLE);
                for (DataSnapshot walker : task.getResult().getChildren()) {
                    if(mAuth.getCurrentUser().getUid().equals(walker.getKey())){
                        currentwalkerstatus = walker.child("walker").getValue(Boolean.class);
                    }
                    if(!mAuth.getCurrentUser().getUid().equals(walker.getKey())){
                        walkerx = walker.getValue(Usuario.class);
                        Log.d("walkerx", String.valueOf(currentwalkerstatus));
                        if (!walkerx.getWalker().equals(currentwalkerstatus) || walkerx.getWalker().equals(currentwalkerstatus)) {
                            users.add(new Usuario(walker.getKey(),walkerx.getNombre(),walkerx.getLocalidad(),walkerx.getCorreo(),walkerx.getDireccion(),walkerx.getFoto(),walkerx.getWalker(),walkerx.getExperiencia()));
                        }
                    }
                }
                if(users.size() > 0){
                    UsersAdapter usersAdapter = new UsersAdapter(users,this);
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    binding.usersList.setAdapter(usersAdapter);
                    binding.usersList.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    @Override
    public void onUserClicked(Usuario user) {
        Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
        intent.putExtra(Constants.KEY_USER_ID,user.getId());
        intent.putExtra(Constants.KEY_USER,user.getCorreo());

        startActivity(intent);
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
        startActivity(new Intent(getApplicationContext(), ListaDeChatsActivity.class));
        finish();
    }
}