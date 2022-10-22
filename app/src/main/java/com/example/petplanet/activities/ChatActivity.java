package com.example.petplanet.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petplanet.R;
import com.example.petplanet.adapters.ChatAdapter;
import com.example.petplanet.databinding.ActivityCambiarPasswordBinding;
import com.example.petplanet.databinding.ActivityChatBinding;
import com.example.petplanet.models.ChatMessage;
import com.example.petplanet.models.Usuario;
import com.example.petplanet.utilities.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class ChatActivity extends AppCompatActivity {

    private ActivityChatBinding binding;
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    DatabaseReference myChat;
    public static final String PATH_USERS = "users/";
    public List<ChatMessage> chatMessages;
    private ChatAdapter chatAdapter;
    ChatMessage chatM = new ChatMessage();
    public String uid2;

    public String getUid2() {
        return uid2;
    }

    public void setUid2(String uid2) {
        this.uid2 = uid2;
    }

    public String foto;

    public String getFotoUid2() {
        return foto;
    }

    public void setFotoUid2(String foto) {
        this.foto = foto;
    }

    byte[] decodedString;
    boolean listo = false;
    Bitmap decodedByte;
    Usuario walkerx = new Usuario();
    Usuario usuariochat = new Usuario();


    private String conversionId = null;

    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        chatMessages = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        Log.d("TAG", "onCreate: " + mAuth.getCurrentUser().getUid());
        String correo;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                correo = null;

            } else {
                correo = extras.getString(Constants.KEY_USER);
                setUid2(extras.getString(Constants.KEY_USER_ID));
            }
        } else {
            correo = (String) savedInstanceState.getSerializable(Constants.KEY_USER);
            setUid2((String) savedInstanceState.getSerializable(Constants.KEY_USER_ID));
        }
        Log.d("CrHATerrrr112", "asdasd2: " + correo);
        llenarUsuariochat();
        llenarWalkerx();
        setListeners();

        Log.d("CrHATerrrr112", "asdasd2: " + getUid2());

        myChat = database.getReference(Constants.PATH_CHATS);
        myChat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                llenarUsuariochat();
                llenarWalkerx();
                chatMessages.clear();
                count = chatMessages.size();
                for (DataSnapshot chatt : snapshot.getChildren()) {
                    ChatMessage chat = chatt.getValue(ChatMessage.class);
                    chatM.setMessage(chat.getMessage());
                    chatM.setSenderid(chat.getSenderid());
                    chatM.setReceiverid(chat.getReceiverid());
                    chatM.setDatetime(chat.getDatetime());
                    Log.d("CrHATerrrr222", "onCreate244444444444444444: " + getUid2());
                    Log.d("CrHATerrrr27722", "asdasd39: " + chatM.getMessage());
                    if (chatM.getSenderid().equals(mAuth.getCurrentUser().getUid()) && chatM.getReceiverid().equals(getUid2())) {

                        chatMessages.add(chatM);
                    }
                    Log.d("CrHATerrrr66", "222uto: " + mAuth.getCurrentUser().getUid());
                    Log.d("CrHATerrrr66", "222uto: " + getUid2());
                    if (chatM.getReceiverid().equals(mAuth.getCurrentUser().getUid()) && chatM.getSenderid().equals(getUid2())) {
                        Log.d("CrHATerrrr66", "222uto: " + chatM.getMessage());
                        Log.d("CrHATerrrrss", "contadorrrr: " + count);
                        chatMessages.add(chatM);
                    }
                    chatM = new ChatMessage();
                    Log.d("CrHATerrrr", "onCreate: " + chatMessages.size());
                }
                Log.d("CrHATerrrrss", "mnmhhhhhhhhhhhh: " + chatMessages.size());
                chatMessages.sort(Comparator.comparing(ChatMessage::getDatetime));
                if (chatMessages.isEmpty()) {
                    //chatAdapter.notifyDataSetChanged();
                } else {
                    if (chatAdapter == null) {
                        llenarUsuariochat();
                        llenarWalkerx();
                        binding.chatRecyclerView.setVisibility(View.VISIBLE);
                        binding.progressBar.setVisibility(View.GONE);
                    } else {
                        chatAdapter.notifyItemRangeInserted(chatMessages.size(), chatMessages.size());
                        binding.chatRecyclerView.smoothScrollToPosition(chatMessages.size() - 1);
                        binding.chatRecyclerView.setVisibility(View.VISIBLE);
                        binding.progressBar.setVisibility(View.GONE);
                    }
                }
                if (conversionId == null) {
                    checkForConversion();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ChatActivity.this, "Error al cargar los chats", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void llenarWalkerx() {
        myRef = database.getReference(PATH_USERS + getUid2());
        myRef.getDatabase().getReference(PATH_USERS + getUid2()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                walkerx = task.getResult().getValue(Usuario.class);
                byte[] decodedString = Base64.decode(walkerx.getFoto(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                binding.TextName.setText(walkerx.getNombre());
                binding.imagePerson.setImageBitmap(decodedByte);
                chatAdapter = new ChatAdapter(decodeFromFirebaseBase64(walkerx.getFoto()), chatMessages, mAuth.getCurrentUser().getUid());
                setFotoUid2(walkerx.getFoto());
                binding.chatRecyclerView.setAdapter(chatAdapter);
            }
        });
    }


    private void llenarUsuariochat() {
        myRef = database.getReference(PATH_USERS + mAuth.getCurrentUser().getUid());
        myRef.getDatabase().getReference(PATH_USERS + mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                usuariochat = task.getResult().getValue(Usuario.class);
            }
        });
    }

    private void sendMessage() {
        HashMap<String, Object> message = new HashMap<>();
        message.put(Constants.KEY_SENDER_ID, mAuth.getCurrentUser().getUid());
        message.put(Constants.KEY_RECEIVER_ID, getUid2());
        message.put(Constants.KEY_MESSAGE, binding.inputmessage.getText().toString());
        message.put(Constants.KEY_TIMESTAMP, java.text.DateFormat.getDateTimeInstance().format(new Date()));
        myRef = database.getReference(Constants.PATH_CHATS);
        myRef.push().setValue(message);
        Log.d("CrHATerrrr", "onCreatetttt: " + conversionId);
        if (conversionId != null) {
            updateConversion(binding.inputmessage.getText().toString());
        } else {
            HashMap<String, Object> conversion = new HashMap<>();
            conversion.put(Constants.KEY_SENDER_ID, mAuth.getCurrentUser().getUid());
            conversion.put(Constants.KEY_SENDER_NAME, usuariochat.getNombre());
            conversion.put(Constants.KEY_SENDER_IMAGE, usuariochat.getFoto());
            conversion.put(Constants.KEY_RECEIVER_ID, getUid2());
            conversion.put(Constants.KEY_RECEIVER_NAME, walkerx.getNombre());
            conversion.put(Constants.KEY_RECEIVER_IMAGE, walkerx.getFoto());
            conversion.put(Constants.KEY_LAST_MESSAGE, binding.inputmessage.getText().toString());
            conversion.put(Constants.KEY_TIMESTAMP, java.text.DateFormat.getDateTimeInstance().format(new Date()));
            addConversion(conversion);
        }
        binding.inputmessage.setText("");
    }


    private Bitmap decodeFromFirebaseBase64(String image) {
        byte[] decodedByteArray = Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }


    private void setListeners() {
        binding.imageback.setOnClickListener(v -> {
            Intent intent = new Intent(ChatActivity.this, ListaDeChatsActivity.class);
            startActivity(intent);
            finish();
        });

        binding.layoutSend.setOnClickListener(v -> {
            sendMessage();
        });
    }

    private void checkForConversion() {
        if (chatMessages.size() != 0) {
            checkForConversionRemotely(
                    mAuth.getCurrentUser().getUid(), getUid2()
            );
            checkForConversionRemotely(
                    getUid2(), mAuth.getCurrentUser().getUid()
            );
        }
    }

    private void addConversion(HashMap<String, Object> conversion) {
        myRef = database.getReference(Constants.KEY_COLLECTION_CONVERSATIONS);
        myRef.push().setValue(conversion);
    }

    private void updateConversion(String message) {
        myRef = database.getReference(Constants.KEY_COLLECTION_CONVERSATIONS);
        myRef.child(conversionId).child(Constants.KEY_LAST_MESSAGE).setValue(message);
        myRef.child(conversionId).child(Constants.KEY_TIMESTAMP).setValue(java.text.DateFormat.getDateTimeInstance().format(new Date()));
    }


    private void checkForConversionRemotely(String senderId, String receiverId) {
        myRef = database.getReference(Constants.KEY_COLLECTION_CONVERSATIONS);
        myRef.getDatabase().getReference(Constants.KEY_COLLECTION_CONVERSATIONS).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DataSnapshot conversation : task.getResult().getChildren()) {
                    if (conversation.child(Constants.KEY_SENDER_ID).getValue().equals(senderId) && conversation.child(Constants.KEY_RECEIVER_ID).getValue().equals(receiverId)) {
                        conversionId = conversation.getKey();
                        break;
                    }
                }
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
        startActivity(new Intent(getApplicationContext(), ListaDeChatsActivity.class));
        finish();
    }
}