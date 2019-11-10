package com.example.scrapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.BitmapCompat;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DataMain extends AppCompatActivity {

    private Context context = this;

    // User Location Variables
    static LatLng userCoor;
    LocationManager locationManager;
    LocationListener locationListener;
    private boolean userLocationFound = false;


    // Exhibit Data Variables
    static ArrayList<Exhibit> exhibits;
    static boolean exhibitsCreated = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupLocationServices();

        // Waits for location services to find user before finding nearby exhibits.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                findExhibitsByApi();
            }
        }, 5000);


        // Uncomment this to see what Exhibits we're getting. Sometimes returns null, because async
//        new Handler().postDelayed(new Runnable() {
//            @Override
//                public void run() {
//            for (Exhibit exhibit : exhibits) {
//                System.out.println("Loaded Exhibits: " + exhibit.toString());
//            }
//            }
//        }, 10000);

        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);

    }

    // Constantly updates user's location
    public void setupLocationServices() {
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {

                    try {
                        userCoor = new LatLng(location.getLatitude(),
                                location.getLongitude());
                        userLocationFound = true;

                    } catch (Exception e) {
                        Log.e("User Coordinate Error", "User coordinates not found.");
                    }

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {}
        };

        // Asks user for location permissions
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

    // More location permissions stuff
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

    // Pulls exhibit data from the API
    public void findExhibitsByApi() {
        DownloadTask downloadTask = new DownloadTask();

        DecimalFormat df = new DecimalFormat("##0.000000");

        String apiURL10ResultsNearby;

        while (true) {
            if (userLocationFound) {
                apiURL10ResultsNearby = "https://opendata.vancouver.ca/api/records/1.0/search/?dataset=public-art&rows=10&refine.status=In+place&geofilter.distance=" + df.format(this.getUserLatitude()) + "%2C+" + df.format(this.getUserLongtitude()) + "%2C+" + "2000";
                Log.e("API URL", apiURL10ResultsNearby);
                break;
            }
        }


        try {
            exhibits = createExhibitsFromJSON(downloadTask.execute(apiURL10ResultsNearby).get());
            System.out.println("Check: exhibits list: " + exhibits);
            System.out.println("Check: # of exhibits processed: " + exhibits.size());
            System.out.println("Check: exhibits created flag: " + exhibitsCreated);
        } catch (Exception e) {
            Log.e("Exhibits/JSON Error ", e.toString());
        }

    }

    // Takes a JSON array and turns it into multiple Exhibit objects
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

                try{
                    jsonObjectPhotoURL = jsonObjectFields.getJSONObject("photourl");

                    ImageDownloadTask downloadTask = new ImageDownloadTask();
                    Bitmap bmpimg = null;

                    String apiImageResult = "https://covapp.vancouver.ca/PublicArtRegistry/ImageDisplay." + jsonObjectPhotoURL.get("format");

                    bmpimg = downloadTask.execute(apiImageResult).get();
                    photoAttributes.put("displayphoto", bmpimg);

                } catch (Exception e) {
                    Log.e("Check", "ExhibitPhoto Creation Error " + e);
                    photoAttributes.put("displayphoto", BitmapFactory.decodeResource(getApplicationContext().getResources(),
                            R.drawable.default_photo));
                }

                try{photoAttributes.put("mimetype", jsonObjectPhotoURL.get("mimetype"));}catch(Exception e){};
                try{photoAttributes.put("format", jsonObjectPhotoURL.get("format"));}catch(Exception e){
                    Log.e("Check", "ExhibitPhoto Creation Error " + e);
                }
                try{photoAttributes.put("filename", jsonObjectPhotoURL.get("filename"));}catch(Exception e){};
                try{photoAttributes.put("width", jsonObjectPhotoURL.get("width"));}catch(Exception e){};
                try{photoAttributes.put("id", jsonObjectPhotoURL.get("id"));}catch(Exception e){};
                try{photoAttributes.put("height", jsonObjectPhotoURL.get("height"));}catch(Exception e){};

                newExhibitPhoto = new ExhibitPhoto(photoAttributes);

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
        DataMain.exhibitsCreated = true;
        return exhibits;
    }

    public double getUserLatitude(){
        return userCoor.latitude;
    }

    public double getUserLongtitude(){
        return userCoor.longitude;
    }

    public ArrayList<Exhibit> getNearbyExhibits(){
        return exhibits;
    }

}