package com.example.asus.dogcounting;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class backEndWorker extends AsyncTask<String,String,String>{

    String userID;
    Context context;
    String operation;
    AlertDialog.Builder alert_dialog;
    public backEndWorker(Context context){
        this.context = context;
    }
    //private String TAG
    @Override
    protected String doInBackground(String... params) {

        String result = "";
        operation = params[0];
        String hostserver = "192.168.43.104:3306";
        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection myConnection = DriverManager.getConnection("jdbc:mysql://" + hostserver + "/dogcounting", "dogCountingApp", "");
            Statement myStatement = myConnection.createStatement();

            if (operation.equals("Login")) {

                String email = params[1];
                String password = params[2];
                System.out.println("email  " + email + "pass" + password);
                ResultSet myLoginResult = myStatement.executeQuery("select * from users");
                while (myLoginResult.next()) {
                    System.out.println("email  " + myLoginResult.getString("password"));
                    if ((myLoginResult.getString("email").equals(email)) && (myLoginResult.getString("password").equals(password))) {
                        userID = myLoginResult.getString("userID");
                        //System.out.println("here "+userID);
                        userPage.userID=userID;
                        result = "Login Succesfull";
                        return result;
                    } else {
                        result = "not successfull";
                    }
                }
            } else if (operation.equals("signup")) {
                String Fname = params[1];
                String Lname = params[2];
                String emailsignup = params[3];
                String password = params[4];
                String telephone = params[6];

                String sqlqury = "insert into users(first_name, last_name, email, tp_no, password) " +
                        "values (" + "\"" + Fname + "\"" + "," + "\"" + Lname + "\"" + "," + "\"" + emailsignup + "\"" + "," + "\"" + telephone + "\"" + "," + "\"" + password + "\"" + ")";
                System.out.println("email  " + sqlqury);
                myStatement.executeUpdate(sqlqury);
                result = "registration successfull";
                ResultSet myLoginResult = myStatement.executeQuery("select userID from users where email="+"\""+emailsignup+"\""+" and password="+"\""+password+"\"");
                System.out.println(myLoginResult);
                userID = myLoginResult.getString("userID");
                userPage.userID = userID;

            }
            else if(operation.equals("newDog")){

                String user_id = params[1];
                String typeOfDog = params[2];
                String colorOfDog = params[3];
                String DescOfDog = params[4];
                String image_name = params[5];
                String imgPath = "images/"+image_name;
                Bitmap bitmap = DogPage.dogImage;
                String encoded_string;

                System.out.println("here "+bitmap);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

                byte[] array = stream.toByteArray();
                encoded_string =android.util.Base64.encodeToString(array, android.util.Base64.NO_WRAP);

                String phpUrl = "http://192.168.43.104:80/dogCountingApp/newDog.php";
                URL url = new URL(phpUrl);
                HttpURLConnection httpurlconnection = (HttpURLConnection)url.openConnection();
                httpurlconnection.setRequestMethod("POST");
                httpurlconnection.setDoOutput(true);
                OutputStream outputstream = httpurlconnection.getOutputStream();

                BufferedWriter bufferedwriter = new BufferedWriter(new OutputStreamWriter(outputstream,"UTF-8"));

                String data = URLEncoder.encode("encoded_string","UTF-8") + "=" + URLEncoder.encode(encoded_string,"UTF-8")+"&"+
                        URLEncoder.encode("image_name","UTF-8")+"="+URLEncoder.encode(image_name,"UTF-8");
                bufferedwriter.write(data);
                bufferedwriter.flush();
                bufferedwriter.close();
                outputstream.close();

                InputStream inputStream = httpurlconnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
               // String line;

               // while ((line = reader.readLine()) != null) {
                  // result = result +line;
                //}
                reader.close();
                inputStream.close();
                String sqlqury = "insert into dogs(dogType, dogColor, dogDescription, img_path, user_id) " +
                        "values (" + "\"" + typeOfDog + "\"" + "," + "\"" + colorOfDog + "\"" + "," + "\"" + DescOfDog + "\"" + "," + "\"" + imgPath + "\"" + "," + "\"" +user_id + "\"" + ")";
                myStatement.executeUpdate(sqlqury);
                result = "New dog entered sucsessfully";
            }
            else if(operation.equals("searchDog")){
                String method = params[1];
                String userid = params[2];
                searchDogDetails dogDetails;
                if(method.equals("My Dogs")){
                    ResultSet rs = myStatement.executeQuery("select * from dogs where user_id =" + "\""+userid+"\"");

                    while (rs.next()) {
                        dogDetails = new searchDogDetails(rs.getString("dogType"),rs.getString("dogColor"),rs.getString("dogDescription"));
                        searchDog.myDogsArray.add(dogDetails);

                    }
                }
                else if(method.equals("All Dogs")){
                    ResultSet rs = myStatement.executeQuery("select * from Dogs");

                    while (rs.next()) {
                        dogDetails = new searchDogDetails(rs.getString("dogType"),rs.getString("dogColor"),rs.getString("dogDescription"));
                        searchDog.allDogsArray.add(dogDetails);

                    }
                    result = "All dogs search succesfully";
                    return result;
                }


            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("here" + e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    @Override
    protected void onPreExecute() {
        alert_dialog = new AlertDialog.Builder(context);

    }

    @Override
    protected void onPostExecute(final String result) {
        alert_dialog.setMessage(result).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                if(operation.equals("signup")||operation.equals("Login")){

                    if(result.equals("Login Succesfull")||result.equals("registration successfull")){
                        userPageActivity();
                    }
                    else{
                        homePageActivity();
                    }
                }

            }
        });
        alert_dialog.create();
        if(operation.equals("Login")){
            alert_dialog.setTitle("Login Status");
        }
        else if(operation.equals("signup")){
            alert_dialog.setTitle("SignUp Status");
        }
        else if(operation.equals("searchDog")){
            alert_dialog.setTitle("search dog Status");
        }
        alert_dialog.show();

    }
    public void userPageActivity(){
        Intent intent = new Intent("com.example.asus.dogcounting.userPage");
        context.startActivity(intent);
    }
    public void homePageActivity(){
        Intent intent = new Intent("com.example.asus.dogcounting.Home");
        context.startActivity(intent);
    }


}
