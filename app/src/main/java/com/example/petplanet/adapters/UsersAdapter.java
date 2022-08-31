package com.example.petplanet.adapters;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petplanet.R;
import com.example.petplanet.activities.ChatActivity;
import com.example.petplanet.databinding.ItemContainerUserBinding;
import com.example.petplanet.models.Usuario;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> implements View.OnClickListener {

    private List<Usuario> usuarioList;
    private Context context;
    private View.OnClickListener listener;

    public UsersAdapter(List<Usuario> usuarioList, Context context) {
        this.usuarioList = usuarioList;
        this.context = context;
    }

    @NonNull
    @Override
    public UsersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_user, parent ,false);
        v.setOnClickListener(this);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersAdapter.ViewHolder holder, int position) {
        holder.nombre.setText(usuarioList.get(position).getNombre());
        holder.telefono.setText(usuarioList.get(position).getTelefono());
        holder.foto.setImageResource(usuarioList.get(position).getFoto());

    }

    @Override
    public int getItemCount() {
        return usuarioList.size();
    }


    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }
    @Override
    public void onClick(View view) {
        if(listener != null){
            listener.onClick(view);
        }
    }

    public static  class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombre;
        TextView telefono;
        ImageView foto;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);


        nombre = itemView.findViewById(R.id.TextName);
        telefono = itemView.findViewById(R.id.TextPhone);
        foto = itemView.findViewById(R.id.imageprofile);


    }

}
    /*public UsersAdapter(ArrayList<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    @NonNull
    @Override
    public UsersAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContainerUserBinding itemContainerUserBinding = ItemContainerUserBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new UserViewHolder(itemContainerUserBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersAdapter.UserViewHolder holder, int position) {
        holder.setUserData(usuarios.get(position));
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    class  UserViewHolder extends RecyclerView.ViewHolder {
        ItemContainerUserBinding binding;

        UserViewHolder(ItemContainerUserBinding itemContainerUserBinding) {
            super(itemContainerUserBinding.getRoot());
            binding = itemContainerUserBinding;

        }
        void setUserData(Usuario user) {
            binding.TextName.setText(user.getNombre());
            binding.TextPhone.setText(user.getTelefono());
            binding.imageprofile.setImageResource(user.getFoto());
        }


    }*/
}
