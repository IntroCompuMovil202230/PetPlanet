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

import com.example.petplanet.models.Perro;
import com.example.petplanet.R;

import java.util.ArrayList;

public class CardAdapterPerro extends ArrayAdapter {

    ArrayList<Perro> perrolist = new ArrayList<>();


    public CardAdapterPerro(Context context, int textViewResourceId, ArrayList objects) {
        super(context, textViewResourceId, objects);
        perrolist = objects;
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
        TextView horario = (TextView) v.findViewById(R.id.horariotxt);
        TextView conoce = (TextView) v.findViewById(R.id.conoceperrotxt);
        ImageView imageView = (ImageView) v.findViewById(R.id.fotoperrocard);
        nombre.setText(perrolist.get(position).getNombrecompleto());
        horario.setText(perrolist.get(position).getHorario());
        conoce.setText("Conoce mas de: " + perrolist.get(position).getNombrecompleto());
        byte[] decodedString = Base64.decode(perrolist.get(position).getFoto(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imageView.setImageBitmap(decodedByte);
        return v;
    }

}