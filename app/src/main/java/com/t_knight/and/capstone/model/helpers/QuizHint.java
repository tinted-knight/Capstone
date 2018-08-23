package com.t_knight.and.capstone.model.helpers;

import android.support.annotation.Nullable;

public class QuizHint extends android.support.v4.util.Pair<Boolean, String> {

    public QuizHint(@Nullable Boolean isCorrect, @Nullable String hint) {
        super(isCorrect, hint);
    }

    public Boolean answerCorrect() {
        return first;
    }

    public String getHint() {
        return second;
    }

    public boolean notHintEmpty() {
        return second != null && !second.isEmpty();
    }

}
