package com.example.scrapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BootUp extends AppCompatActivity {

    static ArrayList<Exhibit> exhibits;
    static boolean exhibitsCreated = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);

        DownloadTask downloadTask = new DownloadTask();

        String apiURL10Results = "https://opendata.vancouver.ca/api/records/1.0/search/?dataset=public-art&rows=10&facet=status&facet=sitename&facet=siteaddress&facet=neighbourhood&facet=artists&facet=photocredits&facet=type&facet=RegistryID&facet=DescriptionofWork&facet=GEOM&facet=recordid&facet=registryid&refine.status=In+place";
        String apiURL600Results = "https://opendata.vancouver.ca/api/records/1.0/search/?dataset=public-art&rows=600&facet=status&facet=sitename&facet=siteaddress&facet=neighbourhood&facet=artists&facet=photocredits&facet=type&facet=RegistryID&facet=DescriptionofWork&facet=GEOM&facet=recordid&facet=registryid&refine.status=In+place";


        try {
            exhibits = createExhibitsFromJSON(downloadTask.execute(apiURL10Results).get());
        } catch (Exception e) {
            Log.e("Check", e.toString());
        }

        System.out.println("Check: exhibits list: " + exhibits);
        System.out.println("Check: # of exhibits processed: " + exhibits.size());
        System.out.println("Check: exhibits created flag: " + exhibitsCreated);
    }

    public ArrayList<Exhibit> createExhibitsFromJSON(JSONArray jsonArray) {

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

                    newExhibitPhoto = new ExhibitPhoto(
                            (String) jsonObjctPhotoURL.get("mimetype"),
                            (String) jsonObjctPhotoURL.get("format"),
                            (String) jsonObjctPhotoURL.get("filename"),
                            (Integer) jsonObjctPhotoURL.get("width"),
                            (String) jsonObjctPhotoURL.get("id"),
                            (Integer) jsonObjctPhotoURL.get("height"),
                            (Boolean) jsonObjctPhotoURL.get("thumbnail")
                    );

                } catch (Exception e) {
                    Log.e("Check", "ExhibitPhoto Creation Error " + e);
                }

                //Create an ExhibitGeom Object from JSON
                try{
                    JSONObject jsonObjectGeom = jsonObjectFields.getJSONObject("geom");
                    JSONArray jsonArrayCoordinates = jsonObjectGeom.getJSONArray("coordinates");
                    List<Double> coordinates = new ArrayList<Double>();

                    if (jsonArrayCoordinates != null) {
                        coordinates.add(jsonArrayCoordinates.getDouble(0));
                        coordinates.add(jsonArrayCoordinates.getDouble(1));
                    }

                    newExhibitGeom = new ExhibitGeom(
                            (String) jsonObjectGeom.get("type"),
                            (List<Double>) coordinates
                    );
                } catch (Exception e) {
                    Log.e("Check", "ExhibitGeom Creation Error " + e);
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
                    Log.e("Check", "Exhibit Creation Error " + e);
                }

                if (newExhibit != null) {
                    exhibits.add(newExhibit);
                }

            } catch (Exception e){
                Log.e("Check", e.toString());
            }
        }
        BootUp.exhibitsCreated = true;
        return exhibits;
    }
}