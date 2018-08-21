package com.t_knight.and.capstone.firebase;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.t_knight.and.capstone.local_db.TopicListRepo;
import com.t_knight.and.capstone.model.Topic;

public class SingleTopicValueEventListener implements ValueEventListener {

    private final MutableLiveData<Topic> data;
    private final TopicListRepo repo;

    SingleTopicValueEventListener(MutableLiveData<Topic> data) {
        this.data = data;
        repo = null;
    }

    SingleTopicValueEventListener(TopicListRepo repo) {
        data = null;
        this.repo = repo;
    }

    @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        if (data != null)
            data.setValue(dataSnapshot.getValue(Topic.class));

        if (repo != null)
            repo.pinTopicRead(dataSnapshot.getValue(Topic.class));
    }

    @Override public void onCancelled(@NonNull DatabaseError databaseError) {
        Log.d("TAGG", "onCancelled: " + databaseError.toString());
    }

}
