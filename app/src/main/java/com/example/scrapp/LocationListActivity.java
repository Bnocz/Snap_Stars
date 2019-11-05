package com.example.scrapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class LocationListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getString(R.string.app_name)); // for set actionbar_list_activity title
    }




    public void btnSendToDetail(View view) {
        Intent detailIntent = new Intent(this, LocationDetailActivity.class);
        startActivity(detailIntent);
    }

    public void btnSendToMap(View view) {
        Intent mapIntent = new Intent(this, LocationMapActivity.class);
        startActivity(mapIntent);
    }

//    public void btnSendToSettings(View view) {
//        Intent settingsIntent = new Intent(this, UserMenuActivity.class);
//        startActivity(settingsIntent);
//    }

    // Actionbar Stuff
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.actionbar_list_activity, menu);
        return true;
    }

    public void onClickActionBar(MenuItem mi) {
        Intent listIntent = new Intent(this, LocationMapActivity.class);
        startActivity(listIntent);
    }
}
