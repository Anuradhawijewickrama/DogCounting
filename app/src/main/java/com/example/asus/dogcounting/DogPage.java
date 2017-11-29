package com.example.asus.dogcounting;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class DogPage extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView dogImageView;
    Button button_capture;
    Button button_galary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_page);

        button_capture = (Button) findViewById(R.id.button_capture);
        button_galary = (Button) findViewById(R.id.button_galary);
        dogImageView = (ImageView) findViewById(R.id.dogImageView);

        if(!hasCamera()){
            button_capture.setEnabled(false);
        }
    }
    public boolean hasCamera(){
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }
    public void launchCamera(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,REQUEST_IMAGE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap dogImage = (Bitmap) extras.get("data");
            dogImageView.setImageBitmap(dogImage);
        }
    }
}
