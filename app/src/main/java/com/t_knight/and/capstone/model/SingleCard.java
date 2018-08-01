package com.t_knight.and.capstone.model;

public class SingleCard {

    /**
     * card : 0
     * to : When we go for an interview, most of us think carefully about what to wear and what to say but hardly ever about how to act – in other words, what our body language is telling the interviewer.
     * from : Когда мы идем на интервью, большинство из нас думают [внимательно] о том, что одеть и что говорить, но едва ли о том, как себя вести другими словами - что наш язык тела говорит интервьюеру.
     */

    private int card;
    private String to;
    private String from;

    public int getCard() {
        return card;
    }

    public void setCard(int card) {
        this.card = card;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
