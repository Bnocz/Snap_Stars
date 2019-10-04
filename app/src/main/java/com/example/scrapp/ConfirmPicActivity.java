package com.example.scrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ConfirmPicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_pic);
    }

    public void btnSendToDetail(View view) {


        Intent detailIntent = new Intent(this, LocationDetailActivity.class);
        startActivity(detailIntent);
    }
}
