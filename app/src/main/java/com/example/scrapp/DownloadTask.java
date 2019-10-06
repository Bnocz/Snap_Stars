package com.example.scrapp;

import android.os.AsyncTask;
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

    @Override
    protected void onPostExecute(JSONArray jsonArray) {
        System.out.println("Checkk 3");

        ArrayList<Exhibit> exhibits = new ArrayList<Exhibit>();

        for (int i = 0; i < jsonArray.length(); i++){

            Exhibit newExhibit = null;
            ExhibitGeom newExhibitGeom = null;
            ExhibitPhoto newExhibitPhoto = null;


            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                JSONObject jsonObjectFields = jsonObject.getJSONObject("fields");

                //Create an ExhibitPhoto Object from JSON
                try{
                    JSONObject jsonObjctPhotoURL = jsonObjectFields.getJSONObject("photourl");
                    System.out.println("Checkk 5");

                    newExhibitPhoto = new ExhibitPhoto(
                            (String) jsonObjctPhotoURL.get("mimetype"),
                            (String) jsonObjctPhotoURL.get("format"),
                            (String) jsonObjctPhotoURL.get("filename"),
                            (Integer) jsonObjctPhotoURL.get("width"),
                            (String) jsonObjctPhotoURL.get("id"),
                            (Integer) jsonObjctPhotoURL.get("height"),
                            (Boolean) jsonObjctPhotoURL.get("thumbnail")
                    );

                    System.out.println("Checkk 7: " + newExhibitPhoto.toString());

                } catch (Exception e) {
                    System.out.println("Checkk e -- ExhibitPhoto Creation Error " + e);
                }

                //Create an ExhibitGeom Object from JSON
                try{
                    JSONObject jsonObjectGeom = jsonObjectFields.getJSONObject("geom");
                    JSONArray jsonArrayCoordinates = jsonObjectGeom.getJSONArray("coordinates");
                    List<Double> coordinates = new ArrayList<Double>();

                    System.out.println("Chekk 11: " + coordinates);

                    if (jsonArrayCoordinates != null) {
                        coordinates.add(jsonArrayCoordinates.getDouble(0));
                        coordinates.add(jsonArrayCoordinates.getDouble(1));
                    }

                    newExhibitGeom = new ExhibitGeom(
                            (String) jsonObjectGeom.get("type"),
                            (List<Double>) coordinates
                    );
                } catch (Exception e) {
                    System.out.println("Checkk e -- ExhibitGeom Creation Error " + e);
                }

                //Create an Exhibit Object from JSON
                try{
                    newExhibit = new Exhibit(
                            (String) jsonObjectFields.get("sitename"),
                            (String) jsonObjectFields.get("status"),
                            (String) jsonObjectFields.get("descriptionofwork"),
                            newExhibitPhoto,
                            (String) jsonObjectFields.get("url"),
                            (Integer) jsonObjectFields.get("registryid"),
                            newExhibitGeom,
                            (String) jsonObjectFields.get("artists"),
                            (String) jsonObjectFields.get("siteaddress"),
                            (String) jsonObjectFields.get("geo_local_area"),
                            (String) jsonObjectFields.get("type"),
                            (String) jsonObjectFields.get("locationonsite")

                    );
                } catch (Exception e) {
                    System.out.println("Checkk e -- Exhibit Creation Error " + e);
                }

                if (newExhibit != null) {
                    exhibits.add(newExhibit);
                }




            } catch (Exception e){
                System.out.println("Checkk 4: " + e);
            }

            System.out.println("Checkk 8: " + exhibits.size());






        }


    }

    

}
