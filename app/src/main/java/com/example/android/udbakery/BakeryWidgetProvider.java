package com.example.android.udbakery;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import org.parceler.Parcels;

import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 */
public class BakeryWidgetProvider extends AppWidgetProvider {
    public static final String UPDATE_MEETING_ACTION = "android.appwidget.action.APPWIDGET_UPDATE";

    public static ArrayList<String> mIngredients ;

    public Intent myIntent = null;

    @Override
    public void onReceive(Context context, Intent intent) {

        this.myIntent = intent;

        mIngredients = Parcels.unwrap(myIntent.getParcelableExtra(UPDATE_MEETING_ACTION));

        AppWidgetManager mgr = AppWidgetManager.getInstance(context.getApplicationContext());

        Toast.makeText(context.getApplicationContext(), "onReceive Called", Toast.LENGTH_SHORT).show();

        int appWidgetIds[] = mgr.getAppWidgetIds(new ComponentName(context.getApplicationContext(), BakeryWidgetProvider.class));


        mgr.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.list_view);

        onUpdate(context.getApplicationContext(), mgr ,appWidgetIds);


        super.onReceive(context.getApplicationContext(), intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of themfor(int i =0; i< mIngredients.size(); i++)

        for (int i =0; i<appWidgetIds.length; i++) {
            Log.i("appWdgetIds", appWidgetIds.length+"");

            Intent intent = new Intent(context.getApplicationContext(), ListViewWIdgetService.class);
            intent.putExtra(ListViewWIdgetService.WIDGET_STRING, Parcels.wrap(mIngredients));

            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);


            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            RemoteViews rv = new RemoteViews(context.getApplicationContext().getPackageName(), R.layout.bakery_widget_provider);
            rv.setRemoteAdapter(appWidgetIds[i], R.id.list_view, intent);


            Intent startActivityIntent = new Intent(context.getApplicationContext(), BakingActivity.class);
            PendingIntent pd = PendingIntent.getActivity(context.getApplicationContext(), 0, startActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            rv.setPendingIntentTemplate(R.id.list_view, pd);
            appWidgetManager.updateAppWidget(appWidgetIds[i], rv);
            super.onUpdate(context.getApplicationContext(), appWidgetManager, appWidgetIds);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

