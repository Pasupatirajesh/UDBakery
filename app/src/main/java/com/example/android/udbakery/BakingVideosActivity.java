package com.example.android.udbakery;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class BakingVideosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baking_videos);

        ActionBar ab = getSupportActionBar();

        ab.setDisplayHomeAsUpEnabled(true);

        ab.setTitle(R.string.fragment_actionbar_title);

        FragmentManager fm = getSupportFragmentManager();
            Fragment fragment = fm.findFragmentById(R.id.fragment);
            if (fragment==null)
            {
                fragment =new  BakeryVideoFragment();
                fm.beginTransaction().add(R.id.fragment,fragment).commit();
            }
        }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
