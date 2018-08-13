package com.t_knight.and.capstone.ui.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.t_knight.and.capstone.R;
import com.t_knight.and.capstone.local_db.TopicEntity;
import com.t_knight.and.capstone.local_db.TopicListRepo;

import java.util.List;

import timber.log.Timber;

public class TopicListWidgetService extends IntentService {

    public static final String ACTION_UPDATE_WIDGET = "com.t_knight.and.capstone.update_widget";
    public static final String ACTION_UPDATE_LIST = "com.t_knight.and.capstone.update_list";

    public TopicListWidgetService() {
        super("TopicListWidgetService");
    }

    public static void startActionUpdateWidget(Context context) {
        Intent intent = new Intent(context, TopicListWidgetService.class);
        intent.setAction(ACTION_UPDATE_WIDGET);
        context.startService(intent);
    }

    public static void startActionUpdateList(Context context) {
        Intent intent = new Intent(context, TopicListWidgetService.class);
        intent.setAction(ACTION_UPDATE_LIST);
        context.startService(intent);
    }

    @Override protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            if (ACTION_UPDATE_WIDGET.equals(action)) {
                doActionUpdateWidget();
            } else if (ACTION_UPDATE_LIST.equals(action)) {
                doActionUpdateList();
            }
        }
    }

    private void doActionUpdateList() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                new ComponentName(this, TopicListWidget.class));
        Timber.i("doActionUpdateList");
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.lv_topic_list);
        TopicListWidget.updateAppWidgetsList(this, appWidgetManager, appWidgetIds);
    }

    private void doActionUpdateWidget() {
        TopicListRepo repo = new TopicListRepo(getApplicationContext());
        List<TopicEntity> data = repo.getAll();

        StringBuilder sb = new StringBuilder();
        for (TopicEntity entity : data) {
            sb.append(entity.titleFrom);
            sb.append("/n/n");
        }

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                new ComponentName(this, TopicListWidget.class));

        TopicListWidget.updateAppWidgets(this, appWidgetManager, appWidgetIds, sb.toString());
    }

}
