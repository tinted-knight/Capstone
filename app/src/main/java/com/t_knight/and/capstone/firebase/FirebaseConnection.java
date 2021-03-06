package com.t_knight.and.capstone.firebase;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.t_knight.and.capstone.R;
import com.t_knight.and.capstone.local_db.ReadCardEntity;
import com.t_knight.and.capstone.local_db.TopicEntity;
import com.t_knight.and.capstone.local_db.TopicListRepo;
import com.t_knight.and.capstone.model.TopicList;
import com.t_knight.and.capstone.model.Topic;
import com.t_knight.and.capstone.model.TopicDescription;
import com.t_knight.and.capstone.model.quiz.Quiz;

import java.util.List;

public class FirebaseConnection {

    private final String KEY_TOPIC_LIST;
    private final String KEY_TOPICS_CONTENT;
    private final String KEY_QUIZES_CONTENT;

    private final DatabaseReference dbRef;

    private final MutableLiveData<TopicList> topicList;
    //Firebase
    private static FirebaseConnection instance;
    // Local
    private final TopicListRepo local;

    public static FirebaseConnection getInstance(Context context) {
        if (instance == null)
            instance = new FirebaseConnection(context);

        return instance;
    }

    private FirebaseConnection(Context context) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.setPersistenceEnabled(true);
        dbRef = database.getReference();
        dbRef.keepSynced(true);
        local = new TopicListRepo(context);
        topicList = new MutableLiveData<>();
        KEY_TOPIC_LIST = context.getString(R.string.key_topics_list);
        KEY_TOPICS_CONTENT = context.getString(R.string.key_topics_content);
        KEY_QUIZES_CONTENT = context.getString(R.string.key_quizes_content);
    }

    // ================================
    // Firebase
    // ================================

    public LiveData<Topic> getTopicById(Integer id) {
        Query query = dbRef.child(KEY_TOPICS_CONTENT).child(String.valueOf(id));
        MutableLiveData<Topic> topic = new MutableLiveData<>();
        query.addListenerForSingleValueEvent(new SingleTopicValueEventListener(topic));

        return topic;
    }

    public MutableLiveData<TopicList> getAllTopicsDescription() {
        if (topicList.getValue() != null) return topicList;

        Query query = dbRef.child(KEY_TOPIC_LIST);
        query.addListenerForSingleValueEvent(new TopicDescriptionValueEventListener(topicList));

        return topicList;
    }

    public LiveData<Quiz> getQuizById(int id) {
        Query query = dbRef.child(KEY_QUIZES_CONTENT).child(String.valueOf(id));
        MutableLiveData<Quiz> quiz = new MutableLiveData<>();
        query.addListenerForSingleValueEvent(new QuizValueEventListener(quiz));

        return quiz;
    }

    public void pinTopicForRead(int topicId) {
        Query query = dbRef.child(KEY_TOPICS_CONTENT).child(String.valueOf(topicId));
        query.addListenerForSingleValueEvent(new SingleTopicValueEventListener(local));
    }

    // ================================
    // Local
    // ================================

    public void fillDatabase(List<TopicDescription> topicList) {
        local.fillTopicList(topicList);
    }

    public LiveData<List<TopicEntity>> getAllTopics() {
        return local.getAll();
    }

    public void pinTopic(TopicEntity topicEntity) {
        local.pinTopic(topicEntity);
    }

    public LiveData<ReadCardEntity> widgetUpdate() {
        return local.widgetUpdate();
    }

}
