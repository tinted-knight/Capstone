package com.t_knight.and.capstone;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.t_knight.and.capstone.firebase.FirebaseConnection;
import com.t_knight.and.capstone.local_db.TopicListRepo;
import com.t_knight.and.capstone.model.TopicList;
import com.t_knight.and.capstone.model.TopicContentList;
import com.t_knight.and.capstone.model.TopicTitle;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = "TAGG";

    private LiveData<TopicList> topicList;

    private int activeTopicId = 0;
    private MutableLiveData<TopicTitle> activeTopic;

    private FirebaseConnection repo;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repo = FirebaseConnection.getInstance(application);
    }

    public void setActiveTopic(int id) {
        activeTopicId = id;
    }

    public LiveData<TopicTitle> getActiveTopicDetails() {
        if (activeTopic == null) activeTopic = new MutableLiveData<>();
        activeTopic.setValue(topicList.getValue().get(activeTopicId));
        return activeTopic;
    }

    public LiveData<TopicContentList> getAllTopicsContent() {
        return repo.getAllTopicsContent();
    }

    public LiveData<TopicList> getTopicTitles() {
        // TODO if (topicList != null) return topicList
        topicList = repo.getAllTopicsDescription();
        return topicList;
    }

    public void fillLocalDatabase() {
        if (topicList != null && topicList.getValue() != null && topicList.getValue().size() > 0) {
            repo.fillDatabase(topicList.getValue());
        }
    }

}
