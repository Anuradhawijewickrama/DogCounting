package com.example.asus.dogcounting;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class Home extends AppCompatActivity {

    Button signUpButton;
    Button loginbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        onClickListner();

    }

    public void onClickListner(){
        signUpButton =(Button) findViewById(R.id.signUpButton);
        loginbutton = (Button) findViewById(R.id.loginButton);
        signUpButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.example.asus.dogcounting.signupPage");
                startActivity(intent);
                //signUp();
                //  changeFragment(view);
            }
        });

        loginbutton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.example.asus.dogcounting.loginPage");
                startActivity(intent);
                //login();
                // changeFragment(view);
            }
        });

    }


       // public void signUp(){


        //}

        //public void login(){
         //  Intent intent = new Intent("com.example.asus.dogcounting.loginPage");
          //  startActivity(intent);

        //}
  /*  public void changeFragment(View view){
        Fragment fragment;

        if(view == findViewById(R.id.signUpButton)){
            fragment= new sign();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragment_place, fragment);
            ft.commit();

        }
        if(view == findViewById(R.id.loginButton)){
            fragment= new login();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragment_place, fragment);
            ft.commit();
        }
    }*/
}
