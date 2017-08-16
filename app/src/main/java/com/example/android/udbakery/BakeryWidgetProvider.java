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
import android.widget.TextView;

/**
 * Implementation of App Widget functionality.
 */
public class BakeryWidgetProvider extends AppWidgetProvider {
    public static final String UPDATE_MEETING_ACTION = "android.appwidget.action.APPWIDGET_UPDATE";

    private TextView mWidgetTextView;

//    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
//                                int appWidgetId) {
//
//
//        // Construct the RemoteViews object
//        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.bakery_widget_provider);
//
//        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
////        views.setTextViewText(R.id.tv_widget, pref.getString("IG", " "));
//
//        ArrayList<String> mIngredients = pref.getStringSet("IG", "");
//
////        views.setRemoteAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, tv_widget), mIngredients);
//
//        Intent intent = new Intent(context, BakeryDialogActivity.class);
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,intent, 0);
//
//        views.setOnClickPendingIntent(R.id.iv_items, pendingIntent);
//
//
//        // Instruct the widget manager to update the widget
//        appWidgetManager.updateAppWidget(appWidgetId, views);
//    }

    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager mgr = AppWidgetManager.getInstance(context);
        if(intent.getAction().equals(UPDATE_MEETING_ACTION))
        {
            int appWidgetIds[] = mgr.getAppWidgetIds(new ComponentName(context, BakeryWidgetProvider.class));
            Log.e("received", intent.getAction());
            mgr.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.list_view);
        }
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int i =0; i<appWidgetIds.length; i++) {
            Log.i("appWdgetIds", appWidgetIds.length+"");

            Intent intent = new Intent(context, ListViewWIdgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);

            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.bakery_widget_provider);
            rv.setRemoteAdapter(appWidgetIds[i], R.id.list_view, intent);
            Intent startActivityIntent = new Intent(context, BakingActivity.class);
            PendingIntent pd = PendingIntent.getActivity(context, 0, startActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            rv.setPendingIntentTemplate(R.id.list_view, pd);
            appWidgetManager.updateAppWidget(appWidgetIds[i], rv);
            super.onUpdate(context, appWidgetManager, appWidgetIds);

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

