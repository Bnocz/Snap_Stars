package com.example.scrapp;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadTask extends AsyncTask<String, Void, JSONArray> {

    String JSON = "";

    @Override
    protected JSONArray doInBackground(String... params) {

        // Getting JSON from a URL.
        try {
            URL url = new URL(params[0]);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream in = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(in);
            int data = reader.read();

            while (data != -1) {
                char current = (char) data;
                JSON += current;
                data = reader.read();
            }

            JSONObject jsonObject = new JSONObject(JSON);
            JSONArray jsonArray = jsonObject.getJSONArray("records");
            System.out.println("Adam 1: " + jsonArray.toString());
            return jsonArray;
        } catch (Exception e) {
            System.out.println("Adam e: " + e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(JSONArray jsonArray) {
        System.out.println("Adam 3");




    }

}
