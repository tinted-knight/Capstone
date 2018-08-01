package com.t_knight.and.capstone.firebase;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.t_knight.and.capstone.model.TopicContentList;
import com.t_knight.and.capstone.model.TopicList;

public class FirebaseConnection {

    private static final String TAG = "TAGG";
    private DatabaseReference dbRef;

    private final MutableLiveData<TopicList> topicList;

    public FirebaseConnection() {
        dbRef = FirebaseDatabase.getInstance().getReference();
//        dbRef = AppDatabase.getReference();
        topicList = new MutableLiveData<>();
    }

    public LiveData<TopicContentList> getAllTopicsContent() {
        Query query = dbRef.child("topics");

        MutableLiveData<TopicContentList> topicsContent;
        topicsContent = new MutableLiveData<>();
        query.addListenerForSingleValueEvent(new TopicValueEventListener(topicsContent));

        return topicsContent;
    }

    public LiveData<TopicList> getAllTopicsDescription() {
        if (topicList.getValue() != null) return topicList;

        Query query = dbRef.child("content");
        query.addListenerForSingleValueEvent(new TopicDescriptionValueEventListener(topicList));

        return topicList;
    }

}
