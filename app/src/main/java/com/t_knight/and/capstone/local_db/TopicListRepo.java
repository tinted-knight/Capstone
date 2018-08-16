package com.t_knight.and.capstone.local_db;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.t_knight.and.capstone.AppExecutors;
import com.t_knight.and.capstone.model.TopicDescription;

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

    public void fillTopicList(final List<TopicDescription> topicList) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override public void run() {
                List<TopicEntity> entities;
                entities = dao.getAllForWidget();
                if (entities != null && entities.size() > 0) {
                    dao.clear(entities.toArray(new TopicEntity[]{}));
                    Timber.i("dao clear");
                }

                entities = new ArrayList<>(topicList.size());
                for (TopicDescription topic : topicList) {
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

    public List<TopicEntity> getAllForWidget() {
        return dao.getAllForWidget();
    }

    public LiveData<List<TopicEntity>> getAll() {
        return dao.getAll();
    }

    public void pinTopic(final TopicEntity topicEntity) {
        topicEntity.pinned = true;
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override public void run() {
                dao.unpinAll();
                dao.updateTopic(topicEntity);
            }
        });
    }
}
