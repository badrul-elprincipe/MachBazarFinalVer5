package com.badrul.user.machbazarfinalver5;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;

public class About extends AppCompatActivity {
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Intent intent=getIntent();

        actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3D4238")));
        actionBar.setTitle(getResources().getString(R.string.mAbout));
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(About.this,Menu.class);
        startActivity(intent);
        finish();
    }
}
