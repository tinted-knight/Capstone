package com.t_knight.and.capstone.ui.custom_views;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatTextView;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.Display;
import android.view.WindowManager;

import com.t_knight.and.capstone.model.quiz.QuizSpot;

import java.util.ArrayList;
import java.util.List;

//TODO stackoverflow links
public class QuizTextView extends AppCompatTextView {

    private List<QuizSpotRect> quizSpotRects = new ArrayList<>();

    private List<QuizSpot> quizSpots;

    private Rect parentTextViewRect;

    private static final String TAG = "TAGG";

    public QuizTextView(Context context) {
        this(context, null);
    }

    public QuizTextView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public QuizTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        parentTextViewRect = new Rect();
    }

    public void setQuizSpots(List<QuizSpot> data) {
        if (data != null && data.size() > 0) {
            quizSpots = data;
//            if (isInLayout()) requestLayout();
        } else {
            quizSpots = null;
            quizSpotRects = null;
        }
    }

    @Override protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        calculateSpots();
    }

    private void calculateSpots() {
        if (quizSpots != null) {
            quizSpotRects = new ArrayList<>(quizSpots.size());
            for (QuizSpot spot : quizSpots) {
                quizSpotRects.add(new QuizSpotRect(calculateMeasures(spot.getStart(), spot.getEnd()), spot.getStart(), spot.getEnd()));
            }
        }
    }

    private Rect calculateMeasures(int startOffset, int endOffset) {
        Layout textViewLayout = this.getLayout();
        double startXCoordinatesOfSpot = textViewLayout.getPrimaryHorizontal(startOffset);
        double endXCoordinatesOfSpot = textViewLayout.getPrimaryHorizontal(endOffset);

        int currentLineStartOffset = textViewLayout.getLineForOffset(startOffset);
        int currentLineEndOffset = textViewLayout.getLineForOffset(endOffset);
        boolean keywordIsInMultiLine = currentLineStartOffset != currentLineEndOffset;
        textViewLayout.getLineBounds(currentLineStartOffset, parentTextViewRect);

        int[] parentTextViewLocation = {0, 0};
        this.getLocationOnScreen(parentTextViewLocation);

        double parentTextViewTopAndBottomOffset = (
                this.getScrollY() +
                        this.getCompoundPaddingTop()
        );

        parentTextViewRect.top += parentTextViewTopAndBottomOffset;
        parentTextViewRect.bottom += parentTextViewTopAndBottomOffset;

        if (keywordIsInMultiLine) {

            WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();

            Point point = new Point();
            display.getSize(point);
            int screenHeight = point.y;

            int dyTop = parentTextViewRect.top;
            int dyBottom = screenHeight - parentTextViewRect.bottom;
            boolean onTop = dyTop > dyBottom;

            if (onTop) {
                endXCoordinatesOfSpot = textViewLayout.getLineRight(currentLineStartOffset);
            } else {
                parentTextViewRect = new Rect();
                textViewLayout.getLineBounds(currentLineEndOffset, parentTextViewRect);
                parentTextViewRect.top += parentTextViewTopAndBottomOffset;
                parentTextViewRect.bottom += parentTextViewTopAndBottomOffset;
                startXCoordinatesOfSpot = textViewLayout.getLineLeft(currentLineEndOffset);
            }

        }

        parentTextViewRect.left += (
//                parentTextViewLocation[0] +
                startXCoordinatesOfSpot +
                        this.getCompoundPaddingLeft() -
                        this.getScrollX()
        );
        parentTextViewRect.right = (int) (
                parentTextViewRect.left +
                        endXCoordinatesOfSpot -
                        startXCoordinatesOfSpot
        );

        int left = parentTextViewRect.left;
        int right = parentTextViewRect.right;
        int top = parentTextViewRect.top;
        int bottom = parentTextViewRect.bottom;

        return new Rect(left, top, right, bottom);

    }

    public List<QuizSpotRect> getQuizSpotRects() {
        return quizSpotRects;
    }
}
