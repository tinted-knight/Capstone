package com.t_knight.and.capstone.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TopicDescription implements Parcelable {

    private String coverUrl;
    private String description;
    private String from;
    private int id;
    private String titleFrom;
    private String titleTo;
    private String to;
    private int wordCount;

    public TopicDescription() {
        //required
    }

    protected TopicDescription(Parcel in) {
        coverUrl = in.readString();
        description = in.readString();
        from = in.readString();
        id = in.readInt();
        titleFrom = in.readString();
        titleTo = in.readString();
        to = in.readString();
        wordCount = in.readInt();
    }

    public static final Creator<TopicDescription> CREATOR = new Creator<TopicDescription>() {
        @Override
        public TopicDescription createFromParcel(Parcel in) {
            return new TopicDescription(in);
        }

        @Override
        public TopicDescription[] newArray(int size) {
            return new TopicDescription[size];
        }
    };

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

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
        dest.writeString(coverUrl);
        dest.writeString(description);
        dest.writeString(from);
        dest.writeInt(id);
        dest.writeString(titleFrom);
        dest.writeString(titleTo);
        dest.writeString(to);
        dest.writeInt(wordCount);
    }
}
