package com.example.scrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.json.JSONArray;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        DownloadTask downloadTask = new DownloadTask();

        try {
            JSONArray exhibitJSON = downloadTask.execute("https://opendata.vancouver.ca/api/records/1.0/search/?dataset=public-art&rows=10&facet=status&facet=sitename&facet=siteaddress&facet=neighbourhood&facet=artists&facet=photocredits&facet=type&facet=RegistryID&facet=DescriptionofWork&facet=GEOM&facet=recordid&facet=registryid&refine.status=In+place").get();


        } catch (Exception e) {
            System.out.println(e);
        }


    }

    public void btnSendToMap(View view) {


        Intent mapIntent = new Intent(this, LocationMapActivity.class);
        startActivity(mapIntent);
    }

    public void btnSendToCreateAcc(View view) {


        Intent createAccIntent = new Intent(this, CreateAccActivity.class);
        startActivity(createAccIntent);
    }
}
