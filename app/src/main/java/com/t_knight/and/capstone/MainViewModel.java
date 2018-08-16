package com.t_knight.and.capstone;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.t_knight.and.capstone.firebase.FirebaseConnection;
import com.t_knight.and.capstone.local_db.TopicEntity;
import com.t_knight.and.capstone.model.FireContent;
import com.t_knight.and.capstone.model.Topic;
import com.t_knight.and.capstone.model.TopicDescription;
import com.t_knight.and.capstone.ui.widget_new.TopicWidgetService;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = "TAGG";

    private MutableLiveData<FireContent> topicList;
    private LiveData<List<TopicEntity>> topics;

    private int activeTopicId = 0;
    private MutableLiveData<TopicDescription> activeTopic;

    private FirebaseConnection repo;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repo = FirebaseConnection.getInstance(application);
    }

    public void setActiveTopic(int id) {
        activeTopicId = id;
    }

    public LiveData<TopicDescription> getActiveTopicDetails() {
        if (activeTopic == null) activeTopic = new MutableLiveData<>();
        activeTopic.setValue(topicList.getValue().getTopicDescriptions().get(activeTopicId));
        return activeTopic;
    }

    public LiveData<List<Topic>> getAllTopicsContent() {
        return repo.getAllTopicsContent();
    }

    public MutableLiveData<FireContent> loadFromNetwork() {
        // TODO if (topicList != null) return topicList
        topicList = repo.getAllTopicsDescription();
        return topicList;
    }

    public LiveData<List<TopicEntity>> getAllTopics() {
        topics = repo.getAllTopics();
        return topics;
    }

    public void fillLocalDatabase() {
        if (topicList != null && topicList.getValue() != null) {
            repo.fillDatabase(topicList.getValue().getTopicDescriptions());
        }
    }

    public void pinTopicToWidget(TopicEntity topic) {
        repo.pinTopic(topic);
        TopicWidgetService.startActionUpdate(getApplication());
    }
}
