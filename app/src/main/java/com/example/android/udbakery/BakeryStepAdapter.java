package com.example.android.udbakery;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.udbakery.Model.BakeryPojo;

import org.parceler.Parcels;

/**
 * Created by SSubra27 on 7/25/17.
 */

public class BakeryStepAdapter extends RecyclerView.Adapter<BakeryStepAdapter.ViewHolder> {

    private static final String TAG = BakeryStepAdapter.class.getSimpleName();
    private Context mContext;
    private BakeryPojo mBakeryPojo;
    private Parcelable mWrapper;
    private Parcelable mBakingWrapper;
    public  BakeryStepAdapter(Context ct, BakeryPojo bakeryPojo)
    {
        this.mContext = ct;
        this.mBakeryPojo = bakeryPojo;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.content_baking_detail, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.i(TAG,getItemId(position)+"");

        holder.mStepsTextView.setText(mBakeryPojo.getSteps().get(position).getShortDescription());

    }

    @Override
    public int getItemCount() {
        return mBakeryPojo.getSteps().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView mStepsTextView;


        public ViewHolder(View itemView) {
            super(itemView);

            mStepsTextView = itemView.findViewById(R.id.tv_2);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent =new Intent(mContext, BakingVideosActivity.class);

            mWrapper = Parcels.wrap(mBakeryPojo.getSteps().get(getAdapterPosition()).getVideoURL());

            mBakingWrapper = Parcels.wrap(mBakeryPojo.getSteps().get(getAdapterPosition()).getDescription());

            Log.i(TAG, getAdapterPosition()+"");

            intent.putExtra("BakingPojo", mWrapper);
            intent.putExtra("BakingDescPojo", mBakingWrapper);

            mContext.startActivity(intent);
        }
    }
}
