package com.example.petplanet.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petplanet.databinding.ItemContainerRecentConversationsBinding;
import com.example.petplanet.models.ChatMessage;

import java.util.List;

public class RecentConversationsAdapter extends RecyclerView.Adapter<RecentConversationsAdapter.ConversationViewHolder> {

    private final List<ChatMessage> chatMessageList;

    public RecentConversationsAdapter(List<ChatMessage> chatMessageList) {
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

        void setData(ChatMessage chatmessage){
            binding.imageprofile.setImageBitmap(getConversationImage(chatmessage.conversionImage));
            binding.TextName.setText(chatmessage.conversionName);
            binding.textRecentMessage.setText(chatmessage.getMessage());
        }
    }


    private Bitmap getConversationImage(String encodedimg) {
        byte[] bytes = Base64.decode(encodedimg, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

}
