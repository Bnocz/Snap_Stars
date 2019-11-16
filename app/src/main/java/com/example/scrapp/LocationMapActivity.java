package com.example.scrapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocationMapActivity extends AppCompatActivity
    implements OnMapReadyCallback {

    private Context context = this;
    public static Bitmap currentDetailsDisplayPhoto;

    private GoogleMap mMap;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private boolean mapMoved = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_map);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getString(R.string.app_name)); // for set actionbar_list_activity title

        // Obtain SupportMapFragment and get notified when map is ready
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);


        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        addMarker2Map(0);

        Intent intent = getIntent();
        if (intent.getIntExtra("Place Number",0) == 0 ) {
            // Zoom into users location
            locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    addUserMarker(location);
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {}

                @Override
                public void onProviderEnabled(String s) {}

                @Override
                public void onProviderDisabled(String s) {}
            };

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                addUserMarker(lastKnownLocation);
            } else {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            }
        }
    }

    // Adds user location marker to the map
    private void addUserMarker(Location location) {
        String msg = String.format("Current Location: %4.3f Lat %4.3f Long.",
                location.getLatitude(),
                location.getLongitude());
        LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());

        Marker marker = mMap.addMarker(new MarkerOptions().position(latlng).title(msg).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        marker.setTag("User");

        if (!mapMoved) {
            float zoomLevel = 14.0f;
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng,zoomLevel));
            mapMoved = true;
        }
    }

    // Adds exhibit location markers to the map.
    private void addMarker2Map(int startingIndex) {

        for (final Exhibit exhibit : DataMain.exhibits) {
            LatLng exhibitLatLng = new LatLng(exhibit.exhibitGeom.getLongtitude(),
                    exhibit.exhibitGeom.getLatitude());

            // Marker.setTag is used to specify which exhibit is associated with which marker
            Marker marker = mMap.addMarker(new MarkerOptions().position(exhibitLatLng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
            marker.setTag(exhibit);

            // Sets the on-click listener for each marker
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {

                    if (!marker.getTag().equals("User")) {
                        Exhibit _exhibit = (Exhibit) marker.getTag();
                        currentDetailsDisplayPhoto = _exhibit.getExhibitPhoto().getDisplayphoto();

                        //Put exhibit object in intent
                        Intent intent = new Intent(LocationMapActivity.this, LocationDetailActivity.class);
                        intent.putExtra("exhibitObject", _exhibit);
                        intent.putExtra("sourceActivity", "LocationMapActivity");
                        startActivity(intent);
                    }
                    return false;
                }
            });
        }
    }


    // Actionbar Stuff
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.actionbar_map_activity, menu);
        return true;
    }

    // List activity nav button
    public void onClickActionBar(MenuItem mi) {
            Intent listIntent = new Intent(this, LocationListActivity.class);
            startActivity(listIntent);
            finish();
    }

    public static Bitmap getCurrentDisplayPhoto(){
        return currentDetailsDisplayPhoto;
    }

    // Grabs 10 more results from the api and adds them to the map
    public void generateMoreResults(View view) {

        showLoadingScreen();

        try {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    DataMain.setApiResultsStartIndex(DataMain.getApiResultsStartIndex() + 10);
                    triggerEndLoadingScreen(DataMain.findExhibitsByApi(context));
                    addMarker2Map(DataMain.getApiResultsStartIndex());
                }
            }, 2000);

        } catch (Exception e) {
            Log.e("Error", "findExhibitsByApi Error: " + e);
        }
    }

    private void showLoadingScreen() {
        View loadingScreen = findViewById(R.id.loadingMoreResultsScreen);
        loadingScreen.animate().alpha(1).setDuration(40);
        loadingScreen.setVisibility(View.VISIBLE);
    }

    private void endLoadingScreen() {
        View loadingScreen = findViewById(R.id.loadingMoreResultsScreen);
        loadingScreen.animate().alpha(0).setDuration(40);
        loadingScreen.setVisibility(View.INVISIBLE);
    }

    // Triggered when DataMain.findExhibitsByApi finishes
    private void triggerEndLoadingScreen(boolean result) {
        endLoadingScreen();
    }

}
