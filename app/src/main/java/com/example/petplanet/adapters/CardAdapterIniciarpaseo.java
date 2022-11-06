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
import com.example.petplanet.models.Paseo;
import com.example.petplanet.models.Perro;
import com.example.petplanet.models.Usuario;

import java.util.ArrayList;

public class CardAdapterIniciarpaseo extends ArrayAdapter {


    private ArrayList<Paseo> perroslist = new ArrayList<>();

    public CardAdapterIniciarpaseo(Context context, int textViewResourceId, ArrayList objects) {
        super(context, textViewResourceId, objects);
        perroslist = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.cardviewiniciarpaseo, null);
        TextView nombreperro = (TextView) v.findViewById(R.id.nombreperrotxt);
        TextView nombredueno = (TextView) v.findViewById(R.id.duenoperrotxt);
        ImageView imageView = (ImageView) v.findViewById(R.id.fotoperrocard);
        nombreperro.setText("Nombre del perro: " + perroslist.get(position).getNombredelperro());
        nombredueno.setText("Nombre del dueño: " + perroslist.get(position).getDirecciondelowner());
        byte[] decodedString = Base64.decode(perroslist.get(position).getFotodelperro(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imageView.setImageBitmap(decodedByte);
        return v;
    }
}
