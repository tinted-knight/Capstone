package com.t_knight.and.capstone.model.quiz;

import java.util.List;

public class QuizCard {
    /**
     * id : 0
     * text : When we go for an interview, most of us think carefully about what to wear and what to say but hardly ever about how to act – in other words, what our body language is telling the interviewer.
     * translation : Когда мы идем на интервью, большинство из нас думают о том, что надеть и что говорить.
     * literal :
     * spots : [{"start":0,"end":4,"answer":"When","answers":[],"hint_prefix":"","hint":"когда","hint_suffix":""},{"start":8,"end":10,"answer":"we","answers":[],"hint_prefix":"мы ","hint":"идем","hint_suffix":""},{"start":18,"end":27,"answer":"interview","answers":[],"hint_prefix":"","hint":"интервью","hint_suffix":""},{"start":87,"end":90,"answer":"say","answers":[],"hint_prefix":"","hint":"говорить","hint_suffix":""}]
     */

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
