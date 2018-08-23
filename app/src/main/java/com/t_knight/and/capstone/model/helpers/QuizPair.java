package com.t_knight.and.capstone.model.helpers;

import android.support.annotation.Nullable;
import android.support.v4.util.Pair;

public class QuizPair extends Pair<Integer, Integer>{

    public QuizPair(@Nullable Integer topicId, @Nullable Integer difficulty) {
        super(topicId, difficulty);
    }

    public int getTopicId() {
        return first != null ? first : 0;
    }

    public int getDifficulty() {
        return second != null ? second : 0;
    }
}
