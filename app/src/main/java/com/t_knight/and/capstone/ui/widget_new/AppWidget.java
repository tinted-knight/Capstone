package com.t_knight.and.capstone.ui.widget_new;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.t_knight.and.capstone.R;

public class AppWidget extends AppWidgetProvider {

    public static final String SELF_ACTION_NEXT = "action_next";
    public static final String SELF_ACTION_PREVIOUS = "action_previous";
    public static final String SELF_ACTION_UPDATE = "action_update";

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

        // set next and prev buttons click listeners
        views.setOnClickPendingIntent(R.id.btn_next,
                getSelfPendignIntent(context, SELF_ACTION_NEXT));
        views.setOnClickPendingIntent(R.id.btn_previous,
                getSelfPendignIntent(context, SELF_ACTION_PREVIOUS));
        // update widget when user taps it
        views.setOnClickPendingIntent(R.id.tv_to,
                getSelfPendignIntent(context, SELF_ACTION_UPDATE));
        views.setOnClickPendingIntent(R.id.tv_from,
                getSelfPendignIntent(context, SELF_ACTION_UPDATE));

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static PendingIntent getSelfPendignIntent(Context context, String action) {
        Intent intentTo = new Intent(context, AppWidget.class);
        intentTo.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intentTo, 0);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        TopicWidgetService.startActionUpdate(context);
    }

    @Override public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (SELF_ACTION_PREVIOUS.equals(intent.getAction())) {
            TopicWidgetService.getPreviousCard(context);
        } else if (SELF_ACTION_NEXT.equals(intent.getAction())) {
            TopicWidgetService.getNextCard(context);
        } else if (SELF_ACTION_UPDATE.equals(intent.getAction())) {
            TopicWidgetService.startActionUpdate(context);
        }
    }

}

