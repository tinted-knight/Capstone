package com.t_knight.and.capstone.firebase;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.t_knight.and.capstone.model.FireContent;

import timber.log.Timber;

class TopicDescriptionValueEventListener implements ValueEventListener {

    private final MutableLiveData<FireContent> data;

    public TopicDescriptionValueEventListener(MutableLiveData<FireContent> data) {
        this.data = data;
    }

    @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        if (dataSnapshot.getValue() != null)
            data.setValue(dataSnapshot.getValue(FireContent.class));
    }

    @Override public void onCancelled(@NonNull DatabaseError databaseError) {
        Timber.d("onCancelled: %s", databaseError.getMessage());
    }
}
