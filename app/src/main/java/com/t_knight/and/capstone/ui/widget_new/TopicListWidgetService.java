package com.t_knight.and.capstone.ui.widget_new;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.t_knight.and.capstone.R;
import com.t_knight.and.capstone.local_db.ReadCardEntity;
import com.t_knight.and.capstone.local_db.TopicListRepo;

import java.util.List;

public class TopicListWidgetService extends RemoteViewsService {

    @Override public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new TopicListRemoteViewFactory(getApplicationContext());
    }

}

class TopicListRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private List<ReadCardEntity> data;

    TopicListRemoteViewFactory(Context context) {
        this.context = context;
    }

    @Override public void onCreate() {
    }

    @Override public void onDataSetChanged() {
        TopicListRepo repo = new TopicListRepo(context);
        data = repo.getAllCards();
    }

    @Override public void onDestroy() {

    }

    @Override public int getCount() {
        if (data == null) return 0;
        return data.size();
    }

    @Override public RemoteViews getViewAt(int position) {
        if (data == null || data.size() == 0 || data.get(position) == null) return null;

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.item_widget);
        ReadCardEntity card = data.get(position);
        views.setTextViewText(R.id.tv_to, card.to);
        views.setTextViewText(R.id.tv_from, card.from);
        String counter = position + 1 + " of " + data.size();
        views.setTextViewText(R.id.tv_counter, counter);

        return views;
    }

    @Override public RemoteViews getLoadingView() {
        return null;
    }

    @Override public int getViewTypeCount() {
        return data.size();
    }

    @Override public long getItemId(int position) {
        return position;
    }

    @Override public boolean hasStableIds() {
        return true;
    }
}
