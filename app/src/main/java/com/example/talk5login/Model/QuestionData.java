package com.example.talk5login.Model;

import com.google.gson.annotations.SerializedName;

public class QuestionData {
    @SerializedName("AuditResultHeaderID")
    private int headerID;

    @SerializedName("Question")
    private String question;

    

    public QuestionData(int headerID, String question) {
        this.headerID = headerID;
        this.question = question;
    }

    public QuestionData() {
    }

    public int getHeaderID() {
        return headerID;
    }

    public void setHeaderID(int headerID) {
        this.headerID = headerID;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return "QuestionModel{" +
                "headerID=" + headerID +
                ", question='" + question + '\'' +
                '}';
    }
}
