package com.t_knight.and.capstone.model.quiz;

import java.util.List;

public class QuizCard {

    private int id;
    private String text;
    private String translation;
    private String literal;
    private List<QuizSpot> spots;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getLiteral() {
        return literal;
    }

    public void setLiteral(String literal) {
        this.literal = literal;
    }

    public List<QuizSpot> getSpots() {
        return spots;
    }

    public void setSpots(List<QuizSpot> spots) {
        this.spots = spots;
    }

}
