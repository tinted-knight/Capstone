package com.t_knight.and.capstone.firebase;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.t_knight.and.capstone.model.Topic;
import com.t_knight.and.capstone.model.TopicContentList;
import com.t_knight.and.capstone.model.TopicList;
import com.t_knight.and.capstone.model.quiz.Quiz;

public class FirebaseConnection {

    private static final String TAG = "TAGG";
    private DatabaseReference dbRef;

    private final MutableLiveData<TopicList> topicList;

    private static FirebaseConnection instance;

    public static FirebaseConnection getInstance() {
        if (instance == null)
            instance = new FirebaseConnection();
        return instance;
    }

    private FirebaseConnection() {
        dbRef = FirebaseDatabase.getInstance().getReference();
        topicList = new MutableLiveData<>();
    }

    public LiveData<TopicContentList> getAllTopicsContent() {
        Query query = dbRef.child("topics");

        MutableLiveData<TopicContentList> topicsContent;
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

    public LiveData<TopicList> getAllTopicsDescription() {
        if (topicList.getValue() != null) return topicList;

        Query query = dbRef.child("content");
        query.addListenerForSingleValueEvent(new TopicDescriptionValueEventListener(topicList));

        return topicList;
    }

    public LiveData<Quiz> getQuizById(int id) {
        Query query = dbRef.child("quizes").child(String.valueOf(id));
        MutableLiveData<Quiz> quiz = new MutableLiveData<>();
        query.addListenerForSingleValueEvent(new QuizValueEventListener(quiz));

        return quiz;
    }

}
