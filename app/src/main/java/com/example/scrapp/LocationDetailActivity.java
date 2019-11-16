package com.example.scrapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import static android.icu.lang.UProperty.INT_START;

public class LocationDetailActivity extends AppCompatActivity {

    Exhibit exhibit;
    Bitmap displayPhoto;
    static final int REQUEST_TAKE_PHOTO = 1;
    String currentPhotoPath;
    ImageView photo;

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
         photo = findViewById(R.id.iv_photo);

            if (sourceActivity.equals("LocationListActivity")) {
                displayPhoto = LocationListActivity.getCurrentDisplayPhoto();
            } else if (sourceActivity.equals("LocationMapActivity")) {
                displayPhoto = LocationMapActivity.getCurrentDisplayPhoto();
            }
            displayPhoto = this.toGrayscale(displayPhoto);
            photo.setImageBitmap(displayPhoto);

        populateFields();
    }


    public void populateFields(){
        TextView type = findViewById(R.id.tv_type);
        String typeText = "<b> Exhibit Type: </b>" + exhibit.getType();
        type.setText(Html.fromHtml(typeText, 0));

        TextView area = findViewById(R.id.tv_area);
        String areaText = "<b> Area: </b>" + exhibit.getGeoLocalArea();
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
        String linkText = "<b> More Info: </b><a href='" + linkURL + "'>" + linkURL;
        link.setText(Html.fromHtml(linkText, Html.FROM_HTML_MODE_COMPACT));
    }

    public Bitmap toGrayscale(Bitmap bmpOriginal)
    {
        int width;
        int height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();

        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGrayscale;
    }
    public void onCameraClick(View v) {
        Log.e("Check 111: ", "Inside handler");
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {

            }

            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.scrapp",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                Bitmap myBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                setImageFromCamera(myBitmap);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
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

    public void setImageFromCamera(Bitmap photoBM) {
        photo.setImageBitmap(photoBM);
    }

    public void onClickActionBar(MenuItem mi) {
            finish();
    }
}
