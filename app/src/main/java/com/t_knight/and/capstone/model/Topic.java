package com.t_knight.and.capstone.model;

import java.util.List;

public class Topic {

    private int id;
    private String titleFrom;
    private String titleTo;
    private String source;
    private String dest;
    private List<SingleCard> cardContent;

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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public List<SingleCard> getCardContent() {
        return cardContent;
    }

    public void setCardContent(List<SingleCard> cardContent) {
        this.cardContent = cardContent;
    }

}
