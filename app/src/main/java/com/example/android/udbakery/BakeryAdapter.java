package com.example.android.udbakery;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.android.udbakery.Model.BakeryModel;

import java.util.ArrayList;

/**
 * Created by SSubra27 on 7/7/17.
 */

public class BakeryAdapter extends RecyclerView.Adapter<BakeryAdapter.ViewHolder> {

    private Context mContext;
    private static ArrayList<BakeryModel> mBakeryItemData;

    public BakeryAdapter(Context ct, ArrayList<BakeryModel> bm)
    {
        mContext = ct;
        mBakeryItemData = bm;
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
        BakeryModel bakeryModel = mBakeryItemData.get(position);

        String recipeName = bakeryModel.getRecipeName();

        holder.mRecipeButton.setText(recipeName);

    }

    @Override
    public int getItemCount() {
        return mBakeryItemData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private Button mRecipeButton;

        public ViewHolder(View itemView) {
            super(itemView);
            mRecipeButton =(Button) itemView.findViewById(R.id.bt_recipe_one);

        }
    }

    public void setBakeryData(ArrayList<BakeryModel> bakeryData)
    {
        mBakeryItemData = bakeryData;
        notifyDataSetChanged();
    }
}
