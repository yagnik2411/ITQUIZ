package com.example.itquiz;

public class Question {
    private int QuestionId;
    private boolean ansId;

    public Question() {
    }

    public Question(int questionId, boolean ansId) {
        QuestionId = questionId;
        this.ansId = ansId;
    }

    public boolean isAnsId() {
        return ansId;
    }

    public void setAnsId(boolean ansId) {
        this.ansId = ansId;
    }

    public boolean isanswertrue(){
        return ansId;
    }

    public int getQuestionId() {
        return QuestionId;
    }

    public void setQuestionId(int questionId) {
        QuestionId = questionId;
    }

}
