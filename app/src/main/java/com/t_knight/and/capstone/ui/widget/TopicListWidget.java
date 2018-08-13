package com.t_knight.and.capstone.ui.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.t_knight.and.capstone.AppExecutors;
import com.t_knight.and.capstone.R;
import com.t_knight.and.capstone.local_db.TopicEntity;
import com.t_knight.and.capstone.local_db.TopicListRepo;

import java.util.List;

import timber.log.Timber;

/**
 * Implementation of App Widget functionality.
 */
public class TopicListWidget extends AppWidgetProvider {

    static void updateAppWidgets(Context context, AppWidgetManager appWidgetManager,
                                 int[] appWidgetIds, String data) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, data);
        }
    }

    static void updateAppWidgetsList(Context context, AppWidgetManager appWidgetManager,
                                 int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidgetList(context, appWidgetManager, appWidgetId);
        }
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, String widgetText) {
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.topic_list_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        Timber.i("onUpdate");
        for (int appWidgetId : appWidgetIds) {
            updateAppWidgetList(context, appWidgetManager, appWidgetId);
        }
//        TopicListWidgetService.startActionUpdateList(context);
    }

    private static void updateAppWidgetList(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        Timber.i("updateAppWidgetList");
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_list);
        Intent intent = new Intent(context, TopicListRemoteViewsService.class);
        views.setRemoteAdapter(R.id.lv_topic_list, intent);
        views.setEmptyView(R.id.lv_topic_list, R.id.layout_empty);

        appWidgetManager.updateAppWidget(appWidgetId, views);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.lv_topic_list);
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

