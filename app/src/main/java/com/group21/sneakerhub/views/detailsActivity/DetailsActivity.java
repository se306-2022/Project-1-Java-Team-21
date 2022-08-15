package com.group21.sneakerhub.views.detailsActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.group21.sneakerhub.R;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportActionBar().hide();
    }
}