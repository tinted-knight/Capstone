package com.t_knight.and.capstone.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TopicTitle implements Parcelable {

    private int id;
    private String titleFrom;
    private String titleTo;
    private String description;
    private String coverUrl;
    private String from;
    private String to;
    private int wordCount;

    public TopicTitle() {
        // required
    }

    protected TopicTitle(Parcel in) {
        id = in.readInt();
        titleFrom = in.readString();
        titleTo = in.readString();
        description = in.readString();
        coverUrl = in.readString();
        from = in.readString();
        to = in.readString();
        wordCount = in.readInt();
    }

    public static final Creator<TopicTitle> CREATOR = new Creator<TopicTitle>() {
        @Override
        public TopicTitle createFromParcel(Parcel in) {
            return new TopicTitle(in);
        }

        @Override
        public TopicTitle[] newArray(int size) {
            return new TopicTitle[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitleFrom() {
        return titleFrom;
    }

    public void setTitleFrom(String titleFrom) {
        this.titleFrom = titleFrom;
    }

    public String getTitleTo() {
        return titleTo;
    }

    public void setTitleTo(String titleTo) {
        this.titleTo = titleTo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(titleFrom);
        dest.writeString(titleTo);
        dest.writeString(description);
        dest.writeString(coverUrl);
        dest.writeString(from);
        dest.writeString(to);
        dest.writeInt(wordCount);
    }
}
