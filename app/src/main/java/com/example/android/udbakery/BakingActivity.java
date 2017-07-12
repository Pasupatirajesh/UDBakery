package com.example.android.udbakery;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.android.udbakery.Model.BakeryModel;

import java.util.ArrayList;
import java.util.List;

public class BakingActivity extends AppCompatActivity {

    private static final String TAG = BakingActivity.class.getSimpleName();

    public List<BakeryModel.Bakery> mBakeryModelList;

    public RecyclerView mRecyclerView;
    public LinearLayoutManager mLinearLayoutManager;
    public BakeryAdapter mBakeryAdapter;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baking);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

            mBakeryModelList = new ArrayList<>();

            mRecyclerView = (RecyclerView)findViewById(R.id.rv_recipe_card_layout);

            mLinearLayoutManager = new LinearLayoutManager(this);

            mRecyclerView.setLayoutManager(mLinearLayoutManager);

            mBakeryAdapter = new BakeryAdapter(this, mBakeryModelList);

            mRecyclerView.setAdapter(mBakeryAdapter);

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

            fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        new FetchBakesAsyncTask().execute();
    }

    public class FetchBakesAsyncTask extends AsyncTask<Void, Void, List<BakeryModel.Bakery>>
    {

        @Override
        protected List<BakeryModel.Bakery> doInBackground(Void... voids) {
            try {

                new NetworkUtils().start();
            } catch(Exception e)
            {
                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onPostExecute(List<BakeryModel.Bakery> bakeryModelArrayList) {
            super.onPostExecute(bakeryModelArrayList);

            mBakeryModelList = bakeryModelArrayList;
//            Log.i(TAG, mBakeryModelList.size()+"");
            mBakeryAdapter.setBakeryData(mBakeryModelList);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_baking, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
