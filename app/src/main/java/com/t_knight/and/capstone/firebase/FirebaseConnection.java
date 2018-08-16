package com.t_knight.and.capstone.firebase;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.t_knight.and.capstone.local_db.TopicEntity;
import com.t_knight.and.capstone.local_db.TopicListRepo;
import com.t_knight.and.capstone.model.FireContent;
import com.t_knight.and.capstone.model.Topic;
import com.t_knight.and.capstone.model.TopicDescription;
import com.t_knight.and.capstone.model.quiz.Quiz;

import java.util.List;

public class FirebaseConnection {

    private static final String TAG = "TAGG";
    private DatabaseReference dbRef;

    private final MutableLiveData<FireContent> topicList;
    //Firebase
    private static FirebaseConnection instance;
    // Local
    private TopicListRepo local;

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
    }

    // ================================
    // Firebase
    // ================================

    public LiveData<List<Topic>> getAllTopicsContent() {
        Query query = dbRef.child("topics");

        MutableLiveData<List<Topic>> topicsContent;
        topicsContent = new MutableLiveData<>();
        query.addListenerForSingleValueEvent(new TopicValueEventListener(topicsContent));

        return topicsContent;
    }

    public LiveData<Topic> getTopicById(Integer id) {
        Query query = dbRef.child("topics").child(String.valueOf(id));
        MutableLiveData<Topic> topic;
        topic = new MutableLiveData<>();
        query.addListenerForSingleValueEvent(new SingleTopicValueEventListener(topic));

        return topic;
    }

    public MutableLiveData<FireContent> getAllTopicsDescription() {
        if (topicList.getValue() != null) return topicList;

        Query query = dbRef.child("content");
        query.addListenerForSingleValueEvent(new TopicDescriptionValueEventListener(topicList));

        return topicList;
    }

/*
    public void syncTopicDescriptionsForWidget() {
        Query query = dbRef.child("content");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<TopicTitle> result = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                    result.add(snapshot.getValue(TopicTitle.class));
                fillDatabase(result);
            }

            @Override public void onCancelled(@NonNull DatabaseError databaseError) {
                Timber.d("onCancelled: %s", databaseError.getMessage());
            }
        });
    }
*/

    public LiveData<Quiz> getQuizById(int id) {
        Query query = dbRef.child("quizes").child(String.valueOf(id));
        MutableLiveData<Quiz> quiz = new MutableLiveData<>();
        query.addListenerForSingleValueEvent(new QuizValueEventListener(quiz));

        return quiz;
    }

    // ================================
    // Local
    // ================================

    public void fillDatabase(List<TopicDescription> topicList) {
//        List<TopicEntity> entities = new ArrayList<>(topicList.size());
//        for (TopicTitle topic : topicList)
//            entities.add(new TopicEntity(topic));
        local.fillTopicList(topicList);
    }

    public LiveData<List<TopicEntity>> getAllTopics() {
        return local.getAll();
    }

    public void pinTopic(TopicEntity topicEntity) {
        local.pinTopic(topicEntity);
    }

}
