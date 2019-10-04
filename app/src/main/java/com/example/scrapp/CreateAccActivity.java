package com.example.scrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CreateAccActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_acc);
    }

    public void btnSendToLogin(View view) {


        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
    }
    public void onBackClick(View v) {
        finish();
    }
}
