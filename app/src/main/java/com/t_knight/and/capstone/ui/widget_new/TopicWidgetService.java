package com.t_knight.and.capstone.ui.widget_new;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.LiveData;
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

    public static final String ACTION_UPDATE_WIDGET = "com.t_knight.and.capstone.update_widget2";
    public static final String ACTION_CARD_NEXT = "com.t_knight.and.capstone.card_next";
    public static final String ACTION_CARD_PREVIOUS = "com.t_knight.and.capstone.card_previous";

    public TopicWidgetService() {
        super(TopicWidgetService.class.getSimpleName());
    }

    public static void startActionUpdate(Context context) {
        Intent intent = new Intent(context, TopicWidgetService.class);
        intent.setAction(ACTION_UPDATE_WIDGET);
        context.startService(intent);
    }

    public static void getNextCard(Context context) {
        Intent intent = new Intent(context, TopicWidgetService.class);
        intent.setAction(ACTION_CARD_NEXT);
        context.startService(intent);
    }

    public static void getPreviousCard(Context context) {
        Intent intent = new Intent(context, TopicWidgetService.class);
        intent.setAction(ACTION_CARD_PREVIOUS);
        context.startService(intent);
    }

    @Override protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null && intent.getAction() != null) {
            switch (intent.getAction()) {
                case ACTION_CARD_NEXT: {
                    updateWidgetCardNext();
                    break;
                }
                case ACTION_CARD_PREVIOUS: {
                    updateWidgetCardPrevious();
                    break;
                }
                case ACTION_UPDATE_WIDGET: {
                    updateWidgetDefault();
                    break;
                }
                default: {
                    updateWidgetDefault();
                    break;
                }
            }
        }
    }

    private void updateWidgetCardPrevious() {
        AppPreferences prefs = new AppPreferences(this);
        int cardId = prefs.getCardId();
        if (updateWidgetCard(cardId - 1))
            prefs.setCardId(cardId - 1);
    }

    private void updateWidgetCardNext() {
        AppPreferences prefs = new AppPreferences(this);
        int cardId = prefs.getCardId();
        if (updateWidgetCard(cardId + 1))
            prefs.setCardId(cardId + 1);
    }

    private boolean updateWidgetCard(int cardId) {
        TopicListRepo repo = new TopicListRepo(getApplicationContext());
        ReadCardEntity cardEntity = repo.getCard(cardId);
        if (cardEntity != null) {
            Timber.i(cardEntity.to);
            updateWidget(cardEntity.to, cardEntity.from);
            return true;
        }
        return false;
    }

    private void updateWidgetDefault() {
        TopicListRepo repo = new TopicListRepo(getApplicationContext());
        ReadCardEntity cardEntity = repo.getFirstCard();
        if (cardEntity != null) {
            new AppPreferences(this).setCardId(cardEntity.id);
            updateWidget(cardEntity.to, cardEntity.from);
        } else {
            updateWidget("tap to update", "");
        }
    }

    private void updateWidget(String to, String from) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                new ComponentName(this, AppWidget.class));
        AppWidget.updateAppWidgets(this, appWidgetManager, appWidgetIds, to, from);
    }

}
