package com.example.asus.dogcounting;


import android.graphics.Bitmap;

public class searchDogDetails {
    String dogType;
    String dogColor;
    String dogDesc;
    Bitmap dogImg;

    public searchDogDetails(String dogtype, String dogcolor, String dogdesc,Bitmap dogImg){
        this.dogType = dogtype;
        this.dogColor = dogcolor;
        this.dogDesc = dogdesc;
        this.dogImg = dogImg;
    }

    public String getDogType(){
        return dogType;
    }

    public String getDogColor() {
        return dogColor;
    }

    public String getDogDesc() {
        return dogDesc;
    }

    public Bitmap getImage(){
        return dogImg;
    }
}
