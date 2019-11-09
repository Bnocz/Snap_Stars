package com.example.scrapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcel;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.Serializable;

public class LocationDetailActivity extends AppCompatActivity {

    Exhibit exhibit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_detail);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getString(R.string.app_name)); // for set actionbar_list_activity title

        Intent i = getIntent();
        exhibit = (Exhibit) i.getParcelableExtra("exhibitObject");

        //Retrieve Parcel containing display photo. Sticks it back in Exhibit object
//        Parcel parcel = Parcel.obtain();
//        parcel.setDataPosition(0);
//        Bitmap bitmap = Bitmap.CREATOR.createFromParcel(parcel);


        Bitmap bitmap = LocationListActivity.getCurrentDisplayPhoto();
        Log.e("Check 667", bitmap.toString());

        ImageView photo = findViewById(R.id.iv_photo);
        photo.setImageBitmap(bitmap);

        //exhibit.getExhibitPhoto().setDisplayphoto(bitmap);

//        Log.e("Check 666", "" + bitmap.toString());

        populateFields();
    }

    public void populateFields(){


        TextView type = findViewById(R.id.tv_type);
        type.setText(exhibit.getType());

        TextView link = findViewById(R.id.tv_link);
        link.setText(exhibit.getUrl());

        TextView address = findViewById(R.id.tv_address);
        address.setText(exhibit.getSiteaddress());

        TextView locationOnSite = findViewById(R.id.tv_location_on_site);
        locationOnSite.setText(exhibit.getLocationonsite());

        TextView details = findViewById(R.id.tv_details);
        details.setText(exhibit.getDescriptionofwork());
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
            finish();
    }
}
