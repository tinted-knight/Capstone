package com.t_knight.and.capstone.local_db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.t_knight.and.capstone.model.TopicTitle;

@Entity(tableName = "topics")
public class TopicEntity {

    @PrimaryKey
    @ColumnInfo(name = "_id")
    public int topicId;

    public String titleFrom;

    public String titleTo;

    public String description;

    public String coverUrl;

    public String from;

    public String to;

    public int wordCount;

    public TopicEntity(int topicId, String titleFrom, String titleTo, String description, String coverUrl, String from, String to, int wordCount) {
        this.topicId = topicId;
        this.titleFrom = titleFrom;
        this.titleTo = titleTo;
        this.description = description;
        this.coverUrl = coverUrl;
        this.from = from;
        this.to = to;
        this.wordCount = wordCount;
    }

    public TopicEntity(TopicTitle topicTitle) {
        this.topicId = topicTitle.getId();
        this.titleFrom = topicTitle.getTitleFrom();
        this.titleTo = topicTitle.getTitleTo();
        this.description = topicTitle.getDescription();
        this.coverUrl = topicTitle.getCoverUrl();
        this.from = topicTitle.getFrom();
        this.to = topicTitle.getTo();
        this.wordCount = topicTitle.getWordCount();
    }

}
