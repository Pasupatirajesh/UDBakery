package com.example.android.udbakery;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.android.udbakery.Model.BakeryPojo;

import org.parceler.Parcels;

public class BakingDetailActivity extends AppCompatActivity {


    private static final String TAG = BakingDetailActivity.class.getSimpleName() ;
    public static BakeryPojo mBakeryPojo;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private TextView mIngredientsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baking_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();

        ab.setDisplayHomeAsUpEnabled(true);

        Bundle myIntent = getIntent().getExtras();

        if (myIntent != null) {
            if (myIntent.getParcelable("BakeryDataArrayList") != null) {
                mBakeryPojo = Parcels.unwrap(myIntent.getParcelable("BakeryDataArrayList"));

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

                mLinearLayoutManager = new LinearLayoutManager(this);

                mRecyclerView.setLayoutManager(mLinearLayoutManager);

                mRecyclerView.setAdapter(new BakeryStepAdapter(this, mBakeryPojo));
            }
        }
    }
}




