package com.t_knight.and.capstone.model.quiz;

import java.util.List;

public class Quiz {

    private int topic_id;
    private int version;
    private List<QuizCard> cards;

    public int getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(int topic_id) {
        this.topic_id = topic_id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public List<QuizCard> getCards() {
        return cards;
    }

    public void setCards(List<QuizCard> cards) {
        this.cards = cards;
    }

}
