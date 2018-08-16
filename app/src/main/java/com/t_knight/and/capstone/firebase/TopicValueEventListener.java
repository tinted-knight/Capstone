package com.t_knight.and.capstone.firebase;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.t_knight.and.capstone.model.Topic;

import java.util.ArrayList;
import java.util.List;

class TopicValueEventListener implements ValueEventListener {

    private final MutableLiveData<List<Topic>> data;

    TopicValueEventListener(MutableLiveData<List<Topic>> data) {
        this.data = data;
    }

    @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        List<Topic> topicList = new ArrayList<>();
        for (DataSnapshot snapshot : dataSnapshot.getChildren())
            topicList.add(snapshot.getValue(Topic.class));
        if (topicList.size() > 0) data.setValue(topicList);
    }

    @Override public void onCancelled(@NonNull DatabaseError databaseError) {
        Log.d("TAGG", "onCancelled: " + databaseError.toString());
    }
}
