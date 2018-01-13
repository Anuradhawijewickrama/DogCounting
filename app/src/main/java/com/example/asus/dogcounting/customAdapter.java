package com.example.asus.dogcounting;


import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class customAdapter extends ArrayAdapter<searchDogDetails>{


    public customAdapter(Context context, ArrayList<searchDogDetails> data) {
        super(context,R.layout.coustem_list_item ,data);
    }


    @Override
    public View getView(int position,View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.coustem_list_item,parent,false);

        String singleDogType =null;
        String singleDogColor =null;
        String singleDogDesc =null;
        Bitmap singleDogImg = null;

        if (searchDog.method.equals("Nearby Dogs")) {
             singleDogType = searchDog.NearByDogsArray.get(position).getDogType();
             singleDogColor = searchDog.NearByDogsArray.get(position).getDogColor();
             singleDogDesc = searchDog.NearByDogsArray.get(position).getDogDesc();
             singleDogImg = searchDog.NearByDogsArray.get(position).getImage();
        }
        else if (searchDog.method.equals("All Dogs")) {
             singleDogType = searchDog.allDogsArray.get(position).getDogType();
             singleDogColor = searchDog.allDogsArray.get(position).getDogColor();
             singleDogImg = searchDog.allDogsArray.get(position).getImage();
             singleDogDesc = searchDog.allDogsArray.get(position).getDogDesc();


        }

        TextView dogType = (TextView)customView.findViewById(R.id.dogType);
        TextView dogColor = (TextView)customView.findViewById(R.id.dogColor);
        TextView dogDesc = (TextView)customView.findViewById(R.id.dogDesc);
        ImageView dogImg = (ImageView)customView.findViewById(R.id.dogImage);

        dogType.setText(singleDogType);
        dogColor.setText(singleDogColor);
        dogDesc.setText(singleDogDesc);
        dogImg.setImageBitmap(singleDogImg);

        return customView;
    }
}


