package com.t_knight.and.capstone.ui.quiz;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.t_knight.and.capstone.R;
import com.t_knight.and.capstone.model.quiz.QuizCard;
import com.t_knight.and.capstone.model.quiz.QuizSpot;
import com.t_knight.and.capstone.ui.custom_views.QuizEditTextLayoutChangeListener;
import com.t_knight.and.capstone.ui.custom_views.QuizSpotRect;
import com.t_knight.and.capstone.ui.custom_views.QuizTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuizFragment extends Fragment {

    @BindView(R.id.tv_quiz) QuizTextView tvQuiz;
    @BindView(R.id.btn_enter) Button btnEnter;
    @BindView(R.id.tv_hint) TextView tvHint;
    @BindView(R.id.fl_quiz_card) FrameLayout flQuizCard;

    private List<EditText> quizAnswers;
    private QuizViewModel viewModel;

    public QuizFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_quiz, container, false);
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
                    tvQuiz.setQuizSpots(quizCard.getSpots());
                    hideQuizSpots(quizCard);
//                    tvQuiz.setText(quizCard.getText());
                    showQuizEditTexts();
                }
            }
        });
    }

    private void showQuizEditTexts() {
        clearQuizSpotViews();
        // https://stackoverflow.com/questions/7733813/how-can-you-tell-when-a-layout-has-been-drawn
        tvQuiz.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override public void onGlobalLayout() {
                tvQuiz.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                LayoutInflater inflater = getLayoutInflater();
                quizAnswers = new ArrayList<>(tvQuiz.getQuizSpotRects().size());
                for (QuizSpotRect quizSpot : tvQuiz.getQuizSpotRects()) {
                    EditText etQuiz = (EditText)
                            inflater.inflate(R.layout.edittext_quizspot, flQuizCard, false);
                    etQuiz.setFilters(new InputFilter[]{
                            new InputFilter.LengthFilter(quizSpot.getWordLength())});
                    quizSpot.adjustOffset(tvQuiz);
                    adjustCoordinates(etQuiz, quizSpot);
                    etQuiz.addOnLayoutChangeListener(new QuizEditTextLayoutChangeListener(quizSpot));

                    flQuizCard.addView(etQuiz);
                    quizAnswers.add(etQuiz);
                }
            }

            private void adjustCoordinates(EditText etQuiz, QuizSpotRect quizSpot) {
                etQuiz.setLeft(quizSpot.getLeft());
                etQuiz.setRight(quizSpot.getRight());
                etQuiz.setTop(quizSpot.getTop());
                etQuiz.setBottom(quizSpot.getBottom());
            }
        });
    }

    private void clearQuizSpotViews() {
        if (quizAnswers != null)
            for (View v : quizAnswers)
                flQuizCard.removeView(v);
    }

    private void hideQuizSpots(QuizCard quizCard) {
        SpannableString ss = new SpannableString(quizCard.getText() + "\n ");
        for (QuizSpot spot : quizCard.getSpots()) {
            ss.setSpan(
                    new ForegroundColorSpan(Color.TRANSPARENT),
                    spot.getStart(),
                    spot.getEnd(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );
        }
        tvQuiz.setText(ss);
//        tvQuiz.setMovementMethod(LinkMovementMethod.getInstance());
//        tvQuiz.setHighlightColor(Color.RED);
    }
}
