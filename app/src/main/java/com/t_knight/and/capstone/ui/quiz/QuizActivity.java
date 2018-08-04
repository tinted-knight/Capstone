package com.t_knight.and.capstone.ui.quiz;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.t_knight.and.capstone.R;
import com.t_knight.and.capstone.model.quiz.Quiz;
import com.t_knight.and.capstone.model.quiz.QuizCard;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuizActivity extends AppCompatActivity {

    public static final String EXTRA_TOPIC_ID = "topic_id";

    @BindView(R.id.tv_translation) TextView tvTranslation;

    private QuizViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent != null) {
            int topicId = intent.getIntExtra(EXTRA_TOPIC_ID, 0);
            QuizViewModel.QuizVMFactory factory =
                    new QuizViewModel.QuizVMFactory(getApplication(), topicId);
            viewModel = ViewModelProviders.of(this, factory).get(QuizViewModel.class);
            showQuizFragment();
            registerObservers();
        }
    }

    private void showQuizFragment() {
        QuizFragment quizFragment = new QuizFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.flQuiz, quizFragment, QuizFragment.class.getSimpleName())
                .commit();
    }

    private void registerObservers() {
        viewModel.getQuiz().observe(this, new Observer<Quiz>() {
            @Override public void onChanged(@Nullable Quiz quiz) {
                if (quiz != null) {
                    tvTranslation.setText(quiz.getCards().get(0).getTranslation());
                    viewModel.showFirstCard();
                }
            }
        });

        viewModel.getCurrentQuizCard().observe(this, new Observer<QuizCard>() {
            @Override public void onChanged(@Nullable QuizCard quizCard) {
                if (quizCard != null) {
                    tvTranslation.setText(quizCard.getTranslation());
                }
            }
        });
    }

}
