package com.t_knight.and.capstone.ui.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.t_knight.and.capstone.R;
import com.t_knight.and.capstone.local_db.TopicEntity;
import com.t_knight.and.capstone.local_db.TopicListRepo;

import java.util.List;

import timber.log.Timber;

public class TopicListRemoteViewsService extends RemoteViewsService {

    @Override public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new TopicListRemoteViewsFactory(this.getApplicationContext());
    }

}

class TopicListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;

    private List<TopicEntity> data;

    TopicListRemoteViewsFactory(Context appContext) {
        this.context = appContext;
    }

    @Override public void onCreate() {

    }

    @Override public void onDataSetChanged() {
        Timber.i("onDataSetChanged");
        TopicListRepo repo = new TopicListRepo(context);
        data = repo.getAllForWidget();
    }

    @Override public void onDestroy() {

    }

    @Override public int getCount() {
        Timber.i("getCount");
        if (data == null) return 0;
        return data.size();
    }

    @Override public RemoteViews getViewAt(int position) {
        Timber.i("getViewAt");
        if (data == null || data.size() == 0) return null;

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_item);
        TopicEntity item = data.get(position);
        views.setTextViewText(R.id.tv_item, item.titleFrom);
        Timber.i(item.titleFrom);
        return views;
    }

    @Override public RemoteViews getLoadingView() {
        return null;
    }

    @Override public int getViewTypeCount() {
        return 1;
    }

    @Override public long getItemId(int position) {
        return position;
    }

    @Override public boolean hasStableIds() {
        return true;
    }
}
