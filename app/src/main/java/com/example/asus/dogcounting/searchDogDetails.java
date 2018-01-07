package com.example.asus.dogcounting;


public class searchDogDetails {
    String dogType;
    String dogColor;
    String dogDesc;

    public searchDogDetails(String dogtype, String dogcolor, String dogdesc){
        this.dogType = dogtype;
        this.dogColor = dogcolor;
        this.dogDesc = dogdesc;
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
}
