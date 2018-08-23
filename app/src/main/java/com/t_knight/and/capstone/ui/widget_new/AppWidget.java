package com.t_knight.and.capstone.ui.widget_new;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.t_knight.and.capstone.R;

import timber.log.Timber;

public class AppWidget extends AppWidgetProvider {

    private static final String SELF_ACTION_NEXT = "com.t_knight.and.capstone.action_next";
    private static final String SELF_ACTION_PREVIOUS = "com.t_knight.and.capstone.action_previous";

    public static void updateWidgets(Context context, int[] appWidgetIds,
                                      AppWidgetManager appWidgetManager) {
        for (int appWidgetId : appWidgetIds)
            updateAppWidget(context, appWidgetManager, appWidgetId);
    }

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                        int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
        Intent intent = new Intent(context, TopicListWidgetService.class);
        views.setRemoteAdapter(R.id.view_flipper, intent);
        Timber.i("update App Widget >>");
        views.setDisplayedChild(R.id.view_flipper, 0);

        views.setOnClickPendingIntent(R.id.btn_next,
                getSelfPendingIntent(context, SELF_ACTION_NEXT, appWidgetId));
        views.setOnClickPendingIntent(R.id.btn_previous,
                getSelfPendingIntent(context, SELF_ACTION_PREVIOUS, appWidgetId));

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static PendingIntent getSelfPendingIntent(Context context, String action, int widgetId) {
        Intent intentTo = new Intent(context, AppWidget.class);
        intentTo.setAction(action);
        intentTo.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        return PendingIntent.getBroadcast(context, 0, intentTo, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        updateWidgets(context, appWidgetIds, appWidgetManager);
        Timber.i("onUpdate >>");
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override public void onReceive(Context context, Intent intent) {
        if (SELF_ACTION_PREVIOUS.equals(intent.getAction())) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
            views.showPrevious(R.id.view_flipper);
            AppWidgetManager.getInstance(context).updateAppWidget(
                    intent.getIntExtra(
                            AppWidgetManager.EXTRA_APPWIDGET_ID,
                            AppWidgetManager.INVALID_APPWIDGET_ID
                    ),
                    views
            );
        } else if (SELF_ACTION_NEXT.equals(intent.getAction())) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
            views.showNext(R.id.view_flipper);
            AppWidgetManager.getInstance(context).updateAppWidget(
                    intent.getIntExtra(
                            AppWidgetManager.EXTRA_APPWIDGET_ID,
                            AppWidgetManager.INVALID_APPWIDGET_ID
                    ),
                    views

            );
        }
        super.onReceive(context, intent);
    }

}