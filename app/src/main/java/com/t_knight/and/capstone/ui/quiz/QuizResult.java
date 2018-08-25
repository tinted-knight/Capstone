package com.t_knight.and.capstone.ui.quiz;

import android.os.Parcel;
import android.os.Parcelable;

public class QuizResult implements Parcelable {

    public final String word;

    public final String translation;

    public final boolean isCorrect;

    QuizResult(String word, String translation, boolean isCorrect) {
        this.word = word;
        this.translation = translation;
        this.isCorrect = isCorrect;
    }

    protected QuizResult(Parcel in) {
        word = in.readString();
        translation = in.readString();
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
        return word + "; " + translation + "; " + String.valueOf(isCorrect);
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(word);
        dest.writeString(translation);
        dest.writeByte((byte) (isCorrect ? 1 : 0));
    }
}
