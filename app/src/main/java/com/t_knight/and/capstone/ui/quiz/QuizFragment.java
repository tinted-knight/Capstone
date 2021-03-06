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
import com.t_knight.and.capstone.model.helpers.QuizHint;
import com.t_knight.and.capstone.model.quiz.QuizCard;
import com.t_knight.and.capstone.model.quiz.QuizSpot;
import com.t_knight.and.capstone.ui.custom_views.QuizEditTextLayoutChangeListener;
import com.t_knight.and.capstone.ui.custom_views.QuizSpotRect;
import com.t_knight.and.capstone.ui.custom_views.QuizTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

public class QuizFragment extends Fragment {

    @BindView(R.id.tv_quiz) QuizTextView tvQuiz;
    @BindView(R.id.btn_enter) Button btnEnter;
    @BindView(R.id.tv_hint) TextView tvHint;
    @BindView(R.id.fl_quiz_card) FrameLayout flQuizCard;

    @BindColor(R.color.colorQuizSpotBgError) int colorError;
    @BindColor(R.color.colorQuizSpotBgCorrect) int colorCorrect;
    @BindColor(R.color.colorQuizSpotBgMispell) int colorMisple;

    private final String BUNDLE_QUIZ_SPOTS = "quiz_edit_texts";
    private ArrayList<String> quizBundle;

    private List<EditText> etQuizAnswers;
    private QuizViewModel viewModel;

