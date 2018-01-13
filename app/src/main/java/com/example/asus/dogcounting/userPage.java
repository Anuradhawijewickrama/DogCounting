package com.example.asus.dogcounting;

import android.content.Intent;
import android.content.SyncStatusObserver;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class userPage extends AppCompatActivity {

    static String userID;
    static String searchType;
    //public userPage(String userID){
        //this.userID = userID;
   // }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);
        DogPage.userID = userID;
        searchDog.userId = userID;
        //System.out.println("userID "+userID);
    }

    public void enterNewDog(View view){
        Intent intent = new Intent("com.example.asus.dogcounting.DogPage");
        startActivity(intent);
    }
    public void searchdog(View view){

        AlertDialog.Builder searchDogBuilder = new AlertDialog.Builder(this);
        View searchDogVeiw = getLayoutInflater().inflate(R.layout.alertdialog_searchdog, null);
        Button searchAllDogs = (Button) searchDogVeiw.findViewById(R.id.button_allDogs);
        Button searchNearByDogs = (Button) searchDogVeiw.findViewById(R.id.button_nearByDogs);

        searchAllDogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchType = "allDogs";
                Intent intent = new Intent("com.example.asus.dogcounting.searchDog");
                startActivity(intent);
            }
        });
        searchNearByDogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //searchDog searchdog = new searchDog("nearByDogs");
                searchType = "NearbyDogs";
                Intent intent = new Intent("com.example.asus.dogcounting.searchDog");
                startActivity(intent);
            }
        });
        searchDogBuilder.setView(searchDogVeiw);
        AlertDialog aDialog = searchDogBuilder.create();
        aDialog.show();

    }
}
