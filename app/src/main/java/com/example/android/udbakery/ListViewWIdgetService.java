package com.example.android.udbakery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SSubra27 on 8/15/17.
 */

public class ListViewWIdgetService extends RemoteViewsService {
    @Override
    public RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListViewsRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}


        class ListViewsRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory
        {
            private Context mContext;

            private List<String> mIngredients;

            private Bundle myBundle;

            public ListViewsRemoteViewsFactory(Context applicationContext, Intent intent) {

                this.mContext = applicationContext;

                myBundle = intent.getExtras();

            }

            @Override
            public void onCreate() {

                mIngredients = new ArrayList<String>();
            }

            @Override
            public void onDataSetChanged() {
//                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mContext);
//                pref.getString("homescreen_ingredient", "")

                mIngredients = myBundle.getParcelable("homescreen_ingredient");


            }

            @Override
            public void onDestroy() {
                mIngredients.clear();
            }

            @Override
            public int getCount() {
//                return mIngredients.size();
                return 5;
            }

            @Override
            public RemoteViews getViewAt(int i) {

//                RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
//
////                String data = mIngredients.get(i);
//
//                rv.setTextViewText(R.id.item, data);
//
//                Bundle extras  = new Bundle();
//
//                extras.putInt("IG", i);
//
//                Intent fillIntent = new Intent();
//
//                fillIntent.putExtra("homescreen_ingredient", data);
//
//                fillIntent.putExtras(extras);
//
//                rv.setOnClickFillInIntent(R.id.item, fillIntent);
//
//                return rv;
                return null;

            }

            @Override
            public RemoteViews getLoadingView() {
                return null;
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }


        }


