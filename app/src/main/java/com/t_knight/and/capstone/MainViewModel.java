package com.t_knight.and.capstone;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.t_knight.and.capstone.model.Database;
import com.t_knight.and.capstone.model.Topics;
import com.t_knight.and.capstone.model.Topics.Topic;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = "TAGG";

    private DatabaseReference reference;
    private MutableLiveData<List<Topic>> topics;

    public MainViewModel(@NonNull Application application) {
        super(application);
        topics = new MutableLiveData<>();
        initFirebaseDatabase();
    }

    private void initFirebaseDatabase() {
        reference = FirebaseDatabase.getInstance().getReference();
        Query texts = reference.child(Database.ROOT_ELEMENT);
        texts.addListenerForSingleValueEvent(topicEventListener);
    }

    private ValueEventListener topicEventListener = new ValueEventListener() {
        @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            List<Topic> topicList = new ArrayList<>();
            for (DataSnapshot snapshot : dataSnapshot.getChildren())
                topicList.add(snapshot.getValue(Topic.class));
            if (topicList.size() > 0) topics.setValue(topicList);
        }

        @Override public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.d(TAG, "onCancelled: " + databaseError.toString());
        }
    };

    public LiveData<List<Topic>> getTopics() {
        return topics;
    }
}
