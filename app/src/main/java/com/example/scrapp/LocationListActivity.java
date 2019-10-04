package com.example.scrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LocationListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);
    }

    public void btnSendToDetail(View view) {


        Intent detailIntent = new Intent(this, LocationDetailActivity.class);
        startActivity(detailIntent);
    }
    public void btnSendToMap(View view) {


        Intent mapIntent = new Intent(this, LocationMapActivity.class);
        startActivity(mapIntent);
    }
    public void btnSendToSettings(View view) {


        Intent settingsIntent = new Intent(this, UserMenuActivity.class);
        startActivity(settingsIntent);
    }
}
