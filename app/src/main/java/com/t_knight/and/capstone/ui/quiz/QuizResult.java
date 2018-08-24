package com.t_knight.and.capstone.ui.quiz;

import android.os.Parcel;
import android.os.Parcelable;

public class QuizResult implements Parcelable {

    public final String translation;

    public final String correctAnswer;

    public final boolean isCorrect;

    public QuizResult(String translation, String correctAnswer, boolean isCorrect) {
        this.translation = translation;
        this.correctAnswer = correctAnswer;
        this.isCorrect = isCorrect;
    }

    protected QuizResult(Parcel in) {
        translation = in.readString();
        correctAnswer = in.readString();
        isCorrect = in.readByte() != 0;
    }

    public static final Creator<QuizResult> CREATOR = new Creator<QuizResult>() {
        @Override
        public QuizResult createFromParcel(Parcel in) {
            return new QuizResult(in);
        }

        @Override
        public QuizResult[] newArray(int size) {
            return new QuizResult[size];
        }
    };

    @Override public String toString() {
        return translation + "; " + correctAnswer + "; " + String.valueOf(isCorrect);
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(translation);
        dest.writeString(correctAnswer);
        dest.writeByte((byte) (isCorrect ? 1 : 0));
    }
}
