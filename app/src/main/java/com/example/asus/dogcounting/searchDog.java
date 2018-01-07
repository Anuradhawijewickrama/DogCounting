package com.example.asus.dogcounting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class searchDog extends AppCompatActivity {
    static ArrayList<searchDogDetails> myDogsArray;
    static ArrayList<searchDogDetails> allDogsArray;
    static String userId;
    String operation = "searchDog";
    static String method;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_dog);

       /* String[] foods = {"meatball","potato", "chocalate", "Milk", "pork", "chicken" };
        ListAdapter adapter = new customAdapter(this, myDogsArray);
        ListView dogListView = (ListView) findViewById(R.id.searchDog_list);
        dogListView.setAdapter(adapter);*/
        method = "All Dogs";
        allDogsArray = new ArrayList<searchDogDetails>();
        System.out.println("here");
        backEndWorker search_my_dogs = new backEndWorker(this);
        search_my_dogs.execute(operation, "All Dogs",userId);

        ListAdapter adapter = new customAdapter(this, allDogsArray);
        ListView dogListView = (ListView) findViewById(R.id.searchDog_list);
        dogListView.setAdapter(adapter);
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

