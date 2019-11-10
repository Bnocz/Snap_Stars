package com.example.scrapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcel;
import android.provider.MediaStore;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.Serializable;

import static android.icu.lang.UProperty.INT_START;

public class LocationDetailActivity extends AppCompatActivity {

    Exhibit exhibit;
    Bitmap displayPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_detail);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getString(R.string.app_name)); // for set actionbar_list_activity title

        Intent i = getIntent();
        exhibit = (Exhibit) i.getParcelableExtra("exhibitObject");
        String sourceActivity = i.getStringExtra("sourceActivity");

        // Sets up the display photo (grabbed from whichever activity brought us here).
        if (sourceActivity.equals("LocationListActivity")) {
            displayPhoto = LocationListActivity.getCurrentDisplayPhoto();
//        } else if (sourceActivity.equals("LocationMapActivity")) {
//            displayPhoto = LocationMapActivity.getCurrentDisplayPhoto();
        }

        ImageView photo = findViewById(R.id.iv_photo);
        photo.setImageBitmap(displayPhoto);

        populateFields();
    }


    public void populateFields(){
        TextView type = findViewById(R.id.tv_type);
        String typeText = "<b> Exhibit Type: </b>" + exhibit.getType();
        type.setText(Html.fromHtml(typeText, 0));

        TextView area = findViewById(R.id.tv_area);
        String areaText = "<b> Address: </b>" + exhibit.getGeoLocalArea();
        area.setText(Html.fromHtml(areaText, 0));

        TextView address = findViewById(R.id.tv_address);
        String addressText = "<b> Address: </b>" + exhibit.getSiteaddress();
        address.setText(Html.fromHtml(addressText, 0));

        TextView locationOnSite = findViewById(R.id.tv_location_on_site);
        String locationText = "<b> Location: </b>" + exhibit.getLocationonsite();
        locationOnSite.setText(Html.fromHtml(locationText, 0));

        TextView details = findViewById(R.id.tv_details);
        String detailsText = "<b> Details: </b>" + exhibit.getDescriptionofwork();
        details.setText(Html.fromHtml(detailsText, 0));

        TextView link = findViewById(R.id.tv_link);
        link.setClickable(true);
        link.setMovementMethod(LinkMovementMethod.getInstance());
        String linkURL = exhibit.getUrl();
        String linkText = "<a href='" + linkURL + "'><b> More Info: </b>" + linkURL;
        link.setText(Html.fromHtml(linkText, Html.FROM_HTML_MODE_COMPACT));
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
