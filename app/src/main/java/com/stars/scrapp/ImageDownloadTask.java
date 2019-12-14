package com.stars.scrapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class ImageDownloadTask extends AsyncTask<String, Void, Bitmap> {

    String JSON = "";

    @Override
    protected Bitmap doInBackground(String... params) {

        String imageurl = params[0];
        InputStream in = null;

        try
        {
            Log.i("URL", imageurl);
            URL url = new URL(imageurl);
            URLConnection urlConn = url.openConnection();
            HttpURLConnection httpConn = (HttpURLConnection) urlConn;
            httpConn.connect();

            in = httpConn.getInputStream();
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        Bitmap bmpimg = BitmapFactory.decodeStream(in);
        return bmpimg;
    }


    protected void onPostExecute(Boolean result) {

    }

}

