package com.example.android.udbakery;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class BakingVideosActivity extends AppCompatActivity {

//    private boolean mTwoPane;
//    public static BakeryPojo mBakeryPojo;
//    private RecyclerView mRecyclerView;
//    private LinearLayoutManager mLinearLayoutManager;
//    private TextView mIngredientsTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baking_videos);

        ActionBar ab = getSupportActionBar();

        ab.setDisplayHomeAsUpEnabled(true);

        ab.setTitle(R.string.fragment_actionbar_title);


//            Bundle myIntent = getIntent().getExtras();
//
//            if (myIntent != null) {
//                if (myIntent.getParcelable("BakeryDataArrayList") != null) {
//                    mBakeryPojo = Parcels.unwrap(myIntent.getParcelable("BakeryDataArrayList"));
//
//                    ab.setTitle((mBakeryPojo.getName() + " "));
//                    mIngredientsTextView = (TextView) findViewById(R.id.tv_1);
//
//                    mIngredientsTextView.setText(mBakeryPojo.getIngredients().get(0).getIngredient()
//                            + ", " + mBakeryPojo.getIngredients().get(1).getIngredient() + ", "
//                            + mBakeryPojo.getIngredients().get(2).getIngredient() +
//                            ", " + mBakeryPojo.getIngredients().get(3).getIngredient() +
//                            ", " + mBakeryPojo.getIngredients().get(4).getIngredient() +
//                            ", " + mBakeryPojo.getIngredients().get(5).getIngredient() +
//                            ", " + mBakeryPojo.getIngredients().get(6).getIngredient() +
//                            ", " + mBakeryPojo.getIngredients().get(7).getIngredient() +
//                            ", " + mBakeryPojo.getIngredients().get(8).getIngredient());
//
//                    mRecyclerView = (RecyclerView) findViewById(R.id.rv_steps_desc);
//
//                    mLinearLayoutManager = new LinearLayoutManager(this);
//
//                    mRecyclerView.setLayoutManager(mLinearLayoutManager);
//
//                    mRecyclerView.setAdapter(new BakeryStepAdapter(this, mBakeryPojo));
//                }
//            }
//
//                    FragmentManager fm = getSupportFragmentManager();
//                    Fragment fragment = fm.findFragmentById(R.id.fragment);
//                    if (fragment==null)
//                    {
//                        fragment =new  BakeryVideoFragment();
//                        fm.beginTransaction().add(R.id.fragment,fragment).commit();
//                    }
//
//                } else
//        {
//            mTwoPane=false;
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
