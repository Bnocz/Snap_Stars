package com.example.scrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
