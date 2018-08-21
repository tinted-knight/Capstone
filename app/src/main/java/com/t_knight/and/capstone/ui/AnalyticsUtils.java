package com.t_knight.and.capstone.ui;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.t_knight.and.capstone.local_db.TopicEntity;

public class AnalyticsUtils {

    public static void logTopicPinned(Context context, TopicEntity topic) {
        Bundle bundle = new Bundle();
        bundle.putString(Param.ITEM_ID, String.valueOf(topic.topicId));
        bundle.putString(Param.ITEM_NAME, topic.titleTo);

        FirebaseAnalytics.getInstance(context).logEvent("topic_pinned", bundle);
    }

}
