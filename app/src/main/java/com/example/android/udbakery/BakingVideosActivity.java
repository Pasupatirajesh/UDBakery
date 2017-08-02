package com.example.android.udbakery;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

public class BakingVideosActivity extends AppCompatActivity {

    private static final java.lang.String TAG = BakingVideosActivity.class.getSimpleName();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baking_videos);

        ActionBar ab = getSupportActionBar();

        ab.setDisplayShowHomeEnabled(true);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment);
        if (fragment==null)
        {
            fragment =new  BakeryVideoFragment();
            fm.beginTransaction().add(R.id.fragment,fragment).commit();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
