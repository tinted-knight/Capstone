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

    public FirebaseConnection() {
        dbRef = FirebaseDatabase.getInstance().getReference();
    }

    public LiveData<TopicContentList> getAllTopicsContent() {
        Query query = dbRef.child("topics");
        MutableLiveData<TopicContentList> data = new MutableLiveData<>();
        query.addListenerForSingleValueEvent(new TopicValueEventListener(data));
        return data;
    }

    public LiveData<TopicList> getAllTopicsDescription() {
        Query query = dbRef.child("content");
        MutableLiveData<TopicList> data = new MutableLiveData<>();
        query.addListenerForSingleValueEvent(new TopicDescriptionValueEventListener(data));
        return data;
    }

}
