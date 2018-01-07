package com.example.asus.dogcounting;

import android.content.Intent;
import android.content.SyncStatusObserver;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class userPage extends AppCompatActivity {

    static String userID;
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

        Intent intent = new Intent("com.example.asus.dogcounting.searchDog");
        startActivity(intent);
    }
}
