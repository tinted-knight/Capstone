package com.t_knight.and.capstone.local_db;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.t_knight.and.capstone.AppExecutors;
import com.t_knight.and.capstone.model.SingleCard;
import com.t_knight.and.capstone.model.Topic;
import com.t_knight.and.capstone.model.TopicDescription;

import java.util.ArrayList;
import java.util.List;

public class TopicListRepo {

    private TopicsDao dao;

    public TopicListRepo(Context context) {
        dao = AppDatabase.getInstance(context).topicsDao();
    }

    public void fillTopicList(final List<TopicDescription> topicList) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override public void run() {
                List<TopicEntity> entities;
                entities = dao.getAllForWidget();
                if (entities != null && entities.size() > 0) {
                    dao.clear(entities.toArray(new TopicEntity[]{}));
                }

                entities = new ArrayList<>(topicList.size());
                for (TopicDescription topic : topicList) {
                    entities.add(new TopicEntity(topic));
                }

                dao.insertTopic(entities.toArray(new TopicEntity[]{}));
            }
        });
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

    public void pinTopicRead(final Topic topic) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override public void run() {
                List<ReadCardEntity> cardEntities = new ArrayList<>();
                for (SingleCard card : topic.getCardContent()) {
                    cardEntities.add(new ReadCardEntity(card));
                }
                dao.clearCards();
                dao.insertReadCards(cardEntities.toArray(new ReadCardEntity[]{}));
            }
        });
    }

    public LiveData<List<TopicEntity>> getAll() {
        return dao.getAll();
    }

    public ReadCardEntity getCard(int id) {
        return dao.getCard(id);
    }

    public ReadCardEntity getFirstCard() {
        return dao.getFirstCard();
    }

    public LiveData<ReadCardEntity> widgetUpdate() {
        return dao.widgetUpdate();
    }
}
