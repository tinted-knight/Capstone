package com.t_knight.and.capstone.ui.quiz;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.t_knight.and.capstone.firebase.FirebaseConnection;
import com.t_knight.and.capstone.model.quiz.Quiz;
import com.t_knight.and.capstone.model.quiz.QuizCard;

import java.util.ArrayList;
import java.util.List;

public class QuizViewModel extends AndroidViewModel {

    private LiveData<Quiz> quiz;
    private MutableLiveData<QuizCard> currentQuizCard;
    private int currentCardId;
    private final int difficulty;

    //    private List<String> answers;
    private MutableLiveData<List<String>> answersCheckResult;

    private QuizViewModel(@NonNull Application application, FirebaseConnection repository, int topicId, int difficulty) {
        super(application);
        quiz = repository.getQuizById(topicId);
        currentQuizCard = new MutableLiveData<>();
        answersCheckResult = new MutableLiveData<>();
        this.difficulty = difficulty;
    }

    public void navigateNextCard() {
        List<QuizCard> cardList = quiz.getValue().getCards();
        if (cardList.size() > currentCardId + 1) {
            currentCardId++;
            setCurrentQuizCard();
        }
    }

    public void navigatePreviousCard() {
        if (currentCardId - 1 >= 0) {
            currentCardId--;
            setCurrentQuizCard();
        }
    }

    public void showFirstCard() {
        currentCardId = 0;
        setCurrentQuizCard();
    }

    private void setCurrentQuizCard() {
        List<QuizCard> cardList = quiz.getValue().getCards();
        currentQuizCard.setValue(cardList.get(currentCardId));
        // TODO set navigation buttons state
    }

    public void checkAnswers(List<String> answerList) {
        if (answerList != null && answerList.size() > 0) {
            QuizCard quizCard = currentQuizCard.getValue();
            int i = 0;
            List<String> checkResult = new ArrayList<>(answerList.size());
            for (String answer : answerList) {
                String correctAnswer = quizCard.getSpots().get(i++).getAnswer();
                if (!answer.equalsIgnoreCase(correctAnswer))
                    checkResult.add(correctAnswer);
                else
                    checkResult.add(null);
            }
            answersCheckResult.setValue(checkResult);
        }
    }

    public LiveData<Quiz> getQuiz() {
        return quiz;
    }

    public LiveData<QuizCard> getCurrentQuizCard() {
        return currentQuizCard;
    }

    public LiveData<List<String>> getAnswersCheckResult() {
        return answersCheckResult;
    }

    public String getQuizHint(int i) {
        if (currentQuizCard.getValue() == null) return "";
        return currentQuizCard.getValue().getSpots().get(i).getHint();
    }

    public int getDifficulty() {
        return difficulty;
    }

    public static class QuizVMFactory extends ViewModelProvider.NewInstanceFactory {

        private final Application application;
        private final int topicId;
        private final int difficulty;
        private final FirebaseConnection repository;

        QuizVMFactory(Application application, int topicId, int difficulty) {
            this.application = application;
            this.topicId = topicId;
            repository = FirebaseConnection.getInstance(application);
            this.difficulty = difficulty;
        }

        @NonNull @Override public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new QuizViewModel(application, repository, topicId, difficulty);
        }
    }

}
