package com.example.asus.dogcounting;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

public class DogPage extends AppCompatActivity implements OnMapReadyCallback,GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    GestureDetectorCompat gestureDetect;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    EditText text_dogType;
    EditText text_dogColor;
    EditText text_description;
    ImageView dogImageView;
    Button button_capture;
    Button button_galary;
    GoogleMap googleMapDog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_page);
        gestureDetect = new GestureDetectorCompat(this,this);
        gestureDetect.setOnDoubleTapListener(this);
        if(googleServiceAvailable()){
            System.out.println("here");
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
            initMap();
        }


        text_dogType = (EditText) findViewById(R.id.text_dogType);
        text_dogColor = (EditText) findViewById(R.id.text_dogColor);
        text_description = (EditText) findViewById(R.id.text_description);
        button_capture = (Button) findViewById(R.id.button_capture);
        button_galary = (Button) findViewById(R.id.button_galary);
        dogImageView = (ImageView)findViewById(R.id.dogImageView);

        if(!hasCamera()){
            button_capture.setEnabled(false);
        }
    }

    private void initMap() {
        MapFragment new_dog_map_view = (MapFragment) getFragmentManager().findFragmentById(R.id.dogMapFragment);
        new_dog_map_view.getMapAsync(this);
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
    public void onSubmit(View view){
        String typeOfDog = text_dogType.getText().toString();
        String colorOfDog = text_dogColor.getText().toString();
        String descrpitionOfDog = text_description.getText().toString();
        String operation = "newDog";

        backEndWorker back_end_worker_newDog = new backEndWorker(this);
        back_end_worker_newDog.execute(operation,typeOfDog,colorOfDog,descrpitionOfDog);
    }
    public boolean googleServiceAvailable(){
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this);
        if(isAvailable == ConnectionResult.SUCCESS){
            return true;
        }

        else if(api.isUserResolvableError(isAvailable)){
            Dialog dialog = api.getErrorDialog(this,isAvailable,0);
            dialog.show();
        }
        else {
            Toast.makeText(this, "Can't connect to google map", Toast.LENGTH_SHORT).show();
            //return false;
        }
        return false;
    }

    public void onMapReady(GoogleMap googleMap){
        this.googleMapDog = googleMap;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetect.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {

        return true;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }
}
