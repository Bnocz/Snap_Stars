package com.example.scrapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LocationListActivity extends AppCompatActivity {

    private Context context = this;
    public static Bitmap currentDetailsDisplayPhoto;
    boolean taskComplete = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getString(R.string.app_name)); // for set actionbar_list_activity title

        if (DataMain.foundExhibitsByAPIOnce) {
            displayExhibits(0);
        }
    }


    //Creates a Layout and View for each title.
    private void displayExhibits(int startingIndex){
        for (final Exhibit exhibit : DataMain.exhibits.subList(startingIndex, DataMain.exhibits.size())) {
            Log.e("Check 222: ", "" + startingIndex);
            Log.e("Check 223: ", "" + DataMain.exhibits.size());


            // Outermost Layout
            LinearLayout displayLayout = findViewById(R.id.list_items);

            // Grouping Layout
            LinearLayout groupingLayout = new LinearLayout(this);
            groupingLayout.setOrientation(LinearLayout.HORIZONTAL);
            groupingLayout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    currentDetailsDisplayPhoto = exhibit.getExhibitPhoto().getDisplayphoto();

                    //Put exhibit object in intent
                    Intent intent = new Intent(view.getContext(), LocationDetailActivity.class);
                    intent.putExtra("exhibitObject", exhibit);
                    intent.putExtra("sourceActivity", "LocationListActivity");
                    startActivity(intent);

                    finish();
                }
            });

            // Grouping layout margin params
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(10, 10, 0, 10);
            groupingLayout.setLayoutParams(lp);

            // Get and display exhibit thumbnail
            try {
                ImageView thumbnail = new ImageView(this);
                Bitmap bitmap = exhibit.getExhibitPhoto().getDisplayphoto();
                Bitmap resized = Bitmap.createScaledBitmap(bitmap, 250, 200, true);
                thumbnail.setImageBitmap(resized);
                groupingLayout.addView(thumbnail);
            } catch (Exception e) {
                Log.e("DisplayImage", "Display image not found: " + e);
            }

            // TextView Layout
            LinearLayout textViewLayout = new LinearLayout(this);
            textViewLayout.setOrientation(LinearLayout.VERTICAL);

            TextView exhibitType = setupListItemAttributeLine1(exhibit.getType());
            TextView exhibitAddress = setupListItemAttributeLine2(exhibit.getSiteaddress());
            TextView exhibitArea = setupListItemAttributeLine2(exhibit.getGeoLocalArea());

            // Nesting (non-image)layouts and views
            textViewLayout.addView(exhibitType);
            if (!exhibitAddress.getText().equals("")) {
                textViewLayout.addView(exhibitAddress);
            }
            textViewLayout.addView(exhibitArea);
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

    // To Map Activity button
    public void onClickActionBar(MenuItem mi) {
        if (!DataMain.currentlyLoadingExhibits) {
            Intent listIntent = new Intent(this, LocationMapActivity.class);
            startActivity(listIntent);
            finish();
        }
    }

    public static Bitmap getCurrentDisplayPhoto(){
        return currentDetailsDisplayPhoto;
    }

    // Grabs 10 more results from the API and adds them to the list
    public void generateMoreResults(View view) {

        showLoadingScreen();
        DataMain.currentlyLoadingExhibits = true;

        try {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (DataMain.foundExhibitsByAPIOnce) {
                        DataMain.setApiResultsStartIndex(DataMain.getApiResultsStartIndex() + 10);
                        triggerEndLoadingScreen(DataMain.findExhibitsByApi(context));
                        DataMain.currentlyLoadingExhibits = false;
                        displayExhibits(DataMain.getApiResultsStartIndex());
                    } else {
                        triggerEndLoadingScreen(DataMain.findExhibitsByApi(context));
                        DataMain.currentlyLoadingExhibits = false;
                        if (DataMain.foundExhibitsByAPIOnce) {
                            displayExhibits(DataMain.getApiResultsStartIndex());
                        }
                    }
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

    // Moves activity to back on back press, rather than closing it outright
    public void onBackPressed () {
        moveTaskToBack (true);
    }

}
