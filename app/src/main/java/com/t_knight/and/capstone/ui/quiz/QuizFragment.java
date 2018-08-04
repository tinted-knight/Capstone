package com.t_knight.and.capstone.ui.quiz;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.t_knight.and.capstone.R;
import com.t_knight.and.capstone.model.quiz.QuizCard;
import com.t_knight.and.capstone.model.quiz.QuizSpot;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuizFragment extends Fragment {

    @BindView(R.id.tv_quiz) TextView tvQuiz;
    @BindView(R.id.btn_enter) Button btnEnter;
    @BindView(R.id.tv_hint) TextView tvHint;

    private QuizViewModel viewModel;

    public QuizFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.quiz_fragment, container, false);
        ButterKnife.bind(this, rootView);

        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                viewModel.navigateNextCard();
            }
        });
        tvHint.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                viewModel.navigatePreviousCard();
            }
        });

        return rootView;
    }

    @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(QuizViewModel.class);
        registerObservers();
    }

    private void registerObservers() {
        viewModel.getCurrentQuizCard().observe(this, new Observer<QuizCard>() {
            @Override public void onChanged(@Nullable QuizCard quizCard) {
                if (quizCard != null) {
                    debugShowQuizSpots(quizCard);
                }
            }
        });
    }

    private void debugShowQuizSpots(QuizCard quizCard) {
        SpannableString ss = new SpannableString(quizCard.getText());
        for (QuizSpot spot : quizCard.getSpots()) {
            ss.setSpan(
                    new BackgroundColorSpan(Color.YELLOW),
                    spot.getStart(),
                    spot.getEnd(),
                    Spanned.SPAN_INCLUSIVE_INCLUSIVE
            );
        }
        tvQuiz.setText(ss);
        tvQuiz.setMovementMethod(LinkMovementMethod.getInstance());
        tvQuiz.setHighlightColor(Color.RED);
    }
}
