package com.example.asus.dogcounting;

import android.*;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class searchDog extends AppCompatActivity {

    static ArrayList<searchDogDetails> NearByDogsArray;
    static ArrayList<searchDogDetails> allDogsArray;
    static String userId;
    String operation = "searchDog";
    static String method;
    LocationManager locationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_dog);
        ListView dogListView = (ListView) findViewById(R.id.searchDog_list);

       if(userPage.searchType.equals("allDogs")){
           method = "All Dogs";
           allDogsArray = new ArrayList<searchDogDetails>();
           System.out.println("here");
           backEndWorker search_my_dogs = new backEndWorker(this);
           search_my_dogs.execute(operation, "All Dogs",userId);
           try {
               Thread.sleep(1000);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           ListAdapter adapter = new customAdapter(this, allDogsArray);


           dogListView.setAdapter(adapter);


        }
        else if(userPage.searchType.equals("NearbyDogs")){

           double longitude = 0;
           double latitude =0;

           method = "Nearby Dogs";
           NearByDogsArray = new ArrayList<searchDogDetails>();

           locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

           if ((ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) &&
                   (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
               ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 0);
               ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 0);

           }

           Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
           if(location!=null){
               longitude = location.getLongitude();
               latitude = location.getLatitude();
           }

           backEndWorker search_my_dogs = new backEndWorker(this);
           search_my_dogs.execute(operation, "Nearby Dogs",Double.toString(longitude),Double.toString(latitude));
           try {
               Thread.sleep(1000);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           ListAdapter adapter = new customAdapter(this, NearByDogsArray);


           dogListView.setAdapter(adapter);
       }

    }

   /* public void onMyDogs(View view){
        method ="My Dogs";
        myDogsArray = new ArrayList<searchDogDetails>();

        backEndWorker2 search_my_dogs = new backEndWorker2(this);
        search_my_dogs.execute(operation, "My Dogs",userId);

        ListAdapter adapter = new customAdapter(this, myDogsArray);
        ListView dogListView = (ListView) findViewById(R.id.searchDog_list);
        dogListView.setAdapter(adapter);
    }

    public void onAllDogs(View view){
        method = "All Dogs";
        allDogsArray = new ArrayList<searchDogDetails>();
        System.out.println("here");
        backEndWorker search_my_dogs = new backEndWorker(this);
        search_my_dogs.execute(operation, "All Dogs",userId);
        //System.out.println("here "+allDogsArray.get(1).getDogType());
        ListAdapter adapter = new customAdapter(this, allDogsArray);
        ListView dogListView = (ListView) findViewById(R.id.searchDog_list);
        dogListView.setAdapter(adapter);
    }*/
}

