package com.example.scrapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class LocationListActivity extends AppCompatActivity {

    public static Bitmap currentDetailsDisplayPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getString(R.string.app_name)); // for set actionbar_list_activity title

        displayExhibits();
    }


    //Creates a Layout and View for each title.
    private void displayExhibits(){
        for (final Exhibit exhibit : DataMain.exhibits) {

            ArrayList<Exhibit> exhibits = DataMain.exhibits;

            // Outermost Layout
            LinearLayout displayLayout = findViewById(R.id.list_items);

            // Grouping Layout
            LinearLayout groupingLayout = new LinearLayout(this);
            groupingLayout.setOrientation(LinearLayout.HORIZONTAL);
            groupingLayout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    currentDetailsDisplayPhoto = exhibit.getExhibitPhoto().getDisplayphoto();
                    Log.e("Check 665", currentDetailsDisplayPhoto.toString());

                    //Put exhibit object in intent
                    Intent intent = new Intent(view.getContext(), LocationDetailActivity.class);
                    intent.putExtra("exhibitObject", exhibit);

                    //Add display photo (Bitmap) to parcel
//                    Parcel parcel = Parcel.obtain();
//                    exhibit.getExhibitPhoto().getDisplayphoto().writeToParcel(parcel, 0);

                    startActivity(intent);
                }
            });

            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(10, 10, 0, 10);
            groupingLayout.setLayoutParams(lp);

            try {
                ImageView thumbnail = new ImageView(this);
                Bitmap bitmap = exhibit.getExhibitPhoto().getDisplayphoto();
                thumbnail.setImageBitmap(bitmap);
                thumbnail.setAdjustViewBounds(true);
                thumbnail.setMaxHeight(200);
                thumbnail.setMaxWidth(200);
                groupingLayout.addView(thumbnail);
            } catch (Exception e) {
                Log.e("DisplayImage", "Display image not found: " + e);
            }

            // TextView Layout
            LinearLayout textViewLayout = new LinearLayout(this);
            textViewLayout.setOrientation(LinearLayout.VERTICAL);

            TextView exhibitType = setupListItemAttributeLine1(exhibit.getType());
            TextView exhibitAddress = setupListItemAttributeLine2(exhibit.getSiteaddress());

            // Nesting (non-image)layouts and views
            textViewLayout.addView(exhibitType);
            textViewLayout.addView(exhibitAddress);
            groupingLayout.addView(textViewLayout);
            displayLayout.addView(groupingLayout);
        }
    }


    public TextView setupListItemAttributeLine1(String text){
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setId(View.generateViewId());
        textView.setTextSize(17);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setPadding(10,0,10,0);

        return textView;
    }

    public TextView setupListItemAttributeLine2(String text){
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setId(View.generateViewId());
        textView.setTextSize(17);
        textView.setPadding(10,2,10,0);

        return textView;
    }

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

    public static Bitmap getCurrentDisplayPhoto(){
        return currentDetailsDisplayPhoto;
    }


}
