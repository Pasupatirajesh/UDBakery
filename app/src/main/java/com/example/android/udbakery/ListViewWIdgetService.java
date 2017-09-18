package com.example.android.udbakery;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by SSubra27 on 8/15/17.
 */

public class ListViewWIdgetService extends RemoteViewsService {
    public static final String WIDGET_STRING = "com.example.android.udbakery.ListViewWidgetService";

    @Override
    public RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent intent) {
        int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        return new ListViewsRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}
        class ListViewsRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory
        {
            private Context mContext;

            private List<String> mIngredients ;

            private int appWidgetId;

            Set<String> ingredients;

            public ListViewsRemoteViewsFactory(Context applicationContext, Intent intent) {

                this.mContext = applicationContext.getApplicationContext();
                appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
            }

            @Override
            public void onCreate() {
            }

            @Override
            public void onDataSetChanged() {
                SharedPreferences settings;

                settings = mContext.getSharedPreferences(BakingDetailActivity.PREFS_NAME, Context.MODE_PRIVATE);
                if(settings.contains(BakeryWidgetProvider.UPDATE_MEETING_ACTION) && settings!=null)
                {
                   ingredients = settings.getStringSet(BakeryWidgetProvider.UPDATE_MEETING_ACTION, null);
                    if(ingredients == null)
                    {
                        mIngredients = new ArrayList<>();
                    }else
                    {
                        mIngredients = new ArrayList<>(ingredients);
                    }

                } else

                {
                    ingredients = Collections.emptySet();
                    mIngredients = new ArrayList<>(ingredients);
                }
            }

            @Override
            public void onDestroy() {

            }

            @Override
            public int getCount() {

                return mIngredients.size() ;
            }
            @Override
            public RemoteViews getViewAt(int i) {

                RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);

                rv.setTextViewText(R.id.item, mIngredients.get(i));

                Intent fillIntent = new Intent();

                Bundle extras  = new Bundle();

                extras.putString(BakeryWidgetProvider.UPDATE_MEETING_ACTION, mIngredients.get(i));

                fillIntent.putExtras(extras);

                rv.setOnClickFillInIntent(R.id.item, fillIntent);

                return rv;

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


