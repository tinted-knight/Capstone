package com.t_knight.and.capstone.ui.read;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.util.Pair;

import com.t_knight.and.capstone.firebase.FirebaseConnection;
import com.t_knight.and.capstone.model.SingleCard;
import com.t_knight.and.capstone.model.Topic;
import com.t_knight.and.capstone.model.TopicDescription;
import com.t_knight.and.capstone.model.helpers.NavigationButtonsLiveData;

import java.util.List;

class ReadViewModel extends AndroidViewModel {

    private final LiveData<Topic> topicContent;
    private int currentCard;
    private final MutableLiveData<SingleCard> singleCard;
    private final NavigationButtonsLiveData navBtnState;

    private ReadViewModel(@NonNull Application application, FirebaseConnection repository, TopicDescription topicDescription) {
        super(application);
//        repo = repository;
        navBtnState = new NavigationButtonsLiveData();
        singleCard = new MutableLiveData<>();
        topicContent = repository.getTopicById(topicDescription.getId());
    }

    public void navigateNextCard() {
        List<SingleCard> cardList = topicContent.getValue().getCardContent();
        if (cardList.size() > currentCard + 1) {
            currentCard++;
            setCurrentCard();
        }
    }

    public void navigatePreviousCard() {
        if (currentCard - 1 >= 0) {
            currentCard--;
            setCurrentCard();
        }
    }

    public void showFirstCard() {
        currentCard = 0;
        setCurrentCard();
    }

    private void setCurrentCard() {
        List<SingleCard> cardList = topicContent.getValue().getCardContent();
        singleCard.setValue(cardList.get(currentCard));
        if (currentCard + 1 == cardList.size()) {
            navBtnState.setNextState(false);
        } else {
            navBtnState.setNextState(true);
        }

        if (currentCard == 0) {
            navBtnState.setPreviousState(false);
        } else {
            navBtnState.setPreviousState(true);
        }
    }

    public LiveData<Pair<Boolean, Boolean>> getNavigationButtonsState() {
        return navBtnState;
    }

    public LiveData<Topic> getTopicContent() {
        return topicContent;
    }

    public LiveData<SingleCard> getCardContent() {
        return singleCard;
    }

    public static class ReadVMFactory extends ViewModelProvider.NewInstanceFactory {

        private final Application application;
        private final TopicDescription topicTitle;
        private final FirebaseConnection repository;

        ReadVMFactory(Application application, TopicDescription topicDescription) {
            this.application = application;
            this.topicTitle = topicDescription;
            repository = FirebaseConnection.getInstance(application);
        }

        @NonNull @Override public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new ReadViewModel(application, repository, topicTitle);
        }
    }

}
