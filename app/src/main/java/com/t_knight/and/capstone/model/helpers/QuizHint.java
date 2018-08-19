package com.t_knight.and.capstone.model.helpers;

import android.support.annotation.Nullable;

public class QuizHint extends android.support.v4.util.Pair<Boolean, String> {

    /**
     * Constructor for a QuizHint.
     *
     * @param isCorrect the isCorrect object in the Pair
     * @param hint      the hint object in the pair
     */
    public QuizHint(@Nullable Boolean isCorrect, @Nullable String hint) {
        super(isCorrect, hint);
    }

    public Boolean answerCorrect() {
        return first;
    }

    public String getHint() {
        return second;
    }

    public boolean isHintEmpty() {
        if (second == null) return true;
        return second.isEmpty();
    }

}
