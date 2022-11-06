package com.example.petplanet.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petplanet.databinding.ItemContainerRecentConversationsBinding;
import com.example.petplanet.listeners.ConversationListener;
import com.example.petplanet.models.ChatMessage;
import com.example.petplanet.models.Usuario;

import java.util.List;

public class RecentConversationsAdapter extends RecyclerView.Adapter<RecentConversationsAdapter.ConversationViewHolder> {

    private final List<ChatMessage> chatMessageList;
    private final ConversationListener conversationListener;

    public RecentConversationsAdapter(List<ChatMessage> chatMessageList, ConversationListener conversationListener) {
        this.conversationListener = conversationListener;
        this.chatMessageList = chatMessageList;
    }

    @NonNull
    @Override
    public ConversationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ConversationViewHolder(
                ItemContainerRecentConversationsBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ConversationViewHolder holder, int position) {
        holder.setData(chatMessageList.get(position));
    }

    @Override
    public int getItemCount() {
        return chatMessageList.size();
    }

    class ConversationViewHolder extends RecyclerView.ViewHolder {

        ItemContainerRecentConversationsBinding binding;

        ConversationViewHolder(ItemContainerRecentConversationsBinding itemContainerRecentConversationsBinding) {
            super(itemContainerRecentConversationsBinding.getRoot());
            binding = itemContainerRecentConversationsBinding;
        }

        void setData(ChatMessage chatmessage) {
            binding.imageprofile.setImageBitmap(getConversationImage(chatmessage.conversionImage));
            binding.TextName.setText(chatmessage.conversionName);
            binding.textRecentMessage.setText(chatmessage.getMessage());
            binding.getRoot().setOnClickListener(v -> {
                Usuario usuario = new Usuario();
                usuario.setId(chatmessage.conversionId);
                usuario.setNombre(chatmessage.conversionName);
                usuario.setFoto(chatmessage.conversionImage);
                conversationListener.onConversationClicked(usuario);
            });
        }
    }


    private Bitmap getConversationImage(String encodedimg) {
        byte[] bytes = Base64.decode(encodedimg, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

}
