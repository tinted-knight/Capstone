package com.t_knight.and.capstone.ui.quiz;

public class QuizResult {

    public final String translation;

    public final String correctAnswer;

    public final boolean isCorrect;

    public QuizResult(String translation, String correctAnswer, boolean isCorrect) {
        this.translation = translation;
        this.correctAnswer = correctAnswer;
        this.isCorrect = isCorrect;
    }

    @Override public String toString() {
        return translation + "; " + correctAnswer + "; " + String.valueOf(isCorrect);
    }
}
