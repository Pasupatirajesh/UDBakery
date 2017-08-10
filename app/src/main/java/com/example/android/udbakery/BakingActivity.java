package com.example.android.udbakery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.udbakery.Model.BakeryPojo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BakingActivity extends AppCompatActivity implements BakeryAdapter.onItemClickedInterface{

    private static final String TAG = BakingActivity.class.getSimpleName();
    private static final String API_URL = "https://d17h27t6h515a5.cloudfront.net/";

    public List<BakeryPojo> mBakeryModelList;

    public RecyclerView mRecyclerView;
    public LinearLayoutManager mLinearLayoutManager;
    public GridLayoutManager mGridLayoutManager;
    public BakeryAdapter mBakeryAdapter;

    public static Parcelable mWrapper;

    public static BakeryPojo mBakery;

    private boolean mTwoPane;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baking);


            if(findViewById(R.id.bakery_me_linear_layout)!=null)
            {
                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);
                mTwoPane = true;

                mBakeryModelList = new ArrayList<>();

                mRecyclerView = (RecyclerView)findViewById(R.id.rv_recipe_card_layout);

                mBakeryAdapter = new BakeryAdapter(getApplicationContext(), (ArrayList<BakeryPojo>) mBakeryModelList, this);

                mGridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);

                mRecyclerView.setAdapter(mBakeryAdapter);

                mRecyclerView.setLayoutManager(mGridLayoutManager);


            } else
            {
                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);
                mTwoPane = false;

                mBakeryModelList = new ArrayList<>();

                mRecyclerView = (RecyclerView)findViewById(R.id.rv_recipe_card_layout);

                mBakeryAdapter = new BakeryAdapter(getApplicationContext(), (ArrayList<BakeryPojo>) mBakeryModelList, this);

                mLinearLayoutManager = new LinearLayoutManager(getApplicationContext());

                mRecyclerView.setAdapter(mBakeryAdapter);

                mRecyclerView.setLayoutManager(mLinearLayoutManager);
            }



            Gson gson = new GsonBuilder().setLenient().create();


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(API_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            BakeryItemAPI bakeryItemAPI = retrofit.create(BakeryItemAPI.class);

            Call<List<BakeryPojo>> call = bakeryItemAPI.loadIngredients();

            call.enqueue(new Callback<List<BakeryPojo>>() {
                @Override
                public void onResponse(Call<List<BakeryPojo>> call, Response<List<BakeryPojo>> response) {

                    Log.i(TAG, response.body()+"");
                    mBakeryModelList = response.body();
                    mBakeryAdapter.setBakeryData((ArrayList<BakeryPojo>) mBakeryModelList);
                }

                @Override
                public void onFailure(Call<List<BakeryPojo>> call, Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Not working", Toast.LENGTH_SHORT).show();
                }
            });
//            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//
//            fab.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
//                }
//            });

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

    @Override
    public void onItemClicked(int clickedListItem) {
        Toast.makeText(this, "I was clicked", Toast.LENGTH_SHORT).show();
        Context context = this;
        Intent myIntent = new Intent(context, BakingDetailActivity.class);
        mBakery = mBakeryModelList.get(clickedListItem);
        mWrapper = Parcels.wrap(mBakery);
        myIntent.putExtra("BakingPojo", mWrapper);
        startActivity(myIntent);
    }
}
