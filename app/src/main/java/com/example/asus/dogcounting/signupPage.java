package com.example.asus.dogcounting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class signupPage extends AppCompatActivity {
    EditText Fname_signup,Lname_signup,email_signup,password_signup,Cpassword_signup,telepone_signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        Fname_signup = findViewById(R.id.Fname_signup);
        Lname_signup = findViewById(R.id.Lname_signup);
        email_signup = findViewById(R.id.email_signup);
        password_signup = findViewById(R.id.password_signup);
        Cpassword_signup = findViewById(R.id.Cpassword_signup);
        telepone_signup  = findViewById(R.id.tp_signup);
    }

    public void onSignUp(View view){

        String Fname = Fname_signup.getText().toString();
        String Lname = Lname_signup.getText().toString();
        String email = email_signup.getText().toString();
        String password = password_signup.getText().toString();
        String Cpassword = Cpassword_signup.getText().toString();
        String telephonep_signup = telepone_signup.getText().toString();
        String operation = "signup";

        backEndWorker back_end_worker_signup_page = new backEndWorker(this);
        back_end_worker_signup_page.execute(operation,Fname,Lname,email,password,Cpassword,telephonep_signup);
    }
}
