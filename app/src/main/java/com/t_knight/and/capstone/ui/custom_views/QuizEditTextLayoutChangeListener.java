package com.t_knight.and.capstone.ui.custom_views;

import android.view.View;

public class QuizEditTextLayoutChangeListener implements View.OnLayoutChangeListener {

    private int left, top, right, bottom;

    public QuizEditTextLayoutChangeListener(QuizSpotRect quizSpot) {
        left = quizSpot.getLeft();
        top = quizSpot.getTop();
        right = quizSpot.getRight();
        bottom = quizSpot.getBottom();
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        v.setLeft(this.left);
        v.setTop(this.top);
        v.setRight(this.right);
        v.setBottom(this.bottom);
    }


}
