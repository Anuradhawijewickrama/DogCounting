package com.example.asus.dogcounting;


import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

public class backEndWorker extends AsyncTask<String,String,String>{

    Context context;
    String operation;
    AlertDialog alert_dialog;
    public backEndWorker(Context context){
        this.context = context;
    }
    @Override
    protected String doInBackground(String... params) {

        String result="";

        operation = params[0];
        String signup_url = "http://192.168.43.104/DogCounting/signup.php";
        String login_url = "http://192.168.43.104/DogCounting/login.php";
        if(operation.equals("Login")){
            try {

                String email = params[1];
                String password = params[2];
                URL url = new URL(login_url);
                HttpURLConnection http_URL_connection = (HttpURLConnection) url.openConnection();
                http_URL_connection.setRequestMethod("POST");
                http_URL_connection.setDoInput(true);
                http_URL_connection.setDoOutput(true);

                OutputStream out_put_stream = http_URL_connection.getOutputStream();
                //System.out.println("here = ");
                BufferedWriter buffered_writer = new BufferedWriter(new OutputStreamWriter(out_put_stream, "UTF-8"));
                String login_post_data = URLEncoder.encode("email","UTF-8") + "=" + URLEncoder.encode(email,"UTF-8") + "&"
                        + URLEncoder.encode("password","UTF-8") + "=" + URLEncoder.encode(password,"UTF-8");
                buffered_writer.write(login_post_data);
                buffered_writer.flush();
                buffered_writer.close();
                out_put_stream.close();

                InputStream input_stream = http_URL_connection.getInputStream();
                BufferedReader buffered_reader = new BufferedReader(new InputStreamReader(input_stream, "iso-8859-1"));

                String line = "";
                while((line=buffered_reader.readLine())!=null){
                    result += line;

                }
                buffered_reader.close();
                input_stream.close();
                http_URL_connection.disconnect();

                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if(operation.equals("signup")){

            try {
                String Fname = params[1];
                String Lname = params[2];
                String emailsignup = params[3];
                String password = params[4];
                String telephone = params[6];
                URL url = new URL(signup_url);
                HttpURLConnection http_url_connection = (HttpURLConnection)url.openConnection();
                http_url_connection.setRequestMethod("POST");
                http_url_connection.setDoInput(true);
                http_url_connection.setDoOutput(true);
                OutputStream out_put_stream = http_url_connection.getOutputStream();

                BufferedWriter buffered_writer = new BufferedWriter(new OutputStreamWriter(out_put_stream, "UTF-8"));
                String login_post_data = URLEncoder.encode("Fname","UTF-8") + "=" + URLEncoder.encode(Fname,"UTF-8") + "&"
                                       + URLEncoder.encode("Lname","UTF-8") + "=" + URLEncoder.encode(Lname,"UTF-8") +"&"
                                       + URLEncoder.encode("emailsignup","UTF-8") + "=" + URLEncoder.encode(emailsignup,"UTF-8") +"&"
                        + URLEncoder.encode("telephone","UTF-8") + "=" + URLEncoder.encode(telephone,"UTF-8") +"&"
                        + URLEncoder.encode("password","UTF-8") + "=" + URLEncoder.encode(password,"UTF-8") ;

                buffered_writer.write(login_post_data);
                buffered_writer.flush();
                buffered_writer.close();
                out_put_stream.close();
                System.out.println("here = "+login_post_data);
                InputStream input_stream = http_url_connection.getInputStream();
                BufferedReader buffered_reader = new BufferedReader(new InputStreamReader(input_stream, "iso-8859-1"));

                String line = "";
                while((line=buffered_reader.readLine())!=null){
                    result += line;

                }
                buffered_reader.close();
                input_stream.close();
                http_url_connection.disconnect();

                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    @Override
    protected void onPreExecute() {
        alert_dialog = new AlertDialog.Builder(context).create();

        alert_dialog.setTitle("Login Status");


    }

    @Override
    protected void onPostExecute(String result) {
        alert_dialog.setMessage(result);
        alert_dialog.show();
    }


}
