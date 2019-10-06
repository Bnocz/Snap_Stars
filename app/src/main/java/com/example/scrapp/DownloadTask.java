package com.example.scrapp;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.example.scrapp.Exhibit;
import com.example.scrapp.ExhibitPhoto;
import com.example.scrapp.ExhibitGeom;


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
            System.out.println("Checkk 1: " + jsonArray.toString());
            return jsonArray;
        } catch (Exception e) {
            System.out.println("Checkk 2 e: " + e);
        }
        return null;
    }


    protected void onPostExecute(Boolean result) {

    }


}

