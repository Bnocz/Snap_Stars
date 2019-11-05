package com.example.scrapp;

import android.os.AsyncTask;
import android.util.Log;

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
            return jsonArray;
        } catch (Exception e) {
            Log.e("DownloadTaskError", "Exception getting JSON from api url " + e);
        }
        return null;
    }


    protected void onPostExecute(Boolean result) {

    }


}

