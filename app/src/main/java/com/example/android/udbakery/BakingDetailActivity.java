package com.example.android.udbakery;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.android.udbakery.Model.BakeryPojo;

import org.parceler.Parcels;

import java.util.List;

public class BakingDetailActivity extends AppCompatActivity implements BakeryStepAdapter.nextVideoInterface{


    private static final String TAG = BakingDetailActivity.class.getSimpleName();

    public static BakeryPojo mBakeryPojo;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private TextView mIngredientsTextView;
    private BakeryStepAdapter mBakeryStepAdapter;
    private Bundle  myIntent;
    private MenuItem mMenuItem;


    private Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myIntent = getIntent().getExtras();

        Configuration configuration = getResources().getConfiguration();

        if(configuration.smallestScreenWidthDp >=600) {

            setContentView(R.layout.activity_step_desc);

            toolBar = (Toolbar) findViewById(R.id.toolbar);

            setSupportActionBar(toolBar);

            ActionBar ab = getSupportActionBar();

            ab.setDisplayHomeAsUpEnabled(true);

            if (myIntent != null) {

                if (myIntent.getParcelable("BakingPojo") != null) {

                    mBakeryPojo = Parcels.unwrap(myIntent.getParcelable("BakingPojo"));

                    ab.setTitle((mBakeryPojo.getName() + " "));

                    mIngredientsTextView = (TextView) findViewById(R.id.tv_1);

                    mIngredientsTextView.setText(mBakeryPojo.getIngredients().get(0).getIngredient()
                            + ", " + mBakeryPojo.getIngredients().get(1).getIngredient() + ", "
                            + mBakeryPojo.getIngredients().get(2).getIngredient() +
                            ", " + mBakeryPojo.getIngredients().get(3).getIngredient() +
                            ", " + mBakeryPojo.getIngredients().get(4).getIngredient() +
                            ", " + mBakeryPojo.getIngredients().get(5).getIngredient() +
                            ", " + mBakeryPojo.getIngredients().get(6).getIngredient() +
                            ", " + mBakeryPojo.getIngredients().get(7).getIngredient() +
                            ", " + mBakeryPojo.getIngredients().get(8).getIngredient());

                    mRecyclerView = (RecyclerView) findViewById(R.id.rv_steps_desc);

                    mBakeryStepAdapter = new BakeryStepAdapter(getApplicationContext(), mBakeryPojo, this);

                    mLinearLayoutManager = new LinearLayoutManager(this);

                    mRecyclerView.setAdapter(mBakeryStepAdapter);

                    mRecyclerView.setLayoutManager(mLinearLayoutManager);
                }

            }

        } else {

            setContentView(R.layout.activity_step_desc);
            toolBar = (Toolbar) findViewById(R.id.toolbar);

            setSupportActionBar(toolBar);

            ActionBar ab = getSupportActionBar();

            ab.setDisplayHomeAsUpEnabled(true);


            if (myIntent != null) {
                    if (myIntent.getParcelable("BakingPojo") != null) {

                        mBakeryPojo = Parcels.unwrap(myIntent.getParcelable("BakingPojo"));

                        mIngredientsTextView = (TextView) findViewById(R.id.tv_1);
                        ab.setTitle((mBakeryPojo.getName() + " "));

                        mIngredientsTextView.setText(mBakeryPojo.getIngredients().get(0).getIngredient()
                                + ", " + mBakeryPojo.getIngredients().get(1).getIngredient() + ", "
                                + mBakeryPojo.getIngredients().get(2).getIngredient() +
                                ", " + mBakeryPojo.getIngredients().get(3).getIngredient() +
                                ", " + mBakeryPojo.getIngredients().get(4).getIngredient() +
                                ", " + mBakeryPojo.getIngredients().get(5).getIngredient() +
                                ", " + mBakeryPojo.getIngredients().get(6).getIngredient() +
                                ", " + mBakeryPojo.getIngredients().get(7).getIngredient() +
                                ", " + mBakeryPojo.getIngredients().get(8).getIngredient());
                        mRecyclerView = (RecyclerView) findViewById(R.id.rv_steps_desc);

                        mBakeryStepAdapter = new BakeryStepAdapter(getApplicationContext(), mBakeryPojo, this);

                        mLinearLayoutManager = new LinearLayoutManager(this);

                        mRecyclerView.setAdapter(mBakeryStepAdapter);

                        mRecyclerView.setLayoutManager(mLinearLayoutManager);
                    }
                }
            }
    }
    @Override
    public void openVideo() {


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        return super.onOptionsItemSelected(item);
        int id = item.getItemId();

        switch (id)
        {
            case R.id.fav_menu:
                mMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        for(int i=0; i< mBakeryPojo.getIngredients().size(); i++) {

                            List<BakeryPojo.Ingredient> ingredients = mBakeryPojo.getIngredients();

                            Log.i("ingredientsSize", ingredients.size()+"");

                            Intent intent = new Intent(BakingDetailActivity.this, BakeryWidgetProvider.class);

                            intent.putExtra("homescreen_ingredient",Parcels.wrap(ingredients));

                            intent.setAction(BakeryWidgetProvider.UPDATE_MEETING_ACTION);

                            sendBroadcast(intent);
                        }
                        return true;
                    }
                });

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_baking, menu);
        mMenuItem = menu.findItem(R.id.fav_menu);
        return super.onCreateOptionsMenu(menu);

    }
}







