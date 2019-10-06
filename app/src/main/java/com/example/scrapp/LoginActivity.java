package com.example.scrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);






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
