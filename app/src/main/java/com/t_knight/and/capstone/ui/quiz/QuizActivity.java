package com.t_knight.and.capstone.ui.quiz;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.t_knight.and.capstone.R;
import com.t_knight.and.capstone.model.quiz.Quiz;
import com.t_knight.and.capstone.model.quiz.QuizCard;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuizActivity extends AppCompatActivity {

    public static final String EXTRA_TOPIC_ID = "topic_id";
    public static final String EXTRA_DIFFICULTY_LEVEL = "difficulty_level";
    private static final int TOPIC_ID_DEFAULT = 1;
    private static final int DIFFICULTY_LEVEL_DEFAULT = 1;

    @BindView(R.id.tv_translation) TextView tvTranslation;
    @BindView(R.id.toolbar2) Toolbar toolbar;

    private QuizViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            if (intent != null) {
                int topicId = intent.getIntExtra(EXTRA_TOPIC_ID, TOPIC_ID_DEFAULT);
                int difficulty = intent.getIntExtra(EXTRA_DIFFICULTY_LEVEL, DIFFICULTY_LEVEL_DEFAULT);
                QuizViewModel.QuizVMFactory factory =
                        new QuizViewModel.QuizVMFactory(getApplication(), topicId, difficulty);
                viewModel = ViewModelProviders.of(this, factory).get(QuizViewModel.class);
                showQuizFragment();
                registerObservers();
            }
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
        viewModel.getQuizEnd().observe(this, new Observer<Boolean>() {
            @Override public void onChanged(@Nullable Boolean value) {
                if (!value) return;
                Toast.makeText(QuizActivity.this, "quiz end", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(QuizActivity.this, SummaryActivity.class);
                intent.putParcelableArrayListExtra(SummaryActivity.KEY_QUIZ_RESULT, viewModel.getResults());
                startActivity(intent);
                finish();
            }
        });
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
