package com.t_knight.and.capstone.ui.read;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.t_knight.and.capstone.firebase.FirebaseConnection;
import com.t_knight.and.capstone.model.Topic;

public class ReadViewModel extends AndroidViewModel {

    private static final String TAG = "TAGG";

    private final FirebaseConnection repo;
    private final Integer topicId;

    private ReadViewModel(@NonNull Application application, FirebaseConnection repository, Integer topicId) {
        super(application);
        repo = repository;
        this.topicId = topicId;
    }

    public LiveData<Topic> getTopicContent() {
        return repo.getTopicById(topicId);
    }

    public static class ReadVMFactory extends ViewModelProvider.NewInstanceFactory {

        private final Application application;
        private final Integer topicId;
        private final FirebaseConnection repository;

        ReadVMFactory(Application application, Integer topicId) {
            this.application = application;
            this.topicId = topicId;
            repository = FirebaseConnection.getInstance();
        }

        @NonNull @Override public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new ReadViewModel(application, repository, topicId);
        }
    }

}
