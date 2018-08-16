package com.t_knight.and.capstone.firebase;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.t_knight.and.capstone.model.FireContent;
import com.t_knight.and.capstone.model.TopicTitle;

import java.util.ArrayList;
import java.util.List;

public class TopicDescriptionValueEventListener implements ValueEventListener {

    private final MutableLiveData<FireContent> data;

    public TopicDescriptionValueEventListener(MutableLiveData<FireContent> data) {
        this.data = data;
    }

    @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        if (dataSnapshot.getValue() != null)
            data.setValue(dataSnapshot.getValue(FireContent.class));
//        List<TopicTitle> topicTitles = new ArrayList<>();
//        for (DataSnapshot snapshot : dataSnapshot.getChildren())
//            topicTitles.add(snapshot.getValue(TopicTitle.class));
//        if (topicTitles.size() > 0) data.setValue(topicTitles);
    }

    @Override public void onCancelled(@NonNull DatabaseError databaseError) {
        Log.d("TAGG", "onCancelled: " + databaseError.getMessage());
    }
}
