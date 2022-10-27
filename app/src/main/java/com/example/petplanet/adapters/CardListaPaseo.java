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
import com.example.petplanet.models.Usuario;

import java.util.ArrayList;

public class CardListaPaseo extends ArrayAdapter {

    private ArrayList<Paseo> paseolist;


    public CardListaPaseo(Context context, int textViewResourceId, ArrayList objects) {
        super(context, textViewResourceId, objects);
        paseolist = objects;
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
        TextView nombredelperro = (TextView) v.findViewById(R.id.nombredelperrorecorrido);
        TextView diadelpaseo = (TextView) v.findViewById(R.id.diadelpaseo);
        TextView recordido = (TextView) v.findViewById(R.id.distanciarecorrida);
        TextView durecion = (TextView) v.findViewById(R.id.tiemporecorrido);
        ImageView imageView = (ImageView) v.findViewById(R.id.fotoperrocard);
        nombredelperro.setText("Nombre dle perro: " + paseolist.get(position).getNombredelperro());
        diadelpaseo.setText("El dia del paseo fue: " + paseolist.get(position).getFecha());
        recordido.setText("La distancia recorrida fue: " + paseolist.get(position).getDistanciarecorrida());
        durecion.setText("La duracion del paseo fue: " + paseolist.get(position).getDuracion());
        byte[] decodedString = Base64.decode(paseolist.get(position).getFotodelperro(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imageView.setImageBitmap(decodedByte);
        return v;
    }
}
