package com.t_knight.and.capstone.ui.widget_new;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.t_knight.and.capstone.R;
import com.t_knight.and.capstone.local_db.ReadCardEntity;
import com.t_knight.and.capstone.local_db.TopicListRepo;
import com.t_knight.and.capstone.model.helpers.AppPreferences;

import timber.log.Timber;

public class TopicWidgetService extends IntentService {

    private static final String ACTION_UPDATE_WIDGET = "com.t_knight.and.capstone.update_app_widget";

    public TopicWidgetService() {
        super(TopicWidgetService.class.getSimpleName());
    }

    public static void startActionUpdate(Context context) {
        Intent intent = new Intent(context, TopicWidgetService.class);
        intent.setAction(ACTION_UPDATE_WIDGET);
        context.startService(intent);
    }

    @Override protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null && intent.getAction() != null
                && intent.getAction().equals(ACTION_UPDATE_WIDGET)) {
            updateWidget();
        }
    }

    private void updateWidget() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                new ComponentName(this, AppWidget.class));
        Timber.i("updateWidget >> ");
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.view_flipper);
        AppWidget.updateWidgets(this, appWidgetIds, appWidgetManager);
//        AppWidget.updateAppWidgets(this, appWidgetManager, appWidgetIds);
    }

}
