package com.example.scrapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class LocationDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_detail);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getString(R.string.app_name)); // for set actionbar_list_activity title
    }

    public void onBackClick(View v) {
        finish();
    }

    // Actionbar Stuff
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.actionbar_details_activity, menu);
        return true;
    }

    public void onClickActionBar(MenuItem mi) {
        int i = mi.getItemId();
        if (i == R.id.goToMap) {
            Intent listIntent = new Intent(this, LocationMapActivity.class);
            startActivity(listIntent);
        }else if (i == R.id.goToList) {
            Intent listIntent = new Intent(this, LocationListActivity.class);
            startActivity(listIntent);
        }
    }
}
