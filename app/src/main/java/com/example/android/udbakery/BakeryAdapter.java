package com.example.android.udbakery;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.android.udbakery.Model.BakeryPojo;

import java.util.ArrayList;

/**
 * Created by SSubra27 on 7/7/17.
 */

public class BakeryAdapter extends RecyclerView.Adapter<BakeryAdapter.ViewHolder> {

    private static final String TAG = BakeryAdapter.class.getSimpleName();
    private Context mContext;
    private static ArrayList<BakeryPojo> mBakeryItemData;

    private onItemClickedInterface mOnItemClickedInterface;

    public BakeryAdapter(Context ct, ArrayList<BakeryPojo> bm, onItemClickedInterface on)
    {
        mContext = ct;
        mBakeryItemData = bm;
        mOnItemClickedInterface = on;
    }

    public interface onItemClickedInterface
    {


        void onItemClicked(int clickedListItem);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        int id = R.layout.adapter_baking_detail;
        boolean attachToParent = false;
        View v = inflater.inflate(id, parent, attachToParent);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BakeryPojo bakeryModel = mBakeryItemData.get(position);

        String recipeName = bakeryModel.getName();

        holder.mRecipeButton.setText(recipeName);

    }

    @Override
    public int getItemCount() {
        return mBakeryItemData.size();

    }





    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private Button mRecipeButton;

        public ViewHolder(View itemView) {
            super(itemView);
            mRecipeButton =itemView.findViewById(R.id.bt_recipe_one);
            mRecipeButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int itemClicked = getAdapterPosition();
            Log.i(TAG, String.valueOf(getAdapterPosition()));
            mOnItemClickedInterface.onItemClicked(itemClicked);

        }


    }



    public void setBakeryData(ArrayList<BakeryPojo> bakeryData)
    {
        mBakeryItemData = bakeryData;
        notifyDataSetChanged();
    }

}
