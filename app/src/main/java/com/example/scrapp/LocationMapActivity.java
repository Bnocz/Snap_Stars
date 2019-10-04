package com.example.scrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LocationMapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_map);
    }
    public void btnSendToList(View view) {


        Intent listIntent = new Intent(this, LocationListActivity.class);
        startActivity(listIntent);
    }
    public void btnSendToDetail(View view) {


        Intent detailIntent = new Intent(this, LocationDetailActivity.class);
        startActivity(detailIntent);
    }
    public void btnSendToSettings(View view) {


        Intent settingsIntent = new Intent(this, UserMenuActivity.class);
        startActivity(settingsIntent);
    }
}
