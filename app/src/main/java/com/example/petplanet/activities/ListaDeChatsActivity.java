package com.example.petplanet.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;


import com.example.petplanet.databinding.ActivityListaDeChatsBinding;

import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.petplanet.R;
import com.example.petplanet.adapters.RecentConversationsAdapter;
import com.example.petplanet.databinding.ActivityLandingPetWalkerBinding;
import com.example.petplanet.databinding.ActivityListaDeChatsBinding;
import com.example.petplanet.listeners.ConversationListener;
import com.example.petplanet.models.ChatMessage;
import com.example.petplanet.models.Perro;
import com.example.petplanet.models.Usuario;
import com.example.petplanet.utilities.Constants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListaDeChatsActivity extends AppCompatActivity implements ConversationListener {
    private ActivityListaDeChatsBinding binding;
    private FirebaseAuth mAuth;
    Usuario Client = new Usuario();
    Perro perrox = new Perro();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    DatabaseReference myUserRef;
    public static final String PATH_USERS="users/";

    private List<ChatMessage> conversations;
    private RecentConversationsAdapter conversationsAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListaDeChatsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        conversations = new ArrayList<>();
        conversationsAdapter = new RecentConversationsAdapter(conversations,this);
        binding.conversationsRecyclerView.setAdapter(conversationsAdapter);
        binding.toolbarlistchat.setTitle("");
        setSupportActionBar(binding.toolbarlistchat);

        myRef=database.getReference(PATH_USERS+mAuth.getCurrentUser().getUid());
        myRef.getDatabase().getReference(PATH_USERS+mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Client = task.getResult().getValue(Usuario.class);
                byte[] decodedString = Base64.decode(Client.getFoto(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            }
        });

        myRef = database.getReference(Constants.KEY_COLLECTION_CONVERSATIONS);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                conversations.clear();
                for (DataSnapshot conversation : snapshot.getChildren()) {
                    if (conversation.child(Constants.KEY_SENDER_ID).getValue().equals(mAuth.getCurrentUser().getUid())) {
                        ChatMessage chatMessage = new ChatMessage();
                        chatMessage.setSenderid(conversation.child(Constants.KEY_SENDER_ID).getValue().toString());
                        chatMessage.setReceiverid(conversation.child(Constants.KEY_RECEIVER_ID).getValue().toString());
                        if(mAuth.getCurrentUser().getUid().equals(chatMessage.getSenderid())){
                            chatMessage.conversionImage = conversation.child(Constants.KEY_RECEIVER_IMAGE).getValue().toString();
                            chatMessage.conversionName = conversation.child(Constants.KEY_RECEIVER_NAME).getValue().toString();
                            chatMessage.conversionId = conversation.child(Constants.KEY_RECEIVER_ID).getValue().toString();
                        }else{
                            chatMessage.conversionImage = conversation.child(Constants.KEY_SENDER_IMAGE).getValue().toString();
                            chatMessage.conversionName = conversation.child(Constants.KEY_SENDER_NAME).getValue().toString();
                            chatMessage.conversionId = conversation.child(Constants.KEY_SENDER_ID).getValue().toString();
                        }
                        chatMessage.setMessage(conversation.child(Constants.KEY_LAST_MESSAGE).getValue().toString());
                        chatMessage.setDatetime(conversation.child(Constants.KEY_TIMESTAMP).getValue().toString());
                        conversations.add(chatMessage);
                    }
                }
                Collections.sort(conversations, (o1, o2) -> o2.getDatetime().compareTo(o1.getDatetime()));
                conversationsAdapter.notifyDataSetChanged();
                binding.conversationsRecyclerView.smoothScrollToPosition(0);
                binding.conversationsRecyclerView.setVisibility(View.VISIBLE);
                binding.progresconversation.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //getToken();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.toolbarlistchat.setNavigationOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(),LandingPetOwnerActivity.class));// esto cambia si el usuario es dueño o walker
            finish();
        });

        binding.addChatBTN.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), UsersActivity.class);
            startActivity(intent);
            finish();
        });
    }


    @Override
    public void onConversationClicked(Usuario user) {
        Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
        intent.putExtra(Constants.KEY_USER_ID,user.getId());
        intent.putExtra(Constants.KEY_USER,"me han tocado jejeje");

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
        startActivity(new Intent(getApplicationContext(), LandingPetOwnerActivity.class));
        finish();
    }
}