package com.t_knight.and.capstone.ui.widget_new;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.t_knight.and.capstone.local_db.TopicEntity;
import com.t_knight.and.capstone.local_db.TopicListRepo;
import com.t_knight.and.capstone.ui.widget.TopicListWidget;

public class TopicWidgetService extends IntentService {

    public static final String ACTION_UPDATE_WIDGET = "com.t_knight.and.capstone.update_widget2";

    public TopicWidgetService() {
        super(TopicWidgetService.class.getSimpleName());
    }

    public static void startActionUpdate(Context context) {
        Intent intent = new Intent(context, TopicWidgetService.class);
        intent.setAction(ACTION_UPDATE_WIDGET);
        context.startService(intent);
    }

    @Override protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null && ACTION_UPDATE_WIDGET.equals(intent.getAction())) {
            updateWidget();
        }
    }

    private void updateWidget() {
        TopicListRepo repo = new TopicListRepo(getApplicationContext());
        TopicEntity topic = repo.getPinned();

        String title = (topic != null) ? topic.titleFrom : "xxx";

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                new ComponentName(this, AppWidget.class));

        AppWidget.updateAppWidgets(this, appWidgetManager, appWidgetIds, title);
    }

}
