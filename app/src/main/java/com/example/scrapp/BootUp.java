package com.example.scrapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.lang.Object;
import java.util.Locale;


public class BootUp extends AppCompatActivity {

    private Context context = this;

    // User Location Variables
    LatLng userCoor;
    LocationManager locationManager;
    LocationListener locationListener;
    private boolean userLocationFound = false;


    // Exhibit Data Variables
    static ArrayList<Exhibit> exhibits;
    static boolean exhibitsCreated = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupLocationServices();
        findExhibitsByApi();

        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);

    }


    public void setupLocationServices() {
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {

                    try {
                        LatLng userCoor = new LatLng(location.getLatitude(),
                                location.getLongitude());

                        //System.out.println("check 5" + userCoor.toString());

                    } catch (Exception e) {
                        Log.e("User Coor Error", "User coordinates not found.");
                    }
                    userLocationFound = true;
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {}
        };

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            }
        }
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

    public void findExhibitsByApi() {
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

}