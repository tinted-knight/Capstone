package com.t_knight.and.capstone.firebase;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.t_knight.and.capstone.model.TopicContentList;
import com.t_knight.and.capstone.model.Topics.Topic;

class TopicValueEventListener implements ValueEventListener {

    private final MutableLiveData<TopicContentList> data;

    public TopicValueEventListener(MutableLiveData<TopicContentList> data) {
        this.data = data;
    }

    @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        TopicContentList topicList = new TopicContentList();
        for (DataSnapshot snapshot : dataSnapshot.getChildren())
            topicList.add(snapshot.getValue(Topic.class));
        if (topicList.size() > 0) data.setValue(topicList);
    }

    @Override public void onCancelled(@NonNull DatabaseError databaseError) {
        Log.d("TAGG", "onCancelled: " + databaseError.toString());
    }
}
