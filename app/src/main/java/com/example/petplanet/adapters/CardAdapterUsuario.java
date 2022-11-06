package com.example.petplanet.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.petplanet.R;
import com.example.petplanet.models.Usuario;

import java.util.ArrayList;

public class CardAdapterUsuario extends ArrayAdapter {


    private ArrayList<Usuario> usuariolist = new ArrayList<>();

    public CardAdapterUsuario(Context context, int textViewResourceId, ArrayList objects) {
        super(context, textViewResourceId, objects);
        usuariolist = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.cardview, null);
        TextView nombre = (TextView) v.findViewById(R.id.nombreperrotx);
        TextView conoce = (TextView) v.findViewById(R.id.conoceperrotxt);
        ImageView imageView = (ImageView) v.findViewById(R.id.fotoperrocard);
        nombre.setText(usuariolist.get(position).getNombre());
        conoce.setText("Conoce mas de: " + usuariolist.get(position).getNombre());
        byte[] decodedString = Base64.decode(usuariolist.get(position).getFoto(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imageView.setImageBitmap(decodedByte);
        return v;
    }

}
