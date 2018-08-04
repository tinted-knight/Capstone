package com.t_knight.and.capstone.model.quiz;

import java.util.List;

public class QuizSpot {
    /**
     * start : 0
     * end : 4
     * answer : When
     * answers : []
     * hint_prefix :
     * hint : когда
     * hint_suffix :
     */

    private int start;
    private int end;
    private String answer;
    private String hint_prefix;
    private String hint;
    private String hint_suffix;
    private List<String> answers;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getHint_prefix() {
        return hint_prefix;
    }

    public void setHint_prefix(String hint_prefix) {
        this.hint_prefix = hint_prefix;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getHint_suffix() {
        return hint_suffix;
    }

    public void setHint_suffix(String hint_suffix) {
        this.hint_suffix = hint_suffix;
    }

    public List<?> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }
}
