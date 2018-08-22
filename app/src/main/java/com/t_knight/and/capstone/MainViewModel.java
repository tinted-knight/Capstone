package com.t_knight.and.capstone;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.t_knight.and.capstone.firebase.FirebaseConnection;
import com.t_knight.and.capstone.local_db.ReadCardEntity;
import com.t_knight.and.capstone.local_db.TopicEntity;
import com.t_knight.and.capstone.model.FireContent;
import com.t_knight.and.capstone.model.TopicDescription;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private MutableLiveData<FireContent> topicList;
    private final LiveData<List<TopicEntity>> topics;

    private int activeTopicId = 0;
    private MutableLiveData<TopicDescription> activeTopic;

    private final FirebaseConnection repo;
    private final StorageReference storageRef;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repo = FirebaseConnection.getInstance(application);
        topicList = repo.getAllTopicsDescription();
        topics = repo.getAllTopics();
        storageRef = FirebaseStorage.getInstance().getReference();
    }

    public void setActiveTopic(int id) {
        activeTopicId = id;
    }

    public int getActiveTopicId() {
        return activeTopicId;
    }

    public LiveData<TopicDescription> getActiveTopicDetails() {
        if (activeTopic == null) activeTopic = new MutableLiveData<>();
        activeTopic.setValue(topicList.getValue().getTopicDescriptions().get(activeTopicId));
        return activeTopic;
    }

    public MutableLiveData<FireContent> loadFromNetwork() {
        // TODO if (topicList != null) return topicList
        topicList = repo.getAllTopicsDescription();
        return topicList;
    }

    public LiveData<List<TopicEntity>> getAllTopics() {
        return topics;
    }

    public void fillLocalDatabase() {
        if (topicList != null && topicList.getValue() != null) {
            repo.fillDatabase(topicList.getValue().getTopicDescriptions());
        }
    }

    public void pinTopicToWidget(TopicEntity topic) {
        repo.pinTopic(topic);
        repo.pinTopicForRead(topic.topicId);
    }

    public LiveData<ReadCardEntity> widgetUpdate() {
        // smth like a listener to update a home screen widget after insert operation in background
        // if there is smth in the table then should update widget
        return repo.widgetUpdate();
    }

    public StorageReference getStorageRef(String s) {
        return storageRef.child(s);
    }
}
