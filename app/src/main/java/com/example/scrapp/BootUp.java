package com.example.scrapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.lang.Object;

public class BootUp extends AppCompatActivity {

    static ArrayList<Exhibit> exhibits;
    static boolean exhibitsCreated = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);




        DownloadTask downloadTask = new DownloadTask();

        String apiURL10ResultsWithFacets= "https://opendata.vancouver.ca/api/records/1" +
                ".0/search/?dataset=public-art&rows=10&facet=status&facet=sitename&facet=siteaddress&facet=neighbourhood&facet=artists&facet=photocredits&facet=type&facet=RegistryID&facet=DescriptionofWork&facet=GEOM&facet=recordid&facet=registryid&refine.status=In+place";
        String apiURL10Results = "https://opendata.vancouver.ca/api/records/1.0/search/?dataset=public-art&rows=10&refine.status=In+place";
        String apiURL100Results = "https://opendata.vancouver.ca/api/records/1" +
                ".0/search/?dataset=public-art&rows=100&refine.status=In+place";
        String apiURL600Results = "https://opendata.vancouver.ca/api/records/1" +
                ".0/search/?dataset=public-art&rows=600&refine.status=In+place";


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
                HashMap <String, Object> photoAttributes = ExhibitPhoto.getExhibitPhotoBaseAttributes();

                JSONObject jsonObjectPhotoURL = null;
                boolean photoSuccess = false;

                try{
                    jsonObjectPhotoURL = jsonObjectFields.getJSONObject("photourl");
                    photoSuccess = true;
                } catch (Exception e) {
                    Log.e("Check", "ExhibitPhoto Creation Error " + e);
                }

                if (photoSuccess = true) {
                    try{photoAttributes.put("mimetype", jsonObjectPhotoURL.get("mimetype"));}catch(Exception e){};
                    try{photoAttributes.put("format", jsonObjectPhotoURL.get("format"));}catch(Exception e){
                        Log.e("Check", "ExhibitPhoto Creation Error " + e);
                    }
                    try{photoAttributes.put("filename", jsonObjectPhotoURL.get("filename"));}catch(Exception e){};
                    try{photoAttributes.put("width", jsonObjectPhotoURL.get("width"));}catch(Exception e){};
                    try{photoAttributes.put("id", jsonObjectPhotoURL.get("id"));}catch(Exception e){};
                    try{photoAttributes.put("height", jsonObjectPhotoURL.get("height"));}catch(Exception e){};

                    newExhibitPhoto = new ExhibitPhoto(photoAttributes);
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
                HashMap <String, Object> exhibitAttributes = Exhibit.getExhibitBaseAttributes();

                try{exhibitAttributes.put("sitename", jsonObjectFields.get("sitename"));}catch (Exception e){};
                try{exhibitAttributes.put("status", jsonObjectFields.get("status"));}catch (Exception e){};
                try{exhibitAttributes.put("descriptionofwork", jsonObjectFields.get("descriptionofwork"));}catch (Exception e){};
                try{exhibitAttributes.put("exhibitPhoto", newExhibitPhoto);}catch (Exception e){};
                try{exhibitAttributes.put("url", jsonObjectFields.get("url"));}catch (Exception e){};
                try{exhibitAttributes.put("registryid", jsonObjectFields.get("registryid"));}catch (Exception e){};
                try{exhibitAttributes.put("exhibitGeom", newExhibitGeom);}catch (Exception e){
                    Log.e("Check", "Exhibit Creation Error -- Geom " + e);
                };
                try{exhibitAttributes.put("artists", jsonObjectFields.get("artists"));}catch (Exception e){};
                try{exhibitAttributes.put("siteaddress", jsonObjectFields.get("siteaddress"));}catch (Exception e){};
                try{exhibitAttributes.put("geo_local_area", jsonObjectFields.get("geo_local_area"));}catch (Exception e){};
                try{exhibitAttributes.put("type", jsonObjectFields.get("type"));}catch (Exception e){};
                try{exhibitAttributes.put("locationonsite", jsonObjectFields.get("locationonsite"));}catch (Exception e){};

//                System.out.println("" + i);
                System.out.println(exhibitAttributes.toString());


                try{
                    if (newExhibitGeom != null) {
                        newExhibit = new Exhibit(exhibitAttributes);
                    }
                } catch (Exception e) {
                    Log.e("Check", "Exhibit Creation Null Error " + e.getStackTrace()[0].getLineNumber());
                }

                if (newExhibit != null) {
                    exhibits.add(newExhibit);
                }

            } catch (Exception e){
                Log.e("Total Creation Error ", e.toString());
            }
        }
        BootUp.exhibitsCreated = true;
        return exhibits;
    }
}