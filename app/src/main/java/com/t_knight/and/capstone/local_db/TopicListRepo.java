package com.t_knight.and.capstone.local_db;

import android.content.Context;

import com.t_knight.and.capstone.AppExecutors;
import com.t_knight.and.capstone.model.TopicList;
import com.t_knight.and.capstone.model.TopicTitle;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class TopicListRepo {

    private TopicsDao dao;

//    private static TopicListRepo instance;

    public TopicListRepo(Context context) {
        dao = AppDatabase.getInstance(context).topicsDao();
    }

//    public static TopicListRepo get(Context context) {
//        if (instance == null) {
//            synchronized (TopicListRepo.class) {
//                if (instance == null) {
//                    instance = new TopicListRepo(context);
//                }
//            }
//        }
//        return instance;
//    }

    public void fillTopicList(final TopicList topicList) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override public void run() {
                List<TopicEntity> entities;
                entities = dao.getAll();
                if (entities != null && entities.size() > 0)
                    dao.clear(entities.toArray(new TopicEntity[]{}));

                entities = new ArrayList<>(topicList.size());
                Timber.tag("fillTopicList");
                for (TopicTitle topic : topicList) {
                    entities.add(new TopicEntity(topic));
                    Timber.i(topic.getTitleFrom());
                }

                dao.insertTopic(entities.toArray(new TopicEntity[]{}));
            }
        });
    }
//    public void fillTopicList(final List<TopicEntity> data) {
//        AppExecutors.getInstance().diskIO().execute(new Runnable() {
//            @Override public void run() {
//                dao.insertTopic(data.toArray(new TopicEntity[]{}));
//            }
//        });
//    }

}
