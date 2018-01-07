package com.example.asus.dogcounting;


import android.content.Context;
import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class backEndWorker2 extends AsyncTask<String, String, ArrayList<searchDogDetails>> {
    Context context;
    String operation;

    public backEndWorker2(Context context) {
        this.context = context;
    }

    @Override
    protected ArrayList<searchDogDetails> doInBackground(String... params) {
        operation = params[0];
        String hostserver = "192.168.43.104:3306";
        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection myConnection = DriverManager.getConnection("jdbc:mysql://" + hostserver + "/dogcounting", "dogCountingApp", "");
            Statement myStatement = myConnection.createStatement();

            if(operation.equals("searchDog")){
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
                }


            }


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return searchDog.allDogsArray;
    }
}
