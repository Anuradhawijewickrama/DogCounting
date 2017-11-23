package com.example.asus.dogcounting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class loginPage extends AppCompatActivity {
    EditText email_login;
    EditText password_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);

        email_login =(EditText) findViewById(R.id.email_login);
        password_login =(EditText) findViewById(R.id.password_login);
    }

    public void onLogin(View view){

        String email = email_login.getText().toString();
        String password = password_login.getText().toString();
        String operation = "Login";
       //
        backEndWorker back_end_worker_login_page = new backEndWorker(this);
        //System.out.println("object = "+back_end_worker_login_page);
        back_end_worker_login_page.execute(operation,email,password);
    }
}