    public QuizFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_quiz, container, false);
        ButterKnife.bind(this, rootView);

        setClickListeners();
        if (savedInstanceState != null)
            quizBundle = savedInstanceState.getStringArrayList(BUNDLE_QUIZ_SPOTS);

        return rootView;
    }

    @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(QuizViewModel.class);
        registerObservers();
    }

    @Override public void onSaveInstanceState(@NonNull Bundle outState) {
        ArrayList<String> quizAnswers = new ArrayList<>(etQuizAnswers.size());
        for (EditText editText : etQuizAnswers) {
            quizAnswers.add(editText.getText().toString());
        }
        outState.putStringArrayList(BUNDLE_QUIZ_SPOTS, quizAnswers);
        super.onSaveInstanceState(outState);
    }

    private void setClickListeners() {
        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                List<String> answers = new ArrayList<>(etQuizAnswers.size());
                for (EditText answer : etQuizAnswers)
                    answers.add(answer.getText().toString().trim());
                viewModel.checkAnswers(answers);
            }
        });
        tvHint.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                viewModel.navigateNextCard();
            }
        });
        tvHint.setOnLongClickListener(new View.OnLongClickListener() {
            @Override public boolean onLongClick(View v) {
                viewModel.navigatePreviousCard();
                return true;
            }
        });
    }

    private void registerObservers() {
        viewModel.getCurrentQuizCard().observe(this, new Observer<QuizCard>() {
            @Override public void onChanged(@Nullable final QuizCard quizCard) {
                if (quizCard != null) {
                    tvQuiz.setQuizSpots(quizCard.getSpots().subList(0, viewModel.getDifficulty()));
                    hideQuizSpots(
                            quizCard.getText(),
                            quizCard.getSpots().subList(0, viewModel.getDifficulty()));
                    showQuizEditTexts();
                }
            }
        });

        viewModel.getAnswersCheckResult().observe(this, new Observer<List<QuizHint>>() {
            @Override public void onChanged(@Nullable List<QuizHint> hints) {
                if (hints != null) {
                    boolean allCorrect = true;
                    int i = 0;
                    for (QuizHint quizHint : hints) {
                        if (!quizHint.answerCorrect()) {
                            allCorrect = false;
                            highlightError(etQuizAnswers.get(i), quizHint.getHint());
                        } else {
                            if (quizHint.notHintEmpty()) {
                                highlightMisspell(etQuizAnswers.get(i), quizHint.getHint());
                            } else {
                                highlightCorrect(etQuizAnswers.get(i));
                            }
                        }
                        i++;
                    }
                    if (allCorrect) navigateNextWithDelay();
                }
            }

            // TODO move to custom EditText
            private void highlightError(EditText editText, String hint) {
                editText.setText("");
                editText.setHint(hint);
                editText.setBackgroundColor(colorError);
            }

            private void highlightMisspell(EditText editText, String hint) {
                editText.setText(hint);
                editText.setBackgroundColor(colorMisple);
            }

            private void highlightCorrect(EditText editText) {
                editText.setBackgroundColor(colorCorrect);
            }

        });
    }

    private void navigateNextWithDelay() {
        Thread thread = new Thread() {
            @Override public void run() {
                super.run();
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (getActivity() != null)
                    getActivity().runOnUiThread(new Runnable() {
                        @Override public void run() {
                            viewModel.navigateNextCard();
                        }
                    });
            }
        };
        thread.start();
    }

    private void showQuizEditTexts() {
        clearQuizView();
        // https://stackoverflow.com/questions/7733813/how-can-you-tell-when-a-layout-has-been-drawn
        tvQuiz.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override public void onGlobalLayout() {
                tvQuiz.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                LayoutInflater inflater = getLayoutInflater();
                etQuizAnswers = new ArrayList<>(tvQuiz.getQuizSpotRects().size());
                int i = 0;
                for (QuizSpotRect quizSpot : tvQuiz.getQuizSpotRects()) {
                    EditText etQuiz = (EditText)
                            inflater.inflate(R.layout.edittext_quizspot, flQuizCard, false);
                    etQuiz.setFilters(new InputFilter[]{
                            new InputFilter.LengthFilter(quizSpot.getWordLength())});
                    quizSpot.adjustOffset(tvQuiz);
                    etQuiz.addOnLayoutChangeListener(new QuizEditTextLayoutChangeListener(quizSpot));
                    // need to match with hint TextView
                    etQuiz.setTag(i++);
                    // need for d-pad and tab navigation
                    etQuiz.setId(View.generateViewId());
                    etQuiz.setOnFocusChangeListener(showHint);

                    flQuizCard.addView(etQuiz);
                    etQuizAnswers.add(etQuiz);
                }

                reorderEditTextsForNavigation();
                if (quizBundle != null)
                    for (int j = 0; j < etQuizAnswers.size(); j++)
                        etQuizAnswers.get(j).setText(quizBundle.get(j));
            }
        });
    }

    private void reorderEditTextsForNavigation() {
        int maxId = etQuizAnswers.size() - 1;
        for (int i = 0; i < maxId; i++) {
            int nextId = etQuizAnswers.get(i + 1).getId();
            etQuizAnswers.get(i).setNextFocusForwardId(nextId);
        }
        // From last jump to Check button then to first
        int firstId = etQuizAnswers.get(0).getId();
        etQuizAnswers.get(maxId).setNextFocusForwardId(btnEnter.getId());
        btnEnter.setNextFocusForwardId(firstId);
    }

    private final View.OnFocusChangeListener showHint = new View.OnFocusChangeListener() {
        @Override public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) tvHint.setText(viewModel.getQuizHint((Integer) v.getTag()));
        }
    };

    private void clearQuizView() {
        if (etQuizAnswers != null)
            for (View v : etQuizAnswers)
                flQuizCard.removeView(v);
        tvHint.setText("");
    }

    private void hideQuizSpots(String quizString, List<QuizSpot> spots) {
        SpannableString hideQuizSpots =
                new SpannableString(removeUndersopes(quizString, spots) + "\n ");
        for (QuizSpot spot : spots) {
            hideQuizSpots.setSpan(
                    new ForegroundColorSpan(Color.TRANSPARENT),
                    spot.getStart(),
                    spot.getEnd(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );
        }
        tvQuiz.setText(hideQuizSpots);
    }

    private String removeUndersopes(String quizString, List<QuizSpot> spots) {
        QuizSpot spot = spots.get(spots.size() - 1);
        String strWithQuizSpots = quizString.substring(0, spot.getEnd());
        String restOfStringWithoutUnderSopes =
                quizString.substring(spot.getEnd(), quizString.length())
                        .replace("_", "");

        return strWithQuizSpots + restOfStringWithoutUnderSopes;
    }
}
