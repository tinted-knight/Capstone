package com.t_knight.and.capstone;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.t_knight.and.capstone.firebase.FirebaseConnection;
import com.t_knight.and.capstone.model.TopicDescList;
import com.t_knight.and.capstone.model.TopicList;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = "TAGG";

    private FirebaseConnection repo;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repo = new FirebaseConnection();
    }

    public LiveData<TopicList> getAllTopicsContent() {
        return repo.getAllTopicsContent();
    }

    public LiveData<TopicDescList> getTopicTitles() {
        return repo.getAllTopicsDescription();
    }
}
