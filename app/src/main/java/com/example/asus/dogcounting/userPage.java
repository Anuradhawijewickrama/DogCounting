package com.example.asus.dogcounting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class userPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);
    }
    public void enterNewDog(View view){
        Intent intent = new Intent("com.example.asus.dogcounting.DogPage");
        startActivity(intent);
    }
}
