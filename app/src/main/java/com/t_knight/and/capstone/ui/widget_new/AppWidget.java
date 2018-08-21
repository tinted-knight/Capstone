package com.t_knight.and.capstone.ui.widget_new;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.t_knight.and.capstone.R;
import com.t_knight.and.capstone.model.helpers.AppPreferences;

import timber.log.Timber;

/**
 * Implementation of App Widget functionality.
 */
public class AppWidget extends AppWidgetProvider {

    static void updateAppWidgets(Context context, AppWidgetManager appWidgetManager,
                                 int[] appWidgetIds, String to, String from) {
        for (int appWidgetId : appWidgetIds)
            updateAppWidget(context, appWidgetManager, appWidgetId, to, from);
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, String to, String from) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
        views.setTextViewText(R.id.tv_to, to);
        views.setTextViewText(R.id.tv_from, from);

        Intent intentTo = new Intent(context, AppWidget.class);
        intentTo.setAction("to");
        views.setOnClickPendingIntent(R.id.tv_to, PendingIntent.getBroadcast(context, 0, intentTo, 0));

        Intent intentFrom = new Intent(context, AppWidget.class);
        intentFrom.setAction("from");
        views.setOnClickPendingIntent(R.id.tv_from, PendingIntent.getBroadcast(context, 0, intentFrom, 0));

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
//        for (int appWidgetId : appWidgetIds) {
//            updateAppWidget(context, appWidgetManager, appWidgetId);
//        }
        TopicWidgetService.startActionUpdate(context);
    }

    @Override public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if ("to".equals(intent.getAction())) {
            TopicWidgetService.getPreviousCard(context);
        } else if ("from".equals(intent.getAction())) {
            TopicWidgetService.getNextCard(context);
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

