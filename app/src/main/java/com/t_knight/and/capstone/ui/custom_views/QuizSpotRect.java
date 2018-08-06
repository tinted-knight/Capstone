package com.t_knight.and.capstone.ui.custom_views;

import android.graphics.Rect;
import android.widget.TextView;

public class QuizSpotRect {

    private int left, right, top, bottom;
    private int startOffset, endOffset;

    public QuizSpotRect(int left, int right, int top, int bottom, int startOffset, int endOffset) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
        this.startOffset = startOffset;
        this.endOffset = endOffset;
    }

    public QuizSpotRect(Rect rect, int startOffset, int endOffset) {
        this(rect.left, rect.right, rect.top, rect.bottom, startOffset, endOffset);
    }

    public void adjustOffset(TextView parent) {
        left += parent.getLeft();
        right += parent.getLeft();
        top += parent.getTop();
        bottom += parent.getTop();
    }

    public void setOffsetLeft(int offset) {
        this.left += offset;
    }

    public void setOffsetRight(int offset) {
        this.right += offset;
    }

    public void setOffsetTop(int offset) {
        this.top += offset;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public int getTop() {
        return top;
    }

    public int getBottom() {
        return bottom;
    }

    public int getWordLength() {
        return endOffset - startOffset;
    }
}

