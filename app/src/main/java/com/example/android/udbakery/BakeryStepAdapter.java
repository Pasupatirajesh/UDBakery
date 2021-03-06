package com.example.android.udbakery;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
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
    private Parcelable mIdWrapper;
    private nextVideoInterface mNextVideoInterface;
    public  BakeryStepAdapter(Context ct, BakeryPojo bakeryPojo, nextVideoInterface nextVideoInterface)
    {
        this.mContext = ct;
        this.mBakeryPojo = bakeryPojo;
        this.mNextVideoInterface = nextVideoInterface;
    }

    public interface nextVideoInterface
    {
         void openVideo();
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
            Configuration config = mContext.getResources().getConfiguration();
            Intent intent = null;
            if(config.smallestScreenWidthDp >=600)
            {
                 intent =new Intent(mContext, BakingDetailActivity.class);
            } else
            {
                intent = new Intent(mContext, BakingVideosActivity.class);
            }
            mWrapper = Parcels.wrap(mBakeryPojo);

            mBakingWrapper = Parcels.wrap(mBakeryPojo.getSteps().get(getAdapterPosition()).getDescription());

            mIdWrapper = Parcels.wrap(mBakeryPojo);

            Log.i(TAG, getAdapterPosition()+"");
            intent.putExtra("BakingPojo", mWrapper);
            intent.putExtra("BakingDescPojo", mBakingWrapper);
            intent.putExtra("BakingIdPojo", mIdWrapper);
            intent.putExtra("BakingPosId", getAdapterPosition());

            mContext.startActivity(intent);

            mNextVideoInterface.openVideo();
        }
    }
}
