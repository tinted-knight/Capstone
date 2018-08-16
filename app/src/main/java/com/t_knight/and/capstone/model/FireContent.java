package com.t_knight.and.capstone.model;

import java.util.List;

public class FireContent {

    private int version;
    private List<TopicDescription> topicDescriptions;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public List<TopicDescription> getTopicDescriptions() {
        return topicDescriptions;
    }

    public void setTopicDescriptions(List<TopicDescription> topicDescriptions) {
        this.topicDescriptions = topicDescriptions;
    }

}
