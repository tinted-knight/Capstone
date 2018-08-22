package com.t_knight.and.capstone.firebase;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.t_knight.and.capstone.model.quiz.Quiz;

import timber.log.Timber;

class QuizValueEventListener implements ValueEventListener {

    private final MutableLiveData<Quiz> data;

    QuizValueEventListener(MutableLiveData<Quiz> data) {
        this.data = data;
    }

    @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        data.setValue(dataSnapshot.getValue(Quiz.class));
    }

    @Override public void onCancelled(@NonNull DatabaseError databaseError) {
        Timber.d("onCancelled: %s", databaseError.toString());
    }

}
