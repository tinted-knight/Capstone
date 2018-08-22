package com.t_knight.and.capstone.local_db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.t_knight.and.capstone.model.TopicDescription;

@Entity(tableName = "topics")
public class TopicEntity {

    @PrimaryKey
    @ColumnInfo(name = "_id")
    public final int topicId;

    public final String titleFrom;

    public final String titleTo;

    public final String description;

    public final String coverUrl;

    public final String from;

    public final String to;

    public final int wordCount;

    public boolean pinned;

    public TopicEntity(int topicId, String titleFrom, String titleTo, String description, String coverUrl, String from, String to, int wordCount, boolean pinned) {
        this.topicId = topicId;
        this.titleFrom = titleFrom;
        this.titleTo = titleTo;
        this.description = description;
        this.coverUrl = coverUrl;
        this.from = from;
        this.to = to;
        this.wordCount = wordCount;
        this.pinned = pinned;
    }

    public TopicEntity(TopicDescription topic) {
        this(topic.getId(), topic.getTitleFrom(), topic.getTitleTo(),
                topic.getDescription(), topic.getCoverUrl(), topic.getFrom(),
                topic.getTo(), topic.getWordCount(), false
        );
    }

}
