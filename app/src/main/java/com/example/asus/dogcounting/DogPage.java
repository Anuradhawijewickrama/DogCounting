package com.example.asus.dogcounting;

import android.*;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;

public class DogPage extends AppCompatActivity implements OnMapReadyCallback{

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int RESULT_LOAD_IMAGE = 2;
    static Bitmap dogImage;
    EditText text_dogType;
    EditText text_dogColor;
    EditText text_description;
    ImageView dogImageView;
    Button button_capture;
    Button button_galary;
    GoogleMap googleMapDog;
    LocationManager locationManager;
    static String userID;
    String encoded_string,image_name;
    String imageUploadMethod;
    double longitude = 0;
    double latitude =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_page);
        if (googleServiceAvailable()) {
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
            initMap();
        }
        System.out.println("userID "+userID);


        text_dogType = (EditText) findViewById(R.id.text_dogType);
        text_dogColor = (EditText) findViewById(R.id.text_dogColor);
        text_description = (EditText) findViewById(R.id.text_description);
        button_capture = (Button) findViewById(R.id.button_capture);
        button_galary = (Button) findViewById(R.id.button_galary);
        dogImageView = (ImageView) findViewById(R.id.dogImageView);
        image_name = "User "+userID+" dog.png";

        if (!hasCamera()) {
            button_capture.setEnabled(false);
        }
    }

    private void initMap() {
        MapFragment new_dog_map_view = (MapFragment) getFragmentManager().findFragmentById(R.id.dogMapFragment);
        new_dog_map_view.getMapAsync(this);
    }

    public boolean hasCamera() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    public void launchCamera(View view) {
        imageUploadMethod = "From camera";
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        if((ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
            System.out.println("here = ");
        }
    }
    public void uploadFromGalary(View view){
        imageUploadMethod = "From galary";
        Intent galaryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galaryIntent,RESULT_LOAD_IMAGE);
    }


        @Override
        protected void onActivityResult ( int requestCode, int resultCode, Intent data){
        System.out.println("here = " + data);
        System.out.println("here = " + resultCode);
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null) {

                Bundle extras = data.getExtras();
                dogImage = (Bitmap) extras.get("data");
                dogImageView.setImageBitmap(dogImage);
            }
            else if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
                Uri selectedImage = data.getData();
                dogImageView.setImageURI(selectedImage);
            }
        }


    public void onSubmit(View view) {
        String typeOfDog = text_dogType.getText().toString();
        String colorOfDog = text_dogColor.getText().toString();
        String descrpitionOfDog = text_description.getText().toString();
        String operation = "newDog";

        //System.out.println("here"+getFileUri().getPath());
        backEndWorker back_end_worker_newDog = new backEndWorker(this);
        back_end_worker_newDog.execute(operation, userID, typeOfDog, colorOfDog, descrpitionOfDog,image_name,Double.toString(longitude),Double.toString(latitude));
    }


    public boolean googleServiceAvailable() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this);
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (api.isUserResolvableError(isAvailable)) {
            Dialog dialog = api.getErrorDialog(this, isAvailable, 0);
            dialog.show();
        } else {
            Toast.makeText(this, "Can't connect to google map", Toast.LENGTH_SHORT).show();
        }
        return false;
    }


    public void onMapReady(GoogleMap googleMap) {

        this.googleMapDog = googleMap;

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if ((ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) &&
                (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 0);

        }

        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if(location!=null){
            longitude = location.getLongitude();
            latitude = location.getLatitude();
        }
        System.out.println("here = "+ longitude + " " + latitude);
        LatLng currentlocation = new LatLng(latitude,longitude);
        CameraUpdate camupdate = CameraUpdateFactory.newLatLngZoom(currentlocation,15);
        googleMapDog.moveCamera(camupdate);
        googleMapDog.addMarker(new MarkerOptions().position(currentlocation));

    }


}
